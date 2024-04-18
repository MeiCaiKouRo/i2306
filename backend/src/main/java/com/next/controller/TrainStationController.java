package com.next.controller;

import com.next.common.JsonData;
import com.next.dto.TrainStationDto;
import com.next.param.TrainStationParam;
import com.next.service.TrainCityService;
import com.next.service.TrainStationService;
import com.next.model.TrainCity;
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
@RequestMapping("/admin/train/station")
public class TrainStationController {

    @Resource
    private TrainStationService trainStationService;

    @Resource
    private TrainCityService trainCityService;

    @RequestMapping("/list.page")
    public ModelAndView page() {
        return new ModelAndView("trainStation");
    }

    @ResponseBody
    @RequestMapping("/list.json")
    public JsonData list() {

        List<TrainStation> stationList = trainStationService.getAll();
        List<TrainCity> cityList = trainCityService.getAll();
        Map<Integer, String> cityMap = cityList.stream().collect(Collectors.toMap(TrainCity::getId, TrainCity::getName));
        List<TrainStationDto> dtoList = stationList.stream().map(station -> {
            TrainStationDto dto = new TrainStationDto();
            dto.setId(station.getId());
            dto.setName(station.getName());
            dto.setCityId(station.getCityId());
            dto.setCityName(cityMap.get(station.getCityId()));
            return dto;
        }).collect(Collectors.toList());
        return JsonData.success(dtoList);
    }

    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData save(TrainStationParam trainStationParam) {
        trainStationService.save(trainStationParam);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData update(TrainStationParam trainStationParam) {
        trainStationService.update(trainStationParam);
        return JsonData.success();
    }
}
