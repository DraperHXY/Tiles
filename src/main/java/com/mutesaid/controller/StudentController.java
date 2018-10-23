package com.mutesaid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
    @GetMapping("/u/student")
    public String getStudents(){
        return "studentPage";
    }
}
