package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ---------------------------
 * 用户 (User)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "UserSaveOrUpdateVO",description = "用户保存和修改VO类")
public class UserSaveOrUpdateVO {

	@ApiModelProperty("工号")
	private String workNumber;
	@ApiModelProperty("企业ID")
	private String companyId;
	@ApiModelProperty("企业名称")
	private String companyName;
	@ApiModelProperty("部门ID")
	private String departmentId;
	@ApiModelProperty("用户名称")
	private String username;
	@ApiModelProperty("密码")
	private String password;
	@ApiModelProperty("用户级别：1 平台管理员 2 企业管理员 3 企业用户")
	private String level;
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
	@ApiModelProperty("操作人ID")
	private String operaterId;
	@ApiModelProperty("操作人名称")
	private String operaterName;


}