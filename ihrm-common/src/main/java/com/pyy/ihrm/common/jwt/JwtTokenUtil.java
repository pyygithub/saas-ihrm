package com.pyy.ihrm.common.jwt;

import com.pyy.ihrm.common.exception.CustomException;
import com.pyy.ihrm.common.response.ResultCode;
import com.pyy.ihrm.common.utils.Base64Util;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * ========================
 * JWT工具类
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/14 9:26
 * Version: v1.0
 * ========================
 */
@Slf4j
public class JwtTokenUtil {

    /**
     * 解析jwt
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public  Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (ExpiredJwtException eje) {
            log.error("===== Token过期 =====", eje);
            throw new CustomException(ResultCode.TOKEN_EXPIRED);
        } catch (Exception e){
            log.error("===== token解析异常 =====", e);
            throw new CustomException(ResultCode.TOKEN_INVALID);
        }
    }

    /**
     * 构建jwt
     * @param userId
     * @param username
     * @param extAttribute
     * @param jwtConfig
     * @return
     */
    public  String createJWT(String userId, String username, Map<String, Object> extAttribute, JwtConfig jwtConfig) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtConfig.getBase64Secret());
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            //userId是重要信息，进行加密下
            String encryUserId = Base64Util.encode(userId);

            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    // 可以将基本不重要的对象信息放到claims
                    .claim("userId", encryUserId)
                    .setSubject(username)               // 代表这个JWT的主体，即它的所有人
                    .setIssuer(jwtConfig.getClientId()) // 代表这个JWT的签发主体；
                    .setIssuedAt(new Date())            // 是一个时间戳，代表这个JWT的签发时间；
                    .setAudience(jwtConfig.getName())   // 代表这个JWT的接收对象；
                    .signWith(signatureAlgorithm, signingKey);
            //添加JWT扩展属性
            for (Map.Entry<String, Object> entry : extAttribute.entrySet()) {
                builder.claim(entry.getKey(), entry.getValue());
            }

            //添加Token过期时间
            int TTLMillis = jwtConfig.getExpiresSecond();
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
                        .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
            }

            //生成JWT
            return builder.compact();
        } catch (Exception e) {
            log.error("签名失败", e);
            throw new CustomException(ResultCode.PERMISSION_SIGNATURE_ERROR);
        }
    }

    /**
     * 从token中获取用户名
     * @param token
     * @param base64Security
     * @return
     */
    public  String getUsername(String token, String base64Security){
        return parseJWT(token, base64Security).getSubject();
    }

    /**
     * 从token中获取用户ID
     * @param token
     * @param base64Security
     * @return
     */
    public  String getUserId(String token, String base64Security){
        String userId = parseJWT(token, base64Security).get("userId", String.class);
        return Base64Util.decode(userId);
    }

    /**
     * 是否已过期
     * @param token
     * @param base64Security
     * @return
     */
    public  boolean isExpiration(String token, String base64Security) {
        return parseJWT(token, base64Security).getExpiration().before(new Date());
    }
}
