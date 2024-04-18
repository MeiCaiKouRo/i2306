package com.next.controller;

import com.next.common.JsonData;
import com.next.dto.TrainNumberDto;
import com.next.param.TrainNumberParam;
import com.next.service.TrainNumberService;
import com.next.service.TrainStationService;
import com.next.model.TrainNumber;
import com.next.model.TrainStation;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/train/number")
public class TrainNumberController {

    @Resource
    private TrainNumberService trainNumberService;
    @Resource
    private TrainStationService trainStationService;

    @RequestMapping("/list.page")
    public ModelAndView page() {
        return new ModelAndView("trainNumber");
    }

    @ResponseBody
    @RequestMapping("/list.json")
    public JsonData list() {
        List<TrainNumber> numberList = trainNumberService.getAll();
        List<TrainStation> stationList = trainStationService.getAll();
        Map<Integer, String> stationMap = stationList.stream().collect(Collectors.toMap(TrainStation::getId, TrainStation::getName));
        List<TrainNumberDto> dtoList = numberList.stream().map(number -> {
            TrainNumberDto dto = new TrainNumberDto();
            dto.setId(number.getId());
            dto.setFromStationId(number.getFromStationId());
            dto.setToStationId(number.getToStationId());
            dto.setToStation(stationMap.get(number.getFromStationId()));
            dto.setFromStation(stationMap.get(number.getFromStationId()));
            dto.setFromCityId(number.getFromCityId());
            dto.setToCityId(number.getToCityId());
            dto.setName(number.getName());
            dto.setTrainType(number.getTrainType());
            dto.setType(number.getType());
            dto.setSeatNum(number.getSeatNum());
            return dto;
        }).collect(Collectors.toList());
        return JsonData.success(dtoList);
    }

    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData save(TrainNumberParam trainNumberParam) {
        trainNumberService.save(trainNumberParam);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData update(TrainNumberParam trainNumberParam) {
        trainNumberService.update(trainNumberParam);
        return JsonData.success();
    }
}
