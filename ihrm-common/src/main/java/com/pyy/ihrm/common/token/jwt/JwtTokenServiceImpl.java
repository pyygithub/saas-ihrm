package com.pyy.ihrm.common.token.jwt;

import com.pyy.ihrm.common.exception.CustomException;
import com.pyy.ihrm.common.response.ResultCode;
import com.pyy.ihrm.common.token.TokenConfig;
import com.pyy.ihrm.common.token.TokenService;
import com.pyy.ihrm.common.utils.Base64Util;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * ========================
 * 基于JWT的TokenService实现类
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/18 10:47
 * Version: v1.0
 * ========================
 */
@Slf4j
@Component
public class JwtTokenServiceImpl implements TokenService {
    @Override
    public String createToken(String userId, String username, Map<String, Object> extAttribute, TokenConfig tokenConfig) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenConfig.getBase64Secret());
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            //userId是重要信息，进行加密下
            String encryUserId = Base64Util.encode(userId);

            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    // 可以将基本不重要的对象信息放到claims
                    .claim("userId", encryUserId)
                    .setSubject(username)               // 代表这个JWT的主体，即它的所有人
                    .setIssuer(tokenConfig.getClientId()) // 代表这个JWT的签发主体；
                    .setIssuedAt(new Date())            // 是一个时间戳，代表这个JWT的签发时间；
                    .setAudience(tokenConfig.getName())   // 代表这个JWT的接收对象；
                    .signWith(signatureAlgorithm, signingKey);
            //添加JWT扩展属性
            for (Map.Entry<String, Object> entry : extAttribute.entrySet()) {
                builder.claim(entry.getKey(), entry.getValue());
            }

            //添加Token过期时间
            int TTLMillis = tokenConfig.getExpiresSecond();
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

    @Override
    public Object parseToken(String token, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (ExpiredJwtException eje) {
            log.info("===== Token过期 =====");
            throw new CustomException(ResultCode.TOKEN_EXPIRED);
        } catch (Exception e){
            log.info("===== token解析异常 =====");
            throw new CustomException(ResultCode.TOKEN_INVALID);
        }
    }
}
