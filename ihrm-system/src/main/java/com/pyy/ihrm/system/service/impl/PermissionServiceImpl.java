package com.pyy.ihrm.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.pyy.ihrm.common.exception.CustomException;
import com.pyy.ihrm.common.response.ResultCode;
import com.pyy.ihrm.common.utils.SnowflakeId;
import com.pyy.ihrm.domain.system.vo.*;
import com.pyy.ihrm.system.constants.SystemConstants;
import com.pyy.ihrm.system.mapper.PermissionApiMapper;
import com.pyy.ihrm.system.mapper.PermissionButtonMapper;
import com.pyy.ihrm.system.mapper.PermissionMapper;
import com.pyy.ihrm.system.mapper.PermissionMenuMapper;
import com.pyy.ihrm.system.model.Permission;
import com.pyy.ihrm.system.model.PermissionApi;
import com.pyy.ihrm.system.model.PermissionButton;
import com.pyy.ihrm.system.model.PermissionMenu;
import com.pyy.ihrm.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ---------------------------
 * 权限 (PermissionServiceImpl)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
    private PermissionMenuMapper permissionMenuMapper;

	@Autowired
    private PermissionButtonMapper permissionButtonMapper;

	@Autowired
    private PermissionApiMapper permissionApiMapper;

    /**
     * 权限保存
     * @param permissionSaveOrUpdateVO
     */
	@Override
	public void save(PermissionSaveOrUpdateVO permissionSaveOrUpdateVO) {
        // 保存权限
        Permission permissionModel = new Permission();
        BeanUtils.copyProperties(permissionSaveOrUpdateVO, permissionModel);
        permissionModel.setId(SnowflakeId.getId() + "");
        permissionModel.setCreateId(permissionSaveOrUpdateVO.getOperaterId());
        permissionModel.setCreateName(permissionSaveOrUpdateVO.getOperaterName());
        permissionModel.setCreateTime(new Date());
        permissionModel.setIsDeleted(SystemConstants.UN_DELETED);
        permissionMapper.insert(permissionModel);
        log.info("### 权限主表保存成功 ###");

        // 判断权限类型
        String type = permissionSaveOrUpdateVO.getType();
        // 菜单权限
        if (SystemConstants.MENU.equals(type)) {
            PermissionMenu menuModel = new PermissionMenu();
            PermissionMenuSaveOrUpdateVO menuSaveOrUpdateVO = permissionSaveOrUpdateVO.getMenuSaveOrUpdateVO();
            BeanUtils.copyProperties(menuSaveOrUpdateVO, menuModel);
            menuModel.setId(SnowflakeId.getId() + "");
            menuModel.setPermissionId(permissionModel.getId());
            permissionMenuMapper.insert(menuModel);
            log.info("### 菜单权限保存完毕 ###");
        }
        // 按钮权限
        else if (SystemConstants.BUTTON.equals(type)) {
            PermissionButton buttonModel = new PermissionButton();
            PermissionButtonSaveOrUpdateVO buttonSaveOrUpdateVO = permissionSaveOrUpdateVO.getButtonSaveOrUpdateVO();
            BeanUtils.copyProperties(buttonSaveOrUpdateVO, buttonModel);
            buttonModel.setId(SnowflakeId.getId() + "");
            buttonModel.setPermissionId(permissionModel.getId());
            permissionButtonMapper.insert(buttonModel);
            log.info("### 按钮权限保存完毕 ###");
        }
        // API权限
        else if (SystemConstants.API.equals(type)) {
            PermissionApi apiModel = new PermissionApi();
            PermissionApiSaveOrUpdateVO apiSaveOrUpdateVO = permissionSaveOrUpdateVO.getApiSaveOrUpdateVO();
            BeanUtils.copyProperties(apiSaveOrUpdateVO, apiModel);
            apiModel.setId(SnowflakeId.getId() + "");
            apiModel.setPermissionId(permissionModel.getId());
            permissionApiMapper.insert(apiModel);
            log.info("### API权限保存完毕 ###");
        }
	}

	/**
     * 权限修改
     * @param id
     * @param permissionSaveOrUpdateVO
     */
    @Override
    public void update(String id, PermissionSaveOrUpdateVO permissionSaveOrUpdateVO) {
        // 1.根据ID查询权限
        Permission permissionModel = permissionMapper.selectByPrimaryKey(id);
        if (permissionModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "权限");
        }

        // 2.权限属性设置
        BeanUtils.copyProperties(permissionSaveOrUpdateVO, permissionModel);
        permissionModel.setUpdateId(permissionSaveOrUpdateVO.getOperaterId());
        permissionModel.setUpdateName(permissionSaveOrUpdateVO.getOperaterName());
        permissionModel.setUpdateTime(new Date());

        // 3.修改主权限表数据
        permissionMapper.updateByPrimaryKey(permissionModel);
        log.info("### 主权限修改成功 ###");

        // 4.判断权限类型
        String type = permissionSaveOrUpdateVO.getType();
        // 4.1 菜单权限
        if (SystemConstants.MENU.equals(type)) {
            // 根据权限ID查询菜单权限
            Example menuExample = new Example(PermissionMenu.class);
            menuExample.createCriteria().andEqualTo("permissionId", id);
            PermissionMenu menuModel = permissionMenuMapper.selectOneByExample(menuExample);

            // 设置菜单权限属性
            PermissionMenuSaveOrUpdateVO menuSaveOrUpdateVO = permissionSaveOrUpdateVO.getMenuSaveOrUpdateVO();
            BeanUtils.copyProperties(menuSaveOrUpdateVO, menuModel);
            // 执行修改
            permissionMenuMapper.updateByPrimaryKey(menuModel);
            log.info("### 菜单权限修改完毕 ###");
        }
        // 4.2 按钮权限
        else if (SystemConstants.BUTTON.equals(type)) {
            // 根据权限ID查询按钮权限
            Example buttonExample = new Example(PermissionButton.class);
            buttonExample.createCriteria().andEqualTo("permissionId", id);
            PermissionButton buttonModel = permissionButtonMapper.selectOneByExample(buttonExample);

            // 设置菜单权限属性
            PermissionButtonSaveOrUpdateVO buttonSaveOrUpdateVO = permissionSaveOrUpdateVO.getButtonSaveOrUpdateVO();
            BeanUtils.copyProperties(buttonSaveOrUpdateVO, buttonModel);
            // 执行修改
            permissionButtonMapper.updateByPrimaryKey(buttonModel);
            log.info("### 按钮权限修改完毕 ###");
        }
        // 4.3API权限
        else if (SystemConstants.API.equals(type)) {
            // 根据权限ID查询API权限
            Example apiExample = new Example(PermissionApi.class);
            apiExample.createCriteria().andEqualTo("permissionId", id);
            PermissionApi apiModel = permissionApiMapper.selectOneByExample(apiExample);

            // 设置菜单权限属性
            PermissionApiSaveOrUpdateVO apiSaveOrUpdateVO = permissionSaveOrUpdateVO.getApiSaveOrUpdateVO();
            BeanUtils.copyProperties(apiSaveOrUpdateVO, apiModel);
            // 执行修改
            permissionApiMapper.updateByPrimaryKey(apiModel);
            log.info("### API权限修改完毕 ###");
        }
    }

   /**
    * 权限删除
    * @param id
    */
	@Override
	public void delete(String id, String userId, String username) {
	    // 1.根据ID查询权限
	    Permission permissionModel = permissionMapper.selectByPrimaryKey(id);
        if (permissionModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "权限,id=" + id);
        }

		// 2.权限属性设置
        permissionModel.setId(id);
        permissionModel.setUpdateId(userId);
        permissionModel.setUpdateName(username);
        permissionModel.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        permissionModel.setIsDeleted(SystemConstants.UN_DELETED);

        // 3.逻辑删除
        permissionMapper.updateByPrimaryKeySelective(permissionModel);
        log.info("### 权限逻辑删除成功 ###");

        // 4.判断权限类型
        String type = permissionModel.getType();
        // 4.1 菜单权限
        if (SystemConstants.MENU.equals(type)) {
            Example menuExample = new Example(PermissionMenu.class);
            menuExample.createCriteria().andEqualTo("permissionId", id);
            permissionMenuMapper.deleteByExample(menuExample);
            log.info("### 菜单权限删除完毕 ###");
        }
        // 4.2 按钮权限
        else if (SystemConstants.BUTTON.equals(type)) {
            Example buttonExample = new Example(PermissionButton.class);
            buttonExample.createCriteria().andEqualTo("permissionId", id);
            permissionButtonMapper.deleteByExample(buttonExample);
            log.info("### 按钮权限删除完毕 ###");
        }
        // 4.3API权限
        else if (SystemConstants.API.equals(type)) {
            Example apiExample = new Example(PermissionApi.class);
            apiExample.createCriteria().andEqualTo("permissionId", id);
            permissionApiMapper.deleteByExample(apiExample);
            log.info("### API权限删除完毕 ###");
        }
	}

   /**
    * 根据权限ID查询
    * @param id
    */
	@Override
	public PermissionVO findById(String id) {
	    // 1.根据ID查询权限
		Permission permissionModel = permissionMapper.selectByPrimaryKey(id);
		if (permissionModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "权限,id=" + id);
        }
        log.info("### 权限查询成功, permission={}###", JSON.toJSONString(permissionModel));
        // 2.Model转换vo
        PermissionVO permissionVO = new PermissionVO();
        BeanUtils.copyProperties(permissionModel, permissionVO);
        log.info("### 权限Model转换VO成功， permissionVO={}###", permissionVO);

        // 3.根据type补全权限信息
        complePermissionByType(permissionVO, permissionModel.getType(), id);

        return permissionVO;
	}

  /**
     * 权限模糊查询
     * @param queryConditionVO
     * @return
     */
    @Override
    public List<PermissionVO> listByParams(PermissionQueryConditionVO queryConditionVO) {
        List<Permission> permissionList = permissionMapper.selectByPageAndParam(queryConditionVO);
        log.info("### 权限Model模糊查询完毕，总条数：{}条###", permissionList.size());

        List<PermissionVO> permissionVOList = new ArrayList<>();
        permissionList.forEach(permission -> {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission, permissionVO);
            // 根据type补全权限信息
            complePermissionByType(permissionVO, permission.getType(), permission.getId());

            permissionVOList.add(permissionVO);
        });
        log.info("### 权限Model转换VO数据完毕###");
        return permissionVOList;
    }

    // 根据type补全权限信息
    private void complePermissionByType(PermissionVO permissionVO, String type, String permissionId) {
        // 菜单权限
        if (SystemConstants.MENU.equals(type)) {
            Example menuExample = new Example(PermissionMenu.class);
            menuExample.createCriteria().andEqualTo("permissionId", permissionId);
            PermissionMenu menuModel = permissionMenuMapper.selectOneByExample(menuExample);

            PermissionMenuVO menuVO = new PermissionMenuVO();
            BeanUtils.copyProperties(menuModel, menuVO);
            // 补全菜单权限
            permissionVO.setMenu(menuVO);
            log.info("### 菜单权限查询完毕 ###");
        }
        // 按钮权限
        else if (SystemConstants.BUTTON.equals(type)) {
            Example buttonExample = new Example(PermissionButton.class);
            buttonExample.createCriteria().andEqualTo("permissionId", permissionId);
            PermissionButton buttonModel = permissionButtonMapper.selectOneByExample(buttonExample);

            PermissionButtonVO buttonVO = new PermissionButtonVO();
            BeanUtils.copyProperties(buttonModel, buttonVO);
            // 补全按钮权限
            permissionVO.setButton(buttonVO);
            log.info("### 按钮权限查询完毕 ###");
        }
        // API权限
        else if (SystemConstants.API.equals(type)) {
            Example apiExample = new Example(PermissionApi.class);
            apiExample.createCriteria().andEqualTo("permissionId", permissionId);
            PermissionApi apiModel = permissionApiMapper.selectOneByExample(apiExample);

            PermissionApiVO apiVO = new PermissionApiVO();
            BeanUtils.copyProperties(apiModel, apiVO);
            // 补全菜单权限
            permissionVO.setApi(apiVO);
            log.info("### API权限查询完毕 ###");
        }
    }

}
