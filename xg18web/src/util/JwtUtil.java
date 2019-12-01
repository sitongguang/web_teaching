package util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * 如果没有javax.xml.bind.jar，则会报DatatypeConverter类找不到
 * 现在问题是：运行main方法没问题，但是运行Tomcat 9，会出问题：
 * EVERE [RMI TCP Connection(47)-127.0.0.1] org.apache.catalina.startup.ContextConfig.processAnnotationsJar Unable to process Jar entry [module-info.class] from Jar [file:/D:/workspace/xg18/out/artifacts/xg18web_war_exploded/WEB-INF/lib/jackson-core-2.10.0.pr1.jar] for annotations
 *  org.apache.tomcat.util.bcel.classfile.ClassFormatException: Invalid byte tag in constant pool: 19
 */
public class JwtUtil {
    // token秘钥  太短会报错
    public static String SECRET = "qwerasdfdxzvdfajjlkjeiojznvxndjkfaowijeiodl";

    /**
     * 生成Jwt的方法
     *
     * @param id
     *            用户ID
     * @param subject
     *            用户昵称
     * @param ttlMillis
     *            过期时间(Time To Live)
     * @return Token String 凭证
     */
    public static String createJWT(String id, String subject, long ttlMillis) {
        // 签名方法 HS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成Jwt的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成秘钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        //SecretKeySpec产生的Key会有问题：
        //Exception in thread "main" io.jsonwebtoken.security.WeakKeyException: The signing key's size is 240 bits which is not secure enough for the HS256 algorithm.  The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HS256 MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size).  Consider using the io.jsonwebtoken.security.Keys class's 'secretKeyFor(SignatureAlgorithm.HS256)' method to create a key guaranteed to be secure enough for HS256.  See https://tools.ietf.org/html/rfc7518#section-3.2 for more information.
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        Key signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // 设置JWT所存储的信息
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).signWith(signingKey,
                signatureAlgorithm);

        //builder.claim("name", "value"); //存储自定义信息

        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // 构建JWT并将其序列化为紧凑的URL安全字符串
        return builder.compact();
    }

    /**
     * 解析Jwt字符串
     *
     * @param jwt
     *            Jwt字符串
     * @return Claims 解析后的对象
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET)).parseClaimsJws(jwt).getBody();
    }

    /**
     * 验证JWT
     *
     * @param jwtStr jwt字符串
     * @return JOSNObject 解析结果<br/>
     *         &emsp;&emsp;Success 成功标识<br/>
     *         &emsp;&emsp;&emsp;&emsp;true：成功<br/>
     *         &emsp;&emsp;&emsp;&emsp;false：失败<br/>
     *         &emsp;&emsp;Claim 声明对象<br/>
     *         &emsp;&emsp;ErrCode 错误码<br/>
     *         &emsp;&emsp;&emsp;&emsp;1005：过期<br/>
     *         &emsp;&emsp;&emsp;&emsp;1004：未登录
     */
    public static JSONObject validateJWT(String jwtStr) {
        JSONObject message = new JSONObject();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            message.put("Success", true);
            message.put("Claims", claims);
        } catch (ExpiredJwtException e) {
            message.put("Success", false);
            message.put("ErrCode", 1005);
            e.printStackTrace();
        } catch (Exception e) {
            message.put("Success", false);
            message.put("ErrCode", 1004);
            e.printStackTrace();
        }
        return message;
    }

    public static void main(String[] args) {
        String jwt = createJWT("1","stone",2000);
        System.out.println(jwt);
    }
}
