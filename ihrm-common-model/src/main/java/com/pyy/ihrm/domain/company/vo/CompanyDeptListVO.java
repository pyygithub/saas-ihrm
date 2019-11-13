package com.pyy.ihrm.domain.company.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ---------------------------
 * 企业部门 (Department)
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-13 10:03:44
 * 版本：  v1.0
 * ---------------------------
 */
@Data
@NoArgsConstructor
@ApiModel(value = "CompanyDeptListVO",description = "企业部门列表类")
public class CompanyDeptListVO {

	@ApiModelProperty("企业ID")
    private String companyId;
    @ApiModelProperty("企业名称")
    private String companyName;
    @ApiModelProperty("企业联系人（法人代表）")
    private String companyManager;
    @ApiModelProperty("部门列表")
    private List<DepartmentVO> departmentVOList;

    public CompanyDeptListVO(CompanyVO company, List<DepartmentVO> departmentVOList) {
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyManager = company.getLegalRepresentative();
        this.departmentVOList = departmentVOList;
    }
}