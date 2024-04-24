package com.next.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.next.common.TrainEsConstant;
import com.next.dto.TrainNumberLeftDto;
import com.next.exception.BusinessException;
import com.next.model.*;
import com.next.param.SearchLeftCountParam;
import com.next.util.BeanValidator;
import com.next.util.JsonMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jackson.type.TypeReference;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TrainSeatService {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 20, 2, TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(200), new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Resource
    private EsClient esClient;
    @Resource
    private TrainNumberService trainNumberService;
    @Resource
    private TrainCacheService trainCacheService;


    public List<TrainNumberLeftDto> searchLeftCount(SearchLeftCountParam param) throws Exception {
        BeanValidator.check(param);
        List<TrainNumberLeftDto> dtoList = Lists.newArrayList();

        // 从es里获取满足条件的车次
        GetRequest getRequest = new GetRequest(TrainEsConstant.INDEX, TrainEsConstant.TYPE,
                param.getFromStationId() + "_" + param.getToStationId());
        GetResponse getResponse = esClient.get(getRequest);
        if (getResponse == null) {
            throw new BusinessException("数据查询失败，请重试");
        }
        Map<String, Object> map = getResponse.getSourceAsMap();
        if (MapUtils.isEmpty(map)) {
            return dtoList;
        }

        String trainNumbers = (String) map.get(TrainEsConstant.COLUMN_TRAIN_NUMBER);// D9,D386

        // 拆分出所有的车次
        List<String> numberList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(trainNumbers);

        numberList.parallelStream().forEach(number -> {
            TrainNumber trainNumber = trainNumberService.findByNameFromCache(number);
            if (trainNumber == null) {
                return;
            }

            String detailStr = trainCacheService.get("TN_" + number);
            List<TrainNumberDetail> detailList = JsonMapper.string2Obj(detailStr, new TypeReference<List<TrainNumberDetail>>() {
            });

            Map<Integer, TrainNumberDetail> detailMap = Maps.newHashMap();
            detailList.stream().forEach(detail -> detailMap.put(detail.getFromStationId(), detail));

            /**
             * detailList: {1,2},{2,3},{3,4},{4,5},{5,6}
             * detailMap: 1->{1,2}, 2->{2,3}, ... 5->{5,6}
             * param: 2->5
             * target: {2,3},{3,4},{4,5}
             * detailMap 2->{2,3} -> 3-> {3,4}->4->{4,5}
             *
             * {2,3}:5,{3,4}:3,{4,5}:10 ->  left:3
             */
            int curFromStationId = param.getFromStationId();
            int targetToStationId = param.getToStationId();
            long min = Long.MAX_VALUE;
            boolean isSuccess = false;
            String redisKey = number + "_" + param.getDate() + "_Count";

            while (true) {
                TrainNumberDetail detail = detailMap.get(curFromStationId);
                if (detail == null) {
                    log.error("detail is null, stationId:{}, number:{}", curFromStationId, number);
                    break;
                }

                // 从redis里取出本段详情剩余的座位，并更新整体的最小座位数
                min = Math.min(min, NumberUtils.toLong(trainCacheService.hget(redisKey, detail.getFromStationId() + "_" + detail.getToStationId()), 0l));

                if (detail.getToStationId() == targetToStationId) {
                    isSuccess = true;
                    break;
                }

                // 下次查询的起始站是本次详情的到达站
                curFromStationId = detail.getToStationId();
            }
            if (isSuccess) {
                dtoList.add(new TrainNumberLeftDto(trainNumber.getId(), number, min));
            }
        });
        return dtoList;
    }

}
