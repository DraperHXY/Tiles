package com.mutesaid.controller;

import com.mutesaid.pojo.Usr;
import com.mutesaid.service.UsrService;
import com.mutesaid.utils.ResponseBo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class SignupController {
    @Autowired
    private UsrService usrService;

    private Logger logger = LogManager.getLogger(SignupController.class);

    @GetMapping("/signupPage")
    public String signupPage() {
        return "signPage";
    }

    @PostMapping("/signup")
    public String signup(RedirectAttributes model, @Validated Usr usr, BindingResult error) {
        logger.info("signup : usr = [{}]", usr);
        try{
            usrService.insert(usr, error);
            logger.info("signup success");
            return "redirect:loginPage";
        }catch (IllegalArgumentException argE){
            logger.error(argE.getMessage());
            logger.error("signup parameters error");
            Map json = ResponseBo.msg(argE.getMessage());
            model.addFlashAttribute("json", json);
            return "redirect:signupPage";
        }catch (Throwable t) {
            logger.error(t.getMessage());
            logger.error("signup unknown error");
            Map json = ResponseBo.msg("Unknow.Exception");
            model.addFlashAttribute("json", json);
            return "redirect:errorPage";
        }
    }
}
