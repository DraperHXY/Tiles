package com.mutesaid.controller;

import com.mutesaid.service.UsrService;
import com.mutesaid.utils.CookieUtil;
import com.mutesaid.utils.ResponseBo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UsrService usrService;

    @Autowired
    private ResponseBo responseBo;

    private Logger logger = LogManager.getLogger(LoginController.class);

    @GetMapping(value = "/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @PostMapping(value = "/login")
    public String login(HttpServletResponse response, RedirectAttributes model,
                        String name, String pwd) {
        try{
            usrService.isTrue(name, pwd);

            Cookie cookie = usrService.setToken(name);
            response.addCookie(cookie);
            return "redirect:u/student";
        }catch(IllegalArgumentException argE){
            logger.info("用户名密码不合法");
            Map json = responseBo.msg(argE.getMessage());
            model.addFlashAttribute("json", json);
            return "redirect:loginPage";
        }catch (Exception e) {
            logger.info("未知异常{}",e);
            Map json = responseBo.msg("Unknow.Exception");
            model.addFlashAttribute("json", json);
            return "redirect:errorPage";
        }
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletResponse response) {
        response.addCookie(CookieUtil.killCookie("token"));

        return "redirect:loginPage";
    }
}
