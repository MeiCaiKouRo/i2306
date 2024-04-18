package com.next.controller;


import com.next.common.JsonData;
import com.next.param.TrainCityParam;
import com.next.service.TrainCityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/train/city")
public class TrainCityController {

    @Resource
    private TrainCityService trainCityService;

    @RequestMapping("/list.page")
    public ModelAndView page() {
        return new ModelAndView("trainCity");
    }

    @ResponseBody
    @RequestMapping("/list.json")
    public JsonData list() {
        return JsonData.success(trainCityService.getAll());
    }

    @ResponseBody
    @RequestMapping("/save.json")
    public JsonData save(TrainCityParam param) {
        trainCityService.save(param);
        return JsonData.success();
    }

    @ResponseBody
    @RequestMapping("/update.json")
    public JsonData update(TrainCityParam param) {
        trainCityService.update(param);
        return JsonData.success();
    }
}
