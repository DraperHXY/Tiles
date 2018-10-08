package com.mutesaid.service.impl;

import com.google.gson.Gson;
import com.mutesaid.mapper.UsrMapper;
import com.mutesaid.pojo.Usr;
import com.mutesaid.service.UsrService;
import com.mutesaid.utils.DESUtil;
import com.mutesaid.utils.JWTUtil;
import com.mutesaid.utils.MD5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsrServiceImpl implements UsrService {
    @Autowired
    private UsrMapper usrMapper;

    @Autowired
    private MD5Util md5Util;

    @Autowired
    private DESUtil desUtil;

    @Autowired
    private JWTUtil jwtUtil;

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
        String header = "{\"type\":\"JWT\",\"alg\":\"HS256\"}";
        String payload = "{\"usrname\":\"" + name + "\"}";
        String base64Header = new BASE64Encoder().encode(header.getBytes());
        String base64Payload = new BASE64Encoder().encode(payload.getBytes());

        String signature = jwtUtil.encodeHmac(base64Header + "." + base64Payload, "abcd");

        String jwt = base64Header + "." + base64Payload + "." +signature;
        Cookie cookie = new Cookie("usrToken", jwt);
        cookie.setMaxAge(60*10);
        return cookie;
    }

    @Override
    public Map getTokenMap(String token) {
        /*if(isTrueToken(token)){
            String[] jwt = token.split("\\.");
            Gson gson = new Gson();
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] content = decoder.decodeBuffer(jwt[1]);
            return gson.fromJson(jwt[1], Map.class);
        }else {
            return null;
        }*/
        Map<String, String> map = new HashMap<>();
        map.put("usrName","jack");

        return map;

    }

    @Override
    public Boolean isTrueToken(String token) {
        String[] jwt = token.split("\\.");
        String signature = jwtUtil.encodeHmac(jwt[0] + "." + jwt[1], "abcd");
        return jwt[2].equals(signature);
    }
}
