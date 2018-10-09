package com.mutesaid.utils;

import com.mutesaid.service.UsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UsrService usrService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();

        Boolean isLogin = usrService.isLogin(cookies);

        if (isLogin){
            return true;
        }else {
            response.sendRedirect("loginPage");
            return false;
        }
    }
}
