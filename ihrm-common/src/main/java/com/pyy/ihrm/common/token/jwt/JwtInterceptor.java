package com.pyy.ihrm.common.token.jwt;

import com.pyy.ihrm.common.exception.CustomException;
import com.pyy.ihrm.common.response.ResultCode;
import com.pyy.ihrm.common.token.TokenConfig;
import com.pyy.ihrm.common.token.TokenService;
import com.pyy.ihrm.common.token.annonation.RequiresPermissions;
import com.pyy.ihrm.common.token.annonation.TokenIgnore;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ========================
 * JWT鉴权拦截器
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/14 14:45
 * Version: v1.0
 * ========================
 */
@Slf4j
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenConfig tokenConfig;

    @Autowired
    private TokenService tokenService;

    public static final String USER_CLAIMS = "user_claims";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        log.info("### 请求地址：{} ###", request.getRequestURL());
        HandlerMethod handlerMethod = null;
        if (handler instanceof HandlerMethod) {
            handlerMethod = (HandlerMethod) handler;
        }
        // 忽略带JwtIgnore注解的请求, 不做后续token认证校验
        TokenIgnore tokenIgnore = handlerMethod.getMethodAnnotation(TokenIgnore.class);
        if (tokenIgnore != null) {
            return true;
        }
        // 请求中获取key为Authorization的头信息
        String authorization = request.getHeader(tokenConfig.getTokenKey());
        log.info("### authorization={} ###", authorization);
        // 判断请求头信息是否为空，或者是否已Bearer开头
        if (!StringUtils.isEmpty(authorization)  && authorization.startsWith(tokenConfig.getTokenPrefix())) {
            // 前后端约定头信息内容以 Bearer+空格+token 形式组成
            String token = authorization.replace("Bearer ", "");
            // 验证token，并返回claims
            Claims claims = (Claims) tokenService.parseToken(token, tokenConfig.getBase64Secret());
            if (claims != null) {
                // 通过claims获取到当前用户的可访问API权限字符串
                String apis = (String) claims.get("apis"); //api-user-delete,api-user-update
                // 获取@Secured注解
                RequiresPermissions requiresPermissions = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
                if (requiresPermissions != null && requiresPermissions.value() != null) {
                    // 获取当前请求接口授权地址
                    String apiCode = requiresPermissions.value();
                    // 判断当前用户是否具有相应的请求权限
                    if (apis.contains(apiCode)) {
                        request.setAttribute(USER_CLAIMS, claims);
                        log.info("### 鉴权通过，放行请求 ###");
                        return true;
                    }
                }
                log.info("### 权限不足，禁止访问 ###");
                throw new CustomException(ResultCode.UNAUTHORISE);
            }
        }
        log.info("### 用户未登录，请先登录 ###");
        throw new CustomException(ResultCode.UNAUTHENTICATED);
    }
}
