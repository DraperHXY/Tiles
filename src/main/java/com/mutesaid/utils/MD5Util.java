package com.mutesaid.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

@Component
public class MD5Util {
    private Logger logger = LogManager.getLogger(MD5Util.class);

    public String encrypt(String str, String salt) {
        str += salt;
        try {
            // 返回实现指定摘要算法的 MessageDigest 对象
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 使用指定的 byte 数组更新摘要
            md5.update(str.getBytes());
            // 完成哈希计算
            byte[] result = md5.digest();

            StringBuilder buf = new StringBuilder(result.length * 2);

            for(byte b : result) {
                // 使用String的format方法进行转换
                // 先把字节转成int再转成16进制，不足两位补0
                buf.append(String.format("%02x", new Integer(b & 0xff)));
            }
            return buf.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
