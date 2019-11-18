package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * ========================
 * 用户登录VO类
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/18 10:07
 * Version: v1.0
 * ========================
 */
@Data
@ApiModel(value = "PermissionApiVO",description = "API接口权限类")
public class LoginUserVO {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String code;

    private String uuid = "";
}
