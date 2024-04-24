package com.next.controller;

import com.next.common.JsonData;
import com.next.dto.TrainNumberLeftDto;
import com.next.param.SearchLeftCountParam;
import com.next.service.TrainSeatService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/front")
@Slf4j
public class FrontController {

    @Resource
    private TrainSeatService trainSeatService;

    @RequestMapping("/searchLeftCount.json")
    @ResponseBody
    public JsonData searchLeftCount(SearchLeftCountParam param) {
        try {
            List<TrainNumberLeftDto> dtoList = trainSeatService.searchLeftCount(param);
            return JsonData.success(dtoList);
        } catch (Exception e) {
            log.error("searchLeftCount exception, param:{}", param, e);
            return JsonData.fail("查询异常，请稍后尝试");
        }
    }

}
