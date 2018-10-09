package com.mutesaid.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class JWTUtil {
    public String encodeHmac(String data, String key) {
        try{
            Key secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            byte[] digest = mac.doFinal(data.getBytes());
            return new String(Base64.getEncoder().encodeToString(digest));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
