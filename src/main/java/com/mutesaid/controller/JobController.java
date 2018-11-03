package com.mutesaid.controller;

import com.mutesaid.service.ProfessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class JobController {
    @Autowired
    private ProfessionService professionService;

    private Logger logger = LogManager.getLogger(JobController.class);

    @GetMapping("/job")
    public String job(ModelMap model) {
        Long start = System.currentTimeMillis();
        Map<String, List> jobMap = professionService.getProfesList();
        model.addAttribute("jobMap",jobMap);
        Long end = System.currentTimeMillis();
        logger.info("jobPage controller执行时间：{}ms",end-start);
        return "jobpage";
    }

}
