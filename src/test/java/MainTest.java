import com.google.gson.Gson;
import com.mutesaid.utils.DESUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.*;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MainTest {
    private Logger logger = LogManager.getLogger(MainTest.class);

    @Autowired
    private DESUtil desUtil;

    @Test
    public void md5() {

        String key = "abc";
        try {
            MessageDigest md5 =MessageDigest.getInstance("MD5");
            byte[] byteL = key.getBytes();
            md5.update(byteL);
            byte[] result = md5.digest();
            StringBuilder buf = new StringBuilder(result.length * 2);
            for(byte b : result) { // 使用String的format方法进行转换
                // 不足两位补0
                buf.append(String.format("%02x", new Integer(b & 0xff)));
            }
            logger.info(buf.toString());

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String encrypt(String content, String key) {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] result = cipher.doFinal(content.getBytes());
            String str = new BASE64Encoder().encode(result);
            return str;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(byte[] content, String key) {
        try{
            Key secretKey = new SecretKeySpec(key.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] result = cipher.doFinal(content);
            return new String(result);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encodeHmacSHA256(byte[] data) throws Exception {
        Key secretKey = new SecretKeySpec("12345678".getBytes(), "HmacSHA256");

        Mac mac = Mac.getInstance(secretKey.getAlgorithm());

        mac.init(secretKey);

        byte[] digest = mac.doFinal(data);

        return new BASE64Encoder().encode(digest);
    }

    public String sign(Map payload, Date expiration) {
        String token = Jwts.builder()
                .setClaims(payload) //私有声明
                .setExpiration(expiration) //过期时间
                .signWith(SignatureAlgorithm.HS256, "abcd") //签名算法以及密钥
                .compact();
        return token;
    }

    public Claims verify(String token){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey("abcd")
                    .parseClaimsJws(token)
                    .getBody();
            return  claims;
        }catch (Exception e){
            return null;
        }
    }

    @Test
    public void desTest() throws Exception {
        // payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "suyi");

        // expiration
        Date expiration = new Date(System.currentTimeMillis() + 10000);

        // sign
        String token = sign(payload, expiration);
        System.out.println(token);

        // verify
        Claims claims = verify(token);
        System.out.println(claims);
    }
}
