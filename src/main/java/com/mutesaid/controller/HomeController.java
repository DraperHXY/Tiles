package com.mutesaid.controller;

import com.mutesaid.pojo.Expert;
import com.mutesaid.service.ExpertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ExpertService expertService;

    private final Logger logger = LogManager.getLogger(HomeController.class);

    @GetMapping("/home")
    @SuppressWarnings("unchecked")
    public String view(ModelMap model) {
        Long start = System.currentTimeMillis();
        List<Expert> expertList =  expertService.getAllExpert();
        model.addAttribute("expertList", expertList);
        Long end = System.currentTimeMillis();

        logger.info("homePage Controller 执行时间{}ms",end - start);


        return "homepage";
    }
}
