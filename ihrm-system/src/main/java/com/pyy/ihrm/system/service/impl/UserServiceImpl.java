package com.pyy.ihrm.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.common.response.ResultCode;
import com.pyy.ihrm.common.utils.SnowflakeId;
import com.pyy.ihrm.domain.system.vo.UserQueryConditionVO;
import com.pyy.ihrm.domain.system.vo.UserRolesVO;
import com.pyy.ihrm.domain.system.vo.UserSaveOrUpdateVO;
import com.pyy.ihrm.domain.system.vo.UserVO;
import com.pyy.ihrm.system.constants.CommonConstants;
import com.pyy.ihrm.system.exception.CustomException;
import com.pyy.ihrm.system.mapper.UserMapper;
import com.pyy.ihrm.system.mapper.UserRoleMapper;
import com.pyy.ihrm.system.model.User;
import com.pyy.ihrm.system.model.UserRole;
import com.pyy.ihrm.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * ---------------------------
 * 用户 (UserServiceImpl)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 用户保存
     * @param userSaveOrUpdateVO
     */
	@Override
	public void save(UserSaveOrUpdateVO userSaveOrUpdateVO) {
        // 保存用户
        User userModel = new User();
        BeanUtils.copyProperties(userSaveOrUpdateVO, userModel);
        userModel.setId(SnowflakeId.getId() + "");
        userModel.setInServiceStatus(CommonConstants.IN_SERVICE);// 在职
        userModel.setEnableState(CommonConstants.ENABLE);// 启用
        userModel.setCreateId(userSaveOrUpdateVO.getOperaterId());
        userModel.setCreateName(userSaveOrUpdateVO.getOperaterName());
        userModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userModel.setIsDeleted(CommonConstants.UN_DELETED);

        userMapper.insert(userModel);
        log.info("### 用户保存成功 ###");
	}

	/**
     * 用户修改
     * @param id
     * @param userSaveOrUpdateVO
     */
    @Override
    public void update(String id, UserSaveOrUpdateVO userSaveOrUpdateVO) {
        // 1.根据id查询用户
        User userModel = userMapper.selectByPrimaryKey(id);
        if (userModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "用户");
        }

        // 2.设置用户属性
        BeanUtils.copyProperties(userSaveOrUpdateVO, userModel);
        userModel.setUpdateId(userSaveOrUpdateVO.getOperaterId());
        userModel.setUpdateName(userSaveOrUpdateVO.getOperaterName());
        userModel.setUpdateTime(new Date());

        // 3.更新用户
        userMapper.updateByPrimaryKey(userModel);
        log.info("### 用户修改成功 ###");
    }

    /**
    * 用户删除
    * @param id
    */
	@Override
	public void delete(String id, String userId, String username) {
	    // 1.根据ID查询用户
	    User userModel = userMapper.selectByPrimaryKey(id);
        if (userModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "用户,id=" + id);
        }

        // 2.设置用户属性
        userModel.setId(id);
        userModel.setUpdateId(userId);
        userModel.setUpdateName(username);
        userModel.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userModel.setIsDeleted(CommonConstants.DELETED);

        // 3.更新用户（逻辑删除）
        userMapper.updateByPrimaryKeySelective(userModel);
        log.info("### 用户逻辑删除成功 ###");
	}

    /**
    * 根据用户ID查询
    * @param id
    */
	@Override
	public UserRolesVO findById(String id) {
	    // 1.根据用户查询用户
		User userModel = userMapper.selectByPrimaryKey(id);
		if (userModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "用户,id=" + id);
        }
        log.info("### 用户查询成功, user={}###", JSON.toJSONString(userModel));

		// 2.Model转换VO
        UserRolesVO userRolesVO = new UserRolesVO();
        BeanUtils.copyProperties(userModel, userRolesVO);
        log.info("### 用户Model转换VO成功， userRolesVO={}###", userRolesVO);

        // 3.根据用户ID查询拥有角色ID集合
        Example userRoleExample = new Example(UserRole.class);
        userRoleExample.createCriteria().andEqualTo("userId", id);
        List<UserRole> userRoleList = userRoleMapper.selectByExample(userRoleExample);
        List<String> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        log.info("### 当前用户【userId={}】拥有角色集合【roleIds={}】###", id, roleIds);

        // 4.补全角色ID集合
        userRolesVO.setRoleIds(roleIds);

        return userRolesVO;
	}

    /**
     * 用户模糊查询
     * @param queryConditionVO
     * @return
     */
    @Override
    public List<UserVO> listByParams(UserQueryConditionVO queryConditionVO) {
        // 1.根据条件查询用户列表
        List<User> userList = userMapper.selectByPageAndParam(queryConditionVO);
        log.info("### 用户Model模糊查询完毕，总条数：{}条###", userList.size());

        // 2. 用户Model转换VO数据完毕
        List<UserVO> userVOList = new ArrayList<>();
        userList.forEach(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        });
        log.info("### 用户Model转换VO数据完毕###");

        return userVOList;
    }

    /**
     * 用户分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
	@Override
    public QueryResult<UserVO> listByPageAndParams(UserQueryConditionVO queryConditionVO, Integer page, Integer size) {
        // 1.分页设置
        PageHelper.startPage(page, size);
        // 2.根据条件查询用户列表
        List<User> userList = userMapper.selectByPageAndParam(queryConditionVO);
        // 3.获取分页后数据
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        log.info("### 用户分页查询完毕,总条数：{} ###", pageInfo.getTotal());

        // 4.Model转换为VO
        List<UserVO> userVOList = new ArrayList<>();
        userList.forEach(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        });
        log.info("### 用户Model转换VO数据完毕###");

        // 5.封装需要返回的实体数据
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(pageInfo.getTotal());
        queryResult.setList(userVOList);

        return queryResult;
    }

    /**
     * 为用户分配角色
     * @param userId
     * @param roleIds
     */
    @Override
    public void assignRoles(String userId, List<String> roleIds) {
        // 1.根据id查询用户
        User userModel = userMapper.selectByPrimaryKey(userId);
        if (userModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "用户");
        }

        // 2.删除历史用户角色关系
        Example userRoleExample = new Example(UserRole.class);
        userRoleExample.createCriteria().andEqualTo("userId", userId);
        userRoleMapper.deleteByExample(userRoleExample);
        log.info("### 用户【userId={}】历史角色关系删除完毕 ###", userId);

        // 3.添加新用户角色关系
        if (CollectionUtils.isEmpty(roleIds)) {
            roleIds.forEach(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoleMapper.insert(userRole);
            });
        }
        log.info("### 用户【userId={}】新角色【roleIds={}】关系分配完毕 ###", userId, roleIds);
    }
}
