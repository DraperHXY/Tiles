package com.mutesaid.utils;

import com.danga.MemCached.MemCachedClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class CacheUtil {
    @Autowired
    private MemCachedClient memCachedClient;

    private static CacheUtil cacheUtil;

    private static Logger logger = LogManager.getLogger(CacheUtil.class);

    @PostConstruct
    public void init(){
        cacheUtil = this;
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Supplier<T> supplier){
        T result = (T) cacheUtil.memCachedClient.get(key);
        if (result!=null){
            logger.info("缓存");
            return result;
        }else {
            logger.info("数据库");
            T bdResult = supplier.get();
            set(key, bdResult);
            return bdResult;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Function<String, T> function){
        T result = (T) cacheUtil.memCachedClient.get(key);
        if (result!=null){
            logger.info("缓存");
            return result;
        }else {
            logger.info("数据库");
            T bdResult = function.apply(key);
            set(key, bdResult);
            return bdResult;
        }
    }

    private static <T> void set(String key, T obj){
        cacheUtil.memCachedClient.set(key,obj);
    }
}
