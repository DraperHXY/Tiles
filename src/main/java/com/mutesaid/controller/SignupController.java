package com.mutesaid.controller;

import com.mutesaid.pojo.Usr;
import com.mutesaid.service.UsrService;
import com.mutesaid.utils.ResponseBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.util.Map;
import java.util.Objects;


@Controller
public class SignupController {
    @Autowired
    private UsrService usrService;

    @Autowired
    private ResponseBo responseBo;

    @RequestMapping(value = "/signupPage", method = RequestMethod.GET)
    public String signupPage() {
        return "signPage";
    }

    @PostMapping("/signup")
    public String signup(RedirectAttributes model, @Validated Usr usr, BindingResult error) {
        if(usrService.hasUsrName(usr.getName())) {
            Map json = responseBo.msg("Beused.usr.name");
            model.addFlashAttribute("json", json);
            return "redirect:signupPage";
        }
        if(error.hasErrors()){
            String msg = Objects.requireNonNull(error.getFieldError()).getDefaultMessage();
            Map json = responseBo.msg(msg);
            model.addFlashAttribute("json", json);
            return "redirect:signupPage";
        }

        usrService.insert(usr);
        return "redirect:loginPage";
    }
}
