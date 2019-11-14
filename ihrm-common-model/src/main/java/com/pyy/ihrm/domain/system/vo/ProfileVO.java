package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------
 * 用户配置信息类
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "ProfileVO",description = "用户配置信息类")
public class ProfileVO {

	@ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("企业名称")
    private String companyName;

    @ApiModelProperty("权限信息")
    private Map<String, Object> permissions = new HashMap<>();
}