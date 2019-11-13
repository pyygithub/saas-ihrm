package com.pyy.ihrm.domain.company.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ---------------------------
 * 企业信息 (Company)         
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-12 10:10:10
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@ApiModel(value = "CompanySaveOrUpdateVO",description = "企业信息保存和修改VO类")
public class CompanySaveOrUpdateVO {

	@ApiModelProperty("ID")
	private String id;
	@ApiModelProperty("公司名称")
	private String name;
	@ApiModelProperty("当前余额")
	private Double balance;
	@ApiModelProperty("状态")
	private Integer state;
	@ApiModelProperty("审核状态")
	private String auditState;
	@ApiModelProperty("备注")
	private String remarks;
	@ApiModelProperty("所属行业")
	private String industry;
	@ApiModelProperty("公司规模")
	private String companySize;
	@ApiModelProperty("邮箱")
	private String mailbox;
	@ApiModelProperty("公司电话")
	private String companyPhone;
	@ApiModelProperty("法人代表")
	private String legalRepresentative;
	@ApiModelProperty("营业执照-图片ID")
	private String businessLicenseId;
	@ApiModelProperty("公司地址")
	private String companyAddress;
	@ApiModelProperty("公司地区")
	private String companyArea;
	@ApiModelProperty("到期时间")
	private java.util.Date expirationDate;
	@ApiModelProperty("续期时间")
	private java.util.Date renewalDate;
	@ApiModelProperty("当前版本")
	private String version;
	@ApiModelProperty("企业登录账号ID")
	private String managerId;
	@ApiModelProperty("操作人ID")
	private String operaterId;
	@ApiModelProperty("操作人名称")
	private String operaterName;
}