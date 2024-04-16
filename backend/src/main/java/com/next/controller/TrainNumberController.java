package com.next.controller;

import com.next.common.JsonData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/train/number")
public class TrainNumberController {

    @RequestMapping("/list.page")
    public ModelAndView page() {
        return new ModelAndView("trainNumber");
    }

    @ResponseBody
    @RequestMapping("/list.json")
    public JsonData list() {
        return JsonData.success();
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
}
