package com.mutesaid.service;

import com.mutesaid.pojo.Usr;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Map;

@Service
public interface UsrService {
    void insert(String name, String pwd);

    Boolean hasUsrName(String name);

    Boolean isTrue(String name, String pwd);

    Cookie setToken(String name);

    Boolean isLogin(Cookie[] cookies);

    Map getTokenMap(String token);

    Map getTokenMap(Cookie[] cookies);

    Boolean isTrueToken(String token);
}
