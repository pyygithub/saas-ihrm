package com.pyy.ihrm.company.service;


import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.domain.company.vo.DepartmentQueryConditionVO;
import com.pyy.ihrm.domain.company.vo.DepartmentSaveOrUpdateVO;
import com.pyy.ihrm.domain.company.vo.DepartmentVO;

import java.util.List;

/**
 * ---------------------------
 * 部门 (DepartmentService)         
 * ---------------------------
 * 作者：  
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
     * 部门模糊查询
     * @param queryConditionVO
     * @return
     */
    public List<DepartmentVO> listByParams(DepartmentQueryConditionVO queryConditionVO);


    /**
     * 部门分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
    public QueryResult<DepartmentVO> listByPageAndParams(DepartmentQueryConditionVO queryConditionVO, Integer page, Integer size);
}
