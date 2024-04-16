package com.next.controller;

import com.next.common.JsonData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/train/numberDetail")
public class TrainNumberDetailController {

    @RequestMapping("/list.page")
    public ModelAndView page() {
        return new ModelAndView("trainNumberDetail");
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

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") Integer id) {
        return JsonData.success();
    }
}
