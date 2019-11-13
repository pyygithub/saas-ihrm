package com.pyy.ihrm.company.service;


import com.pyy.ihrm.domain.company.vo.CompanyDeptListVO;
import com.pyy.ihrm.domain.company.vo.DepartmentQueryConditionVO;
import com.pyy.ihrm.domain.company.vo.DepartmentSaveOrUpdateVO;
import com.pyy.ihrm.domain.company.vo.DepartmentVO;

import java.util.List;

/**
 * ---------------------------
 * 部门 (DepartmentService)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:03:44
 * 版本：  v1.0
 * ---------------------------
 */
public interface DepartmentService {

    /**
     * 部门保存
     * @param departmentSaveOrUpdateVO
     */
	public void save(DepartmentSaveOrUpdateVO departmentSaveOrUpdateVO);

	/**
     * 部门修改
     * @param id
     * @param departmentSaveOrUpdateVO
     */
    public void update(String id, DepartmentSaveOrUpdateVO departmentSaveOrUpdateVO);

    /**
     * 部门删除
     * @param id
     * @param userId
     * @param username
     */
	public void delete(String id, String userId, String username);

    /**
     * 根据部门ID查询
     * @param id
     */
	public DepartmentVO findById(String id);

    /**
     * 根据条件模糊查询所有部门信息
     * @param queryConditionVO
     * @return
     */
    public List<DepartmentVO> findByParams(DepartmentQueryConditionVO queryConditionVO);

    /**
     * 根据条件模糊查询某企业下部门信息
     * @param companyId
     * @param queryConditionVO
     * @return
     */
    public CompanyDeptListVO findByCompanyIdAndParams(String companyId, DepartmentQueryConditionVO queryConditionVO);
}
