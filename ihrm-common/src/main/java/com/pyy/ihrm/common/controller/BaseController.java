package com.pyy.ihrm.common.controller;

import com.pyy.ihrm.common.jwt.JwtInterceptor;
import com.pyy.ihrm.common.utils.Base64Util;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ========================
 * 通用Controller
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/14 15:29
 * Version: v1.0
 * ========================
 */
public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String userId;
    protected String username;
    protected String companyId;
    protected String companyName;
    protected Claims claims;

    @ModelAttribute
    public void setResAnReq(HttpServletRequest request,HttpServletResponse response) {
        this.request = request;
        this.response = response;

        Object obj = request.getAttribute(JwtInterceptor.USER_CLAIMS);

        if(obj != null) {
            this.claims = (Claims) obj;
            this.userId = Base64Util.decode((String)claims.get("userId"));
            this.username = claims.getSubject();
            this.companyId = (String)claims.get("companyId");
            this.companyName = (String)claims.get("companyName");
        }
    }

}
