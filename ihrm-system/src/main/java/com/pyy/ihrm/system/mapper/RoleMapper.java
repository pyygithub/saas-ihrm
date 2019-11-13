package com.pyy.ihrm.system.mapper;

import com.pyy.ihrm.domain.system.vo.RoleQueryConditionVO;
import com.pyy.ihrm.system.model.Role;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * ---------------------------
 * 角色 (RoleMapper)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
public interface RoleMapper extends Mapper<Role> {

    List<Role> selectByPageAndParam(RoleQueryConditionVO queryConditionVO);
}