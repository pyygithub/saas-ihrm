package com.pyy.ihrm.system.mapper;

import com.pyy.ihrm.domain.system.vo.PermissionQueryConditionVO;
import com.pyy.ihrm.system.model.Permission;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * ---------------------------
 * 权限 (PermissionMapper)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
public interface PermissionMapper extends Mapper<Permission> {

    List<Permission> selectByPageAndParam(PermissionQueryConditionVO queryConditionVO);
}