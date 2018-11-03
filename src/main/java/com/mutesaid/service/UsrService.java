package com.mutesaid.service;

import com.mutesaid.pojo.Usr;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.Cookie;

@Service
public interface UsrService {
    void insert(Usr usr, BindingResult error);

    Boolean hasUsrName(String name);

    void isTrue(String name, String pwd);

    Boolean pwdMatch(String pwd, Usr usr);

    Cookie setToken(String name);
}
