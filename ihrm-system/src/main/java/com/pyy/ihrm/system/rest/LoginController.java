package com.pyy.ihrm.system.rest;

import com.pyy.ihrm.common.controller.BaseController;
import com.pyy.ihrm.common.token.annonation.RequiresPermissions;
import com.pyy.ihrm.common.token.annonation.TokenIgnore;
import com.pyy.ihrm.common.response.Result;
import com.pyy.ihrm.common.utils.SnowflakeId;
import com.pyy.ihrm.domain.system.vo.LoginUserVO;
import com.pyy.ihrm.domain.system.vo.ProfileVO;
import com.pyy.ihrm.domain.system.vo.ValidateImgVO;
import com.pyy.ihrm.system.service.UserService;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * ========================
 * 登录相关Controller
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/18 10:12
 * Version: v1.0
 * ========================
 */
@Api(tags = "LoginController", description = "登录相关Controller")
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
public class LoginController extends BaseController{

    private static final String CODE_KEY = "code_";

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取图形验证码
     * @return
     */
    @ApiOperation(value = "获取图形验证码", notes = "获取图形（算数）验证码")
    @TokenIgnore
    @GetMapping("/code")
    public Result getCode() {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果：5
        String result = captcha.text();
        String uuid = CODE_KEY + SnowflakeId.getId();
        ValidateImgVO validateImgVO = new ValidateImgVO(captcha.toBase64(), uuid);
        // 保存验证码到redis
        stringRedisTemplate.opsForValue().set(uuid, result);
        return Result.SUCCESS(validateImgVO);
    }

    /**
     * 用户登录
     * @param loginUser
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParam(name = "loginUser", value = "用户登录信息", required = true, dataType = "LoginUserVO", paramType = "body")
    @TokenIgnore
    @PostMapping("/login")
    public Result login(@Valid @RequestBody LoginUserVO loginUser) {
        String token = userService.login(loginUser);
        return Result.SUCCESS(token);
    }

    @ApiOperation("退出登录")
    @TokenIgnore
    @DeleteMapping(value = "/logout")
    public Result logout(HttpServletRequest request){
        // 清除redis 等业务
        return Result.SUCCESS();
    }


    /**
     * 获取个人信息
     * @return
     */
    @ApiOperation(value = "获取个人信息", notes = "获取个人信息")
    @RequiresPermissions("API-USER-PROFILE")
    @GetMapping("/profile")
    public Result profile() {
        ProfileVO profileVO = userService.profile(userId);
        return Result.SUCCESS(profileVO);
    }


}
