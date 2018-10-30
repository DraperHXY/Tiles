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
import org.springframework.validation.BindingResult;

import javax.servlet.http.Cookie;
import java.util.*;

@Service
public class UsrServiceImpl implements UsrService {
    private static final String JWT_KEY = "www.jnshu.com";

    @Autowired
    private UsrMapper usrMapper;

    private Logger logger = LogManager.getLogger(UsrServiceImpl.class);

    @Override
    public void insert(Usr usr, BindingResult error) {
        if (error.hasErrors()){
            logger.info("用户参数校验失败");
            String msg = Objects.requireNonNull(error.getFieldError()).getDefaultMessage();
            throw new IllegalArgumentException(msg);
        }
        Boolean beUsed = hasUsrName(usr.getName());
        if (beUsed) {
            logger.info("用户名被占用");
            throw new IllegalArgumentException("Beused.usr.name");
        }

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
    public void isTrue(String name, String pwd) {
        Usr usr = usrMapper.getByName(name);
        if(usr==null){
            logger.info("用户名不存在");
            throw new IllegalArgumentException("Input.usr.null");
        }
        Boolean isMatch = pwdMatch(pwd, usr);
        if(!isMatch){
            logger.info("密码错误");
            throw new IllegalArgumentException("Input.usr.match");
        }
    }

    @Override
    public Boolean pwdMatch(String pwd, Usr usr) {
        String pwdIn = MD5Util.encrypt(pwd, usr.getUpdateAt().toString());
        return usr.getPwd().equals(pwdIn);
    }

    @Override
    public Cookie setToken(String name) {
        Map<String, Object> payload = new HashMap<>(1);
        payload.put("usrName", name);

        String jwt = JJWTUtil.sign(payload, JWT_KEY);
        return CookieUtil.addCookie("token", jwt);
    }
}
