package com.pyy.ihrm.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyy.ihrm.common.exception.CustomException;
import com.pyy.ihrm.common.token.TokenConfig;
import com.pyy.ihrm.common.token.TokenService;
import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.common.response.ResultCode;
import com.pyy.ihrm.common.utils.SnowflakeId;
import com.pyy.ihrm.common.utils.SpringSecurityUtil;
import com.pyy.ihrm.domain.system.vo.*;
import com.pyy.ihrm.system.constants.SystemConstants;
import com.pyy.ihrm.system.mapper.PermissionMapper;
import com.pyy.ihrm.system.mapper.RolePermissionMapper;
import com.pyy.ihrm.system.mapper.UserMapper;
import com.pyy.ihrm.system.mapper.UserRoleMapper;
import com.pyy.ihrm.system.model.Permission;
import com.pyy.ihrm.system.model.RolePermission;
import com.pyy.ihrm.system.model.User;
import com.pyy.ihrm.system.model.UserRole;
import com.pyy.ihrm.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.*;
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

	@Autowired
    private RolePermissionMapper rolePermissionMapper;

	@Autowired
    private PermissionMapper permissionMapper;

	@Autowired
    private TokenConfig tokenConfig;

	@Autowired
    private TokenService tokenService;

	@Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        userModel.setInServiceStatus(SystemConstants.IN_SERVICE);// 在职
        userModel.setEnableState(SystemConstants.ENABLE);// 启用
        userModel.setCreateId(userSaveOrUpdateVO.getOperaterId());
        userModel.setCreateName(userSaveOrUpdateVO.getOperaterName());
        userModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userModel.setIsDeleted(SystemConstants.UN_DELETED);

        // 密码加密
        String encoderPassword = SpringSecurityUtil.encoderPassword(userModel.getPassword());
        userModel.setPassword(encoderPassword);

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
        userModel.setIsDeleted(SystemConstants.DELETED);

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
        List<String> roleIds = getRoleIdsByUserId(id);
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

    /**
     * 用户登录
     * @param loginUser
     */
    @Override
    public String login(LoginUserVO loginUser) {
        // 查询验证码
        String code = stringRedisTemplate.opsForValue().get(loginUser.getUuid());
        // 清除验证码
        stringRedisTemplate.delete(loginUser.getUuid());
        if (StringUtils.isBlank(code)) {
            log.debug("### 验证码已过期 ###");
            throw new CustomException(ResultCode.VALIDATE_CODE_EXPIRED);
        }
        if (StringUtils.isBlank(loginUser.getCode()) || !loginUser.getCode().equals(code)) {
            log.debug("### 验证码错误 ###");
            throw new CustomException(ResultCode.VALIDATE_CODE_INVALID);
        }
        // 根据用户查询用户
        Example userExample = new Example(User.class);
        userExample.createCriteria()
                .andEqualTo("isDeleted", SystemConstants.UN_DELETED)
                .andEqualTo("username", loginUser.getUsername());
        User user = userMapper.selectOneByExample(userExample);
        // 登录失败
        if (user == null || !SpringSecurityUtil.checkpassword(loginUser.getPassword(), user.getPassword())) {
            log.info("### 登录失败：用户不存在或密码错误 ###");
            throw new CustomException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }
        // 用户状态
        if (SystemConstants.DISABLED.equals(user.getEnableState())) {
            log.info("### 账号已停用，请联系管理员 ###");
            throw new CustomException(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }
        // 登录成功：生成令牌
        else {
            // API权限字符串
            List<String> roleIds = this.getRoleIdsByUserId(user.getId());
            // 获取到所有可以访问的API权限
            String apis = this.getApiCodesByRoleIds(roleIds);
            Map<String, Object> extAttribute = new HashMap<>();
            extAttribute.put("apis", apis);
            extAttribute.put("companyId", user.getCompanyId());
            extAttribute.put("companyName", user.getCompanyName());
            String token = tokenService.createToken(user.getId(), user.getUsername(), extAttribute, tokenConfig);
            log.info("### 用户登录成功 ###");
            // 返回token
            return token;
        }
    }

    /**
     * 获取个人信息
     * @param userId
     * @return
     */
    @Override
    public ProfileVO profile(String userId) {
        // 根据用户id查询用户信息
        User user = userMapper.selectByPrimaryKey(userId);
        // 根据不同用户级别获取用户权限
        String level = user.getLevel();

        Map<String, Object> permissions = null;
        // 平台管理员：拥有所有权限
        if (SystemConstants.SAAS_ADMIN.equals(level)) {
            permissions = this.getAllPermissions();
        }
        // 企业管理员：拥有企业下所有权限
        else if (SystemConstants.COMPANY_ADMIN.equals(level)) {
            permissions = this.getAllPermissionsByCompanyId(user.getCompanyId());
        }
        //  企业员工：拥有对应角色权限
        else if (SystemConstants.COMPANY_USER.equals(level)) {
            permissions = this.getRolePermissionsByUserId(userId);
        }

        // 封装个人信息
        ProfileVO profileVO = new ProfileVO();
        profileVO.setUsername(user.getUsername());
        profileVO.setMobile(user.getMobile());
        profileVO.setCompanyName(user.getCompanyName());
        profileVO.setRoles(permissions);
        log.info("### 获取个人信息完毕：profileVO={} ###", profileVO);

        return profileVO;
    }

    // 根据企业ID查询某企业下所有权限编码
    private Map<String, Object> getAllPermissionsByCompanyId(String companyId) {
        Example permissionExample = new Example(Permission.class);
        permissionExample.createCriteria()
                .andEqualTo("isDeleted", SystemConstants.UN_DELETED)
                .andEqualTo("enVisible", SystemConstants.EN_VISIBLE)
                .andEqualTo("companyId", companyId);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);

        // 按权限类型对权限编码分类
        return classifyPermissionByType(permissionList);
    }

    // 查询所有权限编码
    private Map<String, Object> getAllPermissions() {
        Example permissionExample = new Example(Permission.class);
        permissionExample.createCriteria().andEqualTo("isDeleted", SystemConstants.UN_DELETED);
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);

        // 按权限类型对权限编码分类
        return classifyPermissionByType(permissionList);
    }

    // 根据用户id查询关联角色对应权限编码
    private Map<String, Object> getRolePermissionsByUserId(String userId) {
        // 根据用户ID查询关联角色ID
        List<String> roleIds = this.getRoleIdsByUserId(userId);

        if (!CollectionUtils.isEmpty(roleIds)) {
            // 根据角色id查询关联权限ID
            Example rolePermissionExample = new Example(RolePermission.class);
            rolePermissionExample.createCriteria().andIn("roleId", roleIds);
            List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);

            // 根据权限id查询权限详情
            if (!CollectionUtils.isEmpty(rolePermissionList)) {
                List<String> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
                // 去掉重复ID
                Set<String> removedDuplicatePermissionIds = new HashSet(permissionIds);
                // 查询权限
                Example permissionExample = new Example(Permission.class);
                permissionExample.createCriteria()
                        .andEqualTo("isDeleted", SystemConstants.UN_DELETED)
                        .andIn("id", removedDuplicatePermissionIds);
                List<Permission> apiPermissionList = permissionMapper.selectByExample(permissionExample);

                // 按权限类型对权限编码分类
                return classifyPermissionByType(apiPermissionList);
            }
        }
        return null;
    }

    // 按权限类型对权限编码分类
    private Map<String, Object> classifyPermissionByType(List<Permission> permissionList) {
        Map<String, Object> permissions = new HashMap<>();
        // 按类型分类
        List<String> menus = new ArrayList<>(20);
        List<String> buttons = new ArrayList<>(30);
        List<String> apis = new ArrayList<>(20);
        for (Permission permission : permissionList) {
            String code = permission.getCode();
            String type = permission.getType();
            if (SystemConstants.MENU.equalsIgnoreCase(type)) {
                menus.add(code);
            } else if (SystemConstants.BUTTON.equalsIgnoreCase(type)) {
                buttons.add(code);
            } else if (SystemConstants.API.equalsIgnoreCase(type)) {
                apis.add(code);
            }
        }
        permissions.put("menus", menus);
        permissions.put("buttons", buttons);
        permissions.put("apis", apis);
        log.info("### 权限编码按类型分类完毕 permissions={} ###", permissions);
        return permissions;
    }

    // 根据用户id查询关联角色ID集合
    private List<String> getRoleIdsByUserId(String userId) {
        Example userRoleExample = new Example(UserRole.class);
        userRoleExample.createCriteria().andEqualTo("userId", userId);
        List<UserRole> userRoleList = userRoleMapper.selectByExample(userRoleExample);
        return userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

    // 根据角色ID集合查询关联API权限字符串
    private String getApiCodesByRoleIds(List<String> roleIds) {
        if (!CollectionUtils.isEmpty(roleIds)) {
            // 根据角色id查询关联权限ID
            Example rolePermissionExample = new Example(RolePermission.class);
            rolePermissionExample.createCriteria().andIn("roleId", roleIds);
            List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);
            if (!CollectionUtils.isEmpty(rolePermissionList)) {
                List<String> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
                // 去掉重复ID
                Set<String> removedDuplicatePermissionIds = new HashSet(permissionIds);
                for (String permissionId : removedDuplicatePermissionIds) {
                    // 查询所有API权限
                    Example permissionExample = new Example(Permission.class);
                    permissionExample.createCriteria()
                            .andEqualTo("isDeleted", SystemConstants.UN_DELETED)
                            .andEqualTo("id", permissionId)
                            .andEqualTo("type", SystemConstants.API);
                    List<Permission> apiPermissionList = permissionMapper.selectByExample(permissionExample);
                    List<String> apiCodeList = apiPermissionList.stream().map(Permission::getCode).collect(Collectors.toList());

                    String apis = String.join(",", apiCodeList);
                    log.info("### 当前用户拥有的API权限标识：apis={} ###", apis);
                    return apis;
                }
            }
        }
        return null;
    }
}
