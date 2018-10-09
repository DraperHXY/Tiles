package com.mutesaid.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class DESUtil {
    @Autowired
    private MD5Util md5Util;

    public String encrypt(String msg, String key) {
        try {
            String key32 = md5Util.encrypt(key,"");

            Key secretKey = new SecretKeySpec(key32.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(key32.substring(0, 16).getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] result = cipher.doFinal(msg.getBytes());

            return Base64.getEncoder().encodeToString(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String msg, String key) {
        try{
            String key32 = md5Util.encrypt(key,"");
            byte[] content = Base64.getDecoder().decode(key32);

            Key secretKey = new SecretKeySpec(key32.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(key32.substring(0, 16).getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] result = cipher.doFinal(content);
            return new String(result);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
