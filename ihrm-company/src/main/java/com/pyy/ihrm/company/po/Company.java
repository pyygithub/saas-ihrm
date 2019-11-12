package com.pyy.ihrm.company.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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
@Table(name = "company")
public class Company implements Serializable{

	private static final long serialVersionUID = 8294633490615151005L;
	/** ID */
	@Id
	private String id;
	/** 公司名称 */
	private String name;
	/** 当前余额 */
	private Double balance;
	/** 状态 */
	private Integer state;
	/** 审核状态 */
	private String auditState;
	/** 备注 */
	private String remarks;
	/** 所属行业 */
	private String industry;
	/** 公司规模 */
	private String companySize;
	/** 邮箱 */
	private String mailbox;
	/** 公司电话 */
	private String companyPhone;
	/** 法人代表 */
	private String legalRepresentative;
	/** 营业执照-图片ID */
	private String businessLicenseId;
	/** 公司地址 */
	private String companyAddress;
	/** 公司地区 */
	private String companyArea;
	/** 到期时间 */
	private java.util.Date expirationDate;
	/** 续期时间 */
	private java.util.Date renewalDate;
	/** 当前版本 */
	private String version;
	/** 企业登录账号ID */
	private String managerId;
	/** 创建人ID */
	private String createId;
	/** 创建人名称 */
	private String createName;
	/** 创建时间 */
	private java.util.Date createTime;
	/** 更新人ID */
	private String updateId;
	/** 更新人名称 */
	private String updateName;
	/** 更新时间 */
	private java.util.Date updateTime;
	/** 删除标记 0：正常 1：删除 */
	private Integer isDeleted;

}