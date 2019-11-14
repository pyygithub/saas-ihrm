package com.pyy.ihrm.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.common.response.ResultCode;
import com.pyy.ihrm.common.utils.SnowflakeId;
import com.pyy.ihrm.domain.system.vo.RolePermissionsVO;
import com.pyy.ihrm.domain.system.vo.RoleQueryConditionVO;
import com.pyy.ihrm.domain.system.vo.RoleSaveOrUpdateVO;
import com.pyy.ihrm.domain.system.vo.RoleVO;
import com.pyy.ihrm.system.constants.CommonConstants;
import com.pyy.ihrm.system.exception.CustomException;
import com.pyy.ihrm.system.mapper.PermissionApiMapper;
import com.pyy.ihrm.system.mapper.PermissionMapper;
import com.pyy.ihrm.system.mapper.RoleMapper;
import com.pyy.ihrm.system.mapper.RolePermissionMapper;
import com.pyy.ihrm.system.model.*;
import com.pyy.ihrm.system.service.RoleService;
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
 * 角色 (RoleServiceImpl)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
    private RolePermissionMapper rolePermissionMapper;

	@Autowired
    private PermissionMapper permissionMapper;

	@Autowired
    private PermissionApiMapper permissionApiMapper;

    /**
     * 角色保存
     * @param roleSaveOrUpdateVO
     */
	@Override
	public void save(RoleSaveOrUpdateVO roleSaveOrUpdateVO) {
        // 保存角色
        Role roleModel = new Role();
        BeanUtils.copyProperties(roleSaveOrUpdateVO, roleModel);
        roleModel.setId(SnowflakeId.getId() + "");
        roleModel.setCreateId(roleSaveOrUpdateVO.getOperaterId());
        roleModel.setCreateName(roleSaveOrUpdateVO.getOperaterName());
        roleModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        roleModel.setIsDeleted(CommonConstants.UN_DELETED);

        roleMapper.insert(roleModel);
        log.info("### 角色保存成功 ###");
	}

	/**
     * 角色修改
     * @param id
     * @param roleSaveOrUpdateVO
     */
    @Override
    public void update(String id, RoleSaveOrUpdateVO roleSaveOrUpdateVO) {
        Role roleModel = roleMapper.selectByPrimaryKey(id);
        if (roleModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "角色");
        }

        // 修改
        BeanUtils.copyProperties(roleSaveOrUpdateVO, roleModel);
        roleModel.setUpdateId(roleSaveOrUpdateVO.getOperaterId());
        roleModel.setUpdateName(roleSaveOrUpdateVO.getOperaterName());
        roleModel.setUpdateTime(new Date());

        roleMapper.updateByPrimaryKey(roleModel);
        log.info("### 角色修改成功 ###");
    }

    /**
    * 角色删除
    * @param id
    */
	@Override
	public void delete(String id, String userId, String username) {
	    Role roleModel = roleMapper.selectByPrimaryKey(id);
        if (roleModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "角色,id=" + id);
        }

		// 逻辑删除
        roleModel.setId(id);
        roleModel.setUpdateId(userId);
        roleModel.setUpdateName(username);
        roleModel.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        roleModel.setIsDeleted(CommonConstants.UN_DELETED);

        roleMapper.updateByPrimaryKeySelective(roleModel);
        log.info("### 角色逻辑删除成功 ###");
	}

    /**
    * 根据角色ID查询
    * @param id
    */
	@Override
	public RolePermissionsVO findById(String id) {
	    // 1.根据id查询角色信息
		Role roleModel = roleMapper.selectByPrimaryKey(id);
		if (roleModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "角色,id=" + id);
        }
        log.info("### 角色查询成功, role={}###", JSON.toJSONString(roleModel));

        // 2.Model转换vo
        RolePermissionsVO rolePermissionVO = new RolePermissionsVO();
        BeanUtils.copyProperties(roleModel, rolePermissionVO);
        log.info("### 角色Model转换VO成功， rolePermissionVO={}###", rolePermissionVO);

        // 3.根据角色ID查询角色关联权限ID集合
        Example rolePermissionExample = new Example(RolePermission.class);
        rolePermissionExample.createCriteria().andEqualTo("roleId", id);
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectByExample(rolePermissionExample);
        List<String> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        log.info("### 角色【roleId={}】关联权限集合【permissionIds={}】###", id, permissionIds);

        // 4.补全权限属性集合
        rolePermissionVO.setPermissionIds(permissionIds);

        return rolePermissionVO;
	}

    /**
     * 角色模糊查询
     * @param queryConditionVO
     * @return
     */
    @Override
    public List<RoleVO> listByParams(RoleQueryConditionVO queryConditionVO) {
        List<Role> roleList = roleMapper.selectByPageAndParam(queryConditionVO);
        log.info("### 角色Model模糊查询完毕，总条数：{}条###", roleList.size());

        // 角色Model转换VO数据完毕
        List<RoleVO> roleVOList = new ArrayList<>();
        roleList.forEach(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            roleVOList.add(roleVO);
        });
        log.info("### 角色Model转换VO数据完毕###");

        return roleVOList;
    }

    /**
     * 角色分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
	@Override
    public QueryResult<RoleVO> listByPageAndParams(RoleQueryConditionVO queryConditionVO, Integer page, Integer size) {
        // 分页查询
        PageHelper.startPage(page, size);
        List<Role> roleList = roleMapper.selectByPageAndParam(queryConditionVO);
        // 获取分页后数据
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        log.info("### 角色分页查询完毕,总条数：{} ###", pageInfo.getTotal());

        List<RoleVO> roleVOList = new ArrayList<>();
        // 补全数据
        roleList.forEach(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            roleVOList.add(roleVO);
        });
        log.info("### 角色Model转换VO数据完毕###");

        // 封装需要返回的实体数据
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(pageInfo.getTotal());
        queryResult.setList(roleVOList);

        return queryResult;
    }

    /**
     * 为角色分配权限
     * @param roleId
     * @param permissionIds
     */
    @Override
    public void assignPermissions(String roleId, List<String> permissionIds) {
        // 1.根据id查询角色
        Role roleModel = roleMapper.selectByPrimaryKey(roleId);
        if (roleModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "角色");
        }

        // 2.删除历史角色权限关系
        Example rolePermissionExample = new Example(RolePermission.class);
        rolePermissionExample.createCriteria().andEqualTo("roleId", roleId);
        rolePermissionMapper.deleteByExample(rolePermissionExample);
        log.info("### 角色【roleId={}】历史权限关系删除完毕 ###", roleId);


        if (CollectionUtils.isEmpty(permissionIds)) {

            // 3.查询当前权限对应的API权限（系统自动分配）
            permissionIds.forEach(permissionId -> {
                // 3.1根据perentId和type查询当前菜单或按钮对应的API权限
                Example permissionExample = new Example(Permission.class);
                permissionExample.createCriteria()
                        .andEqualTo("isDeleted", CommonConstants.UN_DELETED)
                        .andEqualTo("parentId", permissionId)
                        .andEqualTo("type", CommonConstants.API);
                List<Permission> apiPermissionList = permissionMapper.selectByExample(permissionExample);
                List<String> apiPermissionIds = apiPermissionList.stream().map(Permission::getId).collect(Collectors.toList());
                permissionIds.addAll(apiPermissionIds);// 自动赋予API权限

                log.info("### 为权限【permissionId={}】自动赋予API权限【apiPermissionIds={}】###",permissionId, apiPermissionIds);
            });

            // 4.添加新角色权限关系
            permissionIds.forEach(permissionId -> {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(roleId);
                rolePermissionMapper.insert(rolePermission);
            });
        }
        log.info("### 角色【roleId={}】新权限【permissionIds={}】关系分配完毕 ###", roleId, permissionIds);
    }
}
