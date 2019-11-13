package com.pyy.ihrm.domain.company.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ---------------------------
 * 部门 (Department)         
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-13 10:03:44
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "DepartmentSaveOrUpdateVO",description = "部门保存和修改VO类")
public class DepartmentSaveOrUpdateVO {

	@ApiModelProperty("ID")
	private String id;
	@ApiModelProperty("父级ID")
	private String parentId;
	@ApiModelProperty("企业ID")
	private String companyId;
	@ApiModelProperty("部门编码")
	private String code;
	@ApiModelProperty("部门名称")
	private String name;
	@ApiModelProperty("负责人ID")
	private String managerId;
	@ApiModelProperty("负责人名称")
	private String managerName;
	@ApiModelProperty("介绍")
	private String introduce;
	@ApiModelProperty("操作人ID")
	private String operaterId;
	@ApiModelProperty("操作人名称")
	private String operaterName;
}