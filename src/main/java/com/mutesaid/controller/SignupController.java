package com.mutesaid.controller;

import com.mutesaid.service.UsrService;
import com.mutesaid.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController {
    @Autowired
    UsrService usrService;

    @Autowired
    MD5Util md5Util;

    @RequestMapping(value = "/signupPage", method = RequestMethod.GET)
    public String signupPage() {
        return "signPage";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(RedirectAttributes model, String name, String pwd) {
        if(usrService.hasUsrName(name)) {
            model.addFlashAttribute("msg","-账号已存在");
            return "redirect:signupPage";
        }
        usrService.insert(name, pwd);

        return "redirect:loginPage";
    }
}
