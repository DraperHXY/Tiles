package com.mutesaid.controller;

import com.mutesaid.pojo.Student;
import com.mutesaid.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/u/student")
    public String getStudent(ModelMap model){
        List<Student> stuList = studentService.selectAll();
        model.addAttribute("stuList", stuList);
        return "studentPage";
    }

    @DeleteMapping("/u/student/{id}")
    public String deleteStudent(@PathVariable String id){
        studentService.delete(Long.parseLong(id));

        return "redirect:/u/student";
    }

    @GetMapping("/u/student/add")
    public String addPage(){
        return "addPage";
    }

    @PostMapping("/u/student")
    public String insertStudent(Student stu){
        System.out.println(stu);
        studentService.insert(stu);
        return "redirect:/u/student";
    }

    @PostMapping("/u/student/update/{id}")
    public String updateStudent(@PathVariable String id, ModelMap model){
        model.addAttribute("id", id);

        return "updatePage";
    }

    @PutMapping("/u/student/{id}")
    public String updateStudent(@PathVariable String id, String key, String value){
        System.out.println(studentService.update(Long.parseLong(id), key, value));

        return "redirect:/u/student";
    }
}
