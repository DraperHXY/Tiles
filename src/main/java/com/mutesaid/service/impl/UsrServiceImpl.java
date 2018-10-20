package com.mutesaid.service.impl;

import com.mutesaid.mapper.UsrMapper;
import com.mutesaid.pojo.Usr;
import com.mutesaid.service.UsrService;
import com.mutesaid.utils.CookieUtil;
import com.mutesaid.utils.JJWTUtil;
import com.mutesaid.utils.MD5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsrServiceImpl implements UsrService {
    @Autowired
    private UsrMapper usrMapper;

    private Logger logger = LogManager.getLogger(UsrServiceImpl.class);

    @Override
    public void insert(Usr usr) {
        Long currentTime = System.currentTimeMillis();
        String pwdIn = MD5Util.encrypt(usr.getPwd(), currentTime.toString());
        usr.setPwd(pwdIn);
        usr.setCreateAt(currentTime);
        usr.setUpdateAt(currentTime);
        usrMapper.insert(usr);
    }

    @Override
    public Boolean hasUsrName(String name) {
        return usrMapper.getByName(name)!=null;
    }

    @Override
    public Boolean isTrue(String name, String pwd) {
        Usr usr = usrMapper.getByName(name);
        if(usr==null){
            return false;
        }else {
            String pwdIn = MD5Util.encrypt(pwd, usr.getUpdateAt().toString());
            return usr.getPwd().equals(pwdIn);
        }
    }

    @Override
    public Cookie setToken(String name) {
        String key = "abcd";
        Map<String, Object> payload = new HashMap<>(1);
        payload.put("usrName", name);

        String jwt = JJWTUtil.sign(payload, key);
        return CookieUtil.addCookie("token", jwt);
    }

    @Override
    public Boolean isLogin(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> "token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .anyMatch(this::isTrueToken);
    }

    @Override
    public  Map getTokenMap(String token) {
        return JJWTUtil.verify(token, "abcd");
    }

    @Override
    public  Map getTokenMap(Cookie[] cookies) {
        List<String> jwt = Arrays.stream(cookies)
                .filter(cookie -> "token".equals(cookie.getName()))
                .map(Cookie::getValue).collect(Collectors.toList());
        return getTokenMap(jwt.get(0));
    }

    @Override
    public Boolean isTrueToken(String token) {
        return getTokenMap(token)!=null;
    }
}
