package com.pyy.ihrm.system.service;

import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.domain.system.vo.UserQueryConditionVO;
import com.pyy.ihrm.domain.system.vo.UserRolesVO;
import com.pyy.ihrm.domain.system.vo.UserSaveOrUpdateVO;
import com.pyy.ihrm.domain.system.vo.UserVO;

import javax.validation.Valid;
import java.util.List;

/**
 * ---------------------------
 * 用户 (UserService)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
public interface UserService {

    /**
     * 用户保存
     * @param userSaveOrUpdateVO
     */
	void save(UserSaveOrUpdateVO userSaveOrUpdateVO);

	/**
     * 用户修改
     * @param id
     * @param userSaveOrUpdateVO
     */
    void update(String id, UserSaveOrUpdateVO userSaveOrUpdateVO);

    /**
     * 用户删除
     * @param id
     * @param userId
     * @param username
     */
	void delete(String id, String userId, String username);

    /**
     * 根据用户ID查询
     * @param id
     */
    UserRolesVO findById(String id);

    /**
     * 用户模糊查询
     * @param queryConditionVO
     * @return
     */
    List<UserVO> listByParams(UserQueryConditionVO queryConditionVO);


    /**
     * 用户分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
    QueryResult<UserVO> listByPageAndParams(UserQueryConditionVO queryConditionVO, Integer page, Integer size);

    /**
     * 分配角色
     * @param userId
     * @param roleIds
     */
    void assignRoles(String userId, List<String> roleIds);
}
