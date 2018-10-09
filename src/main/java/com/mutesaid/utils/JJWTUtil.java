package com.mutesaid.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JJWTUtil {
    public String sign(Map<String, Object> payload, Date expiration, String key){
        return Jwts.builder()
                .setClaims(payload)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
    }

    public Map verify(String token, String key){
        try{
            return Jwts.parser()
                    .setSigningKey(key.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }

}
