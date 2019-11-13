package com.pyy.ihrm.system.service;

import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.domain.system.vo.PermissionQueryConditionVO;
import com.pyy.ihrm.domain.system.vo.PermissionSaveOrUpdateVO;
import com.pyy.ihrm.domain.system.vo.PermissionVO;

import java.util.List;

/**
 * ---------------------------
 * 权限 (PermissionService)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
public interface PermissionService {

    /**
     * 权限保存
     * @param permissionSaveOrUpdateVO
     */
	public void save(PermissionSaveOrUpdateVO permissionSaveOrUpdateVO);

	/**
     * 权限修改
     * @param id
     * @param permissionSaveOrUpdateVO
     */
    public void update(String id, PermissionSaveOrUpdateVO permissionSaveOrUpdateVO);

    /**
     * 权限删除
     * @param id
     * @param userId
     * @param username
     */
	public void delete(String id, String userId, String username);

    /**
     * 根据权限ID查询
     * @param id
     */
	public PermissionVO findById(String id);

    /**
     * 权限模糊查询
     * @param queryConditionVO
     * @return
     */
    public List<PermissionVO> listByParams(PermissionQueryConditionVO queryConditionVO);

}
