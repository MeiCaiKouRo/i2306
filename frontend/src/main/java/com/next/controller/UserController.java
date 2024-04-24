package com.next.controller;

import com.next.common.JsonData;
import com.next.common.RequestHolder;
import com.next.model.TrainTraveller;
import com.next.model.TrainUser;
import com.next.service.TrainTravellerService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private TrainTravellerService trainTravellerService;

    @RequestMapping("/getTravellers.json")
    @ResponseBody
    public JsonData getTravellers() {
        TrainUser trainUser = RequestHolder.getCurrentUser();
        List<TrainTraveller> trainTravellerList = trainTravellerService.getByUserId(trainUser.getId());
        List<TrainTraveller> showList = trainTravellerList.stream().map(
                trainTraveller -> TrainTraveller.builder()
                        .id(trainTraveller.getId())
                        .name(trainTraveller.getName())
                        .adultFlag(trainTraveller.getAdultFlag())
                        .idNumber(hideSensitiveMsg(trainTraveller.getIdNumber()))
                        .build())
                .collect(Collectors.toList());
        return JsonData.success(showList);
    }

    private String hideSensitiveMsg(String msg) {
        if (StringUtils.isBlank(msg) || msg.length() < 7) {
            return msg;
        }
        return msg.substring(0, 3) + "******" + msg.substring(msg.length() - 3);
    }


}
