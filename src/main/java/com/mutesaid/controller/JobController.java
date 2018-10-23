package com.mutesaid.controller;

import com.mutesaid.service.ProfessionService;
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

    @GetMapping("/job")
    public String job(ModelMap model) {
        Map<String, List> jobMap = professionService.getProfesList();
        model.addAttribute("jobMap",jobMap);
        return "jobpage";
    }

}
