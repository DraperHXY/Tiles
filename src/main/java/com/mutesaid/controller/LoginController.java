package com.mutesaid.controller;

import com.mutesaid.service.UsrService;
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

@Controller
public class LoginController {
    @Autowired
    UsrService usrService;

    @GetMapping(value = "/loginPage")
    public String loginPage() {
        return "loginPage";
    }

    @PostMapping(value = "/login")
    public String login(HttpServletResponse response, RedirectAttributes model,
                        String name, String pwd) {
        if(!usrService.isTrue(name, pwd)) {
            model.addFlashAttribute("msg","-账号或密码错误");
            return "redirect:loginPage";
        }else {
            Cookie cookie = usrService.setToken(name);

            response.addCookie(cookie);
            return "redirect:student";
        }
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if("token".equals(cookie.getName())) {
                Cookie killCookie = new Cookie("token", null);
                killCookie.setMaxAge(0);
                killCookie.setPath("/");
                response.addCookie(killCookie);
            }
        }
        return "redirect:loginPage";
    }
}
