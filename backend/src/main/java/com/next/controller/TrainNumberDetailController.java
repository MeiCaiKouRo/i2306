package com.next.controller;

import com.next.common.JsonData;
import com.next.dto.TrainNumberDetailDto;
import com.next.service.TrainNumberDetailService;
import com.next.service.TrainNumberService;
import com.next.service.TrainStationService;
import com.next.model.TrainNumber;
import com.next.model.TrainNumberDetail;
import com.next.model.TrainStation;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/train/numberDetail")
public class TrainNumberDetailController {


    @Resource
    private TrainNumberDetailService trainNumberDetailService;

    @Resource
    private TrainStationService trainStationService;

    @Resource
    private TrainNumberService trainNumberService;

    @RequestMapping("/list.page")
    public ModelAndView page() {
        return new ModelAndView("trainNumberDetail");
    }

    @ResponseBody
    @RequestMapping("/list.json")
    public JsonData list() {
        List<TrainNumberDetail> trainNumberDetailList = trainNumberDetailService.getAll();
        List<TrainStation> trainStationList = trainStationService.getAll();
        Map<Integer, String> stationMap = trainStationList.stream().collect(Collectors.toMap(TrainStation::getId, TrainStation::getName));
        List<TrainNumber> trainNumberList = trainNumberService.getAll();
        Map<Integer, String> numberMap = trainNumberList.stream().collect(Collectors.toMap(TrainNumber::getId, TrainNumber::getName));
        List<TrainNumberDetailDto> dtoList = trainNumberDetailList.stream().map(numberDetail -> {
            TrainNumberDetailDto dto = new TrainNumberDetailDto();
            dto.setId(numberDetail.getId());
            dto.setFromStationId(numberDetail.getFromStationId());
            dto.setToStationId(numberDetail.getToStationId());
            dto.setToStation(stationMap.get(numberDetail.getFromStationId()));
            dto.setFromStation(stationMap.get(numberDetail.getFromStationId()));
            dto.setFromCityId(numberDetail.getFromCityId());
            dto.setToCityId(numberDetail.getToCityId());
            dto.setTrainNumberId(numberDetail.getTrainNumberId());
            dto.setTrainNumber(numberMap.get(numberDetail.getTrainNumberId()));
            dto.setStationIndex(numberDetail.getStationIndex());
            dto.setRelativeMinute(numberDetail.getRelativeMinute());
            dto.setWaitMinute(numberDetail.getWaitMinute());
            dto.setMoney(numberDetail.getMoney());
            return dto;
        }).collect(Collectors.toList());
        return JsonData.success(dtoList);
    }

    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData save() {
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData update() {
        return JsonData.success();
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") Integer id) {
        return JsonData.success();
    }
}
