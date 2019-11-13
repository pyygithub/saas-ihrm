package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ---------------------------
 * 用户 (User)         
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "UserVO",description = "用户类")
public class UserVO {

	@ApiModelProperty("ID")
    private String id;
	@ApiModelProperty("工号")
    private String workNumber;
	@ApiModelProperty("部门ID")
    private String departmentId;
	@ApiModelProperty("用户名称")
    private String username;
	@ApiModelProperty("密码")
    private String password;
	@ApiModelProperty("手机号码")
    private String mobile;
	@ApiModelProperty("入职时间")
    private java.util.Date timeOfEntry;
	@ApiModelProperty("聘用形式")
    private String formOfEmployment;
	@ApiModelProperty("管理形式")
    private String formOfManagement;
	@ApiModelProperty("工作城市")
    private String workingCity;
	@ApiModelProperty("转正时间")
    private java.util.Date correctionTime;
	@ApiModelProperty("在职状态 1：在职  2：离职")
    private String inServiceStatus;
	@ApiModelProperty("启用状态 0：禁用  1：启用")
    private String enableState;
	@ApiModelProperty("创建人ID")
    private String createId;
	@ApiModelProperty("创建人名称")
    private String createName;
	@ApiModelProperty("创建时间")
    private java.util.Date createTime;
	@ApiModelProperty("更新人ID")
    private String updateId;
	@ApiModelProperty("更新人名称")
    private String updateName;
	@ApiModelProperty("更新时间")
    private java.util.Date updateTime;
	@ApiModelProperty("删除标记 0：正常 1：删除")
    private Integer isDeleted;

}