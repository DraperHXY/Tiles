package com.mutesaid.service.impl;

import com.mutesaid.mapper.UsrMapper;
import com.mutesaid.pojo.Usr;
import com.mutesaid.service.UsrService;
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

    @Autowired
    private MD5Util md5Util;

    @Autowired
    private JJWTUtil jjwtUtil;

    Logger logger = LogManager.getLogger(UsrServiceImpl.class);

    @Override
    public void insert(String name, String pwd) {
        Long currentTime = System.currentTimeMillis();
        String pwdIn = md5Util.encrypt(pwd, currentTime.toString());
        Usr usr = new Usr();
        usr.setName(name);
        usr.setPwd(pwdIn);
        usr.setCreateAt(currentTime);
        usr.setUpdateAt(currentTime);
        usrMapper.insert(usr);
    }

    @Override
    public Boolean hasUsrName(String name) {
        return usrMapper.getByName(name)!=null;
    }

    /**
     * 判断用户名是否存在，判断密码是否正确
     * @param name 用户名
     * @param pwd 密码
     * @return 判断结果
     */
    @Override
    public Boolean isTrue(String name, String pwd) {
        Usr usr = usrMapper.getByName(name);
        if(usr==null){
            logger.info("用户不存在");
            return false;
        }else {
            String pwdIn = md5Util.encrypt(pwd, usr.getUpdateAt().toString());
            return pwdIn.equals(usr.getPwd());
        }
    }

    @Override
    public Cookie setToken(String name) {
        Long currentTime = System.currentTimeMillis();
        Date expiration = new Date(currentTime + 600*1000);
        Map<String, Object> payload = new HashMap<>();
        payload.put("usrName", name);

        String jwt = jjwtUtil.sign(payload,expiration,"abcd");
        Cookie cookie =  new Cookie("token",jwt);
        cookie.setMaxAge(10*60);
        cookie.setPath("/");
        return cookie;
    }

    @Override
    public Boolean isLogin(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> "token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .anyMatch(cookie -> isTrueToken(cookie));
    }

    @Override
    public  Map getTokenMap(String token) {
        return jjwtUtil.verify(token, "abcd");
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
