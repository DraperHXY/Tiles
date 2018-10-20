package com.mutesaid.utils;

import javax.servlet.http.Cookie;

public class CookieUtil {

    public static Cookie addCookie(String key, String value){
        Cookie cookie =  new Cookie(key,value);
        cookie.setMaxAge(10*60);
        cookie.setPath("/");
        return cookie;
    }

    public static Cookie killCookie(String key){
        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }

}
