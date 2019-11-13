package com.pyy.ihrm.domain.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * ---------------------------
 * 权限 (Permission)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "PermissionSaveOrUpdateVO",description = "权限保存和修改VO类")
public class PermissionSaveOrUpdateVO {

	@ApiModelProperty("父节点id")
	private String parentId;
	@ApiModelProperty("企业ID")
	private String companyId;
	@ApiModelProperty("权限名称")
	private String name;
	@ApiModelProperty("权限标识")
	private String code;
	@NotBlank(message = "权限类型不能为空（1为菜单 2为功能 3为API）")
	@ApiModelProperty("权限类型: 1为菜单 2为功能 3为API")
	private String type;
	@ApiModelProperty("权限描述")
	private String description;
	@ApiModelProperty("企业可见状态: 0 不可见 1 可见")
	private String enVisible;
	@ApiModelProperty("操作人ID")
	private String operaterId;
	@ApiModelProperty("操作人名称")
	private String operaterName;

	@ApiModelProperty("菜单")
	private PermissionMenuSaveOrUpdateVO menuSaveOrUpdateVO;
	@ApiModelProperty("按钮")
	private PermissionButtonSaveOrUpdateVO buttonSaveOrUpdateVO;
	@ApiModelProperty("API接口")
	private PermissionApiSaveOrUpdateVO apiSaveOrUpdateVO;

}