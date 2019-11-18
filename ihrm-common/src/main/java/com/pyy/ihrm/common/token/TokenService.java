package com.pyy.ihrm.common.token;

import java.util.Map;

/**
 * ========================
 * token工具Service接口
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/18 10:46
 * Version: v1.0
 * ========================
 */
public interface TokenService {

    /**
     * 生成token
     * @param userId
     * @param username
     * @param extAttribute
     * @param tokenConfig
     * @return
     */
    String createToken(String userId, String username, Map<String, Object> extAttribute, TokenConfig tokenConfig);

    /**
     * 验证（解析）token
     * @param token
     * @param base64Security
     * @return
     */
    Object parseToken(String token, String base64Security);
}
