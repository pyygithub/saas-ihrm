package com.pyy.ihrm.system.service;

import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.domain.system.vo.RoleQueryConditionVO;
import com.pyy.ihrm.domain.system.vo.RoleSaveOrUpdateVO;
import com.pyy.ihrm.domain.system.vo.RoleVO;

import java.util.List;

/**
 * ---------------------------
 * 角色 (RoleService)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
public interface RoleService {

    /**
     * 角色保存
     * @param roleSaveOrUpdateVO
     */
	void save(RoleSaveOrUpdateVO roleSaveOrUpdateVO);

	/**
     * 角色修改
     * @param id
     * @param roleSaveOrUpdateVO
     */
    void update(String id, RoleSaveOrUpdateVO roleSaveOrUpdateVO);

    /**
     * 角色删除
     * @param id
     * @param userId
     * @param username
     */
	void delete(String id, String userId, String username);

    /**
     * 根据角色ID查询
     * @param id
     */
	RoleVO findById(String id);

    /**
     * 角色模糊查询
     * @param queryConditionVO
     * @return
     */
    List<RoleVO> listByParams(RoleQueryConditionVO queryConditionVO);


    /**
     * 角色分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
    QueryResult<RoleVO> listByPageAndParams(RoleQueryConditionVO queryConditionVO, Integer page, Integer size);

    /**
     * 分配权限
     * @param roleId
     * @param permissionIds
     */
    void assignPermissions(String roleId, List<String> permissionIds);
}
