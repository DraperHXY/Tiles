package com.mutesaid.controller;

import com.mutesaid.service.UsrService;
import com.mutesaid.utils.CookieUtil;
import com.mutesaid.utils.ResponseBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UsrService usrService;

    @Autowired
    private ResponseBo responseBo;

    @GetMapping(value = "/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @PostMapping(value = "/login")
    public String login(HttpServletResponse response, RedirectAttributes model,
                        String name, String pwd) {
        if(!usrService.isTrue(name, pwd)) {
            Map json = responseBo.msg("Input.usr.error");
            model.addFlashAttribute("json", json);
            return "redirect:loginPage";
        }else {
            Cookie cookie = usrService.setToken(name);
            response.addCookie(cookie);
            return "redirect:u/student";
        }
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletResponse response) {
        response.addCookie(CookieUtil.killCookie("token"));

        return "redirect:loginPage";
    }
}
