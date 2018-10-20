package com.mutesaid.utils;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author TwT
 */
@Component
public class ResponseBo {
    @Autowired
    private MessageSource messageSource;

    public Map msg(String msg) {
        Gson gson = new Gson();
        String error = messageSource.getMessage(msg,null, null);
        return gson.fromJson(error, Map.class);
    }

    public Map msg(String msg, Object data) {
        Gson gson = new Gson();
        String error = messageSource.getMessage(msg,null, null);
        Map map = gson.fromJson(error, Map.class);
        map.put("data",data);
        return map;
    }
}
