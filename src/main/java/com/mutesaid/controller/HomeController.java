package com.mutesaid.controller;

import com.mutesaid.pojo.Expert;
import com.mutesaid.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private ExpertService expertService;

    @RequestMapping(method = RequestMethod.GET)
    public String view(ModelMap model) {
        List<Expert> expertList =  expertService.getAllExpert();
        model.addAttribute("expertList", expertList);

        return "homepage";
    }
}
