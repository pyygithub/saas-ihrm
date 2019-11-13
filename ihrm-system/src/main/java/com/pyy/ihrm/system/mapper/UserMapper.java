package com.pyy.ihrm.system.mapper;

import com.pyy.ihrm.domain.system.vo.UserQueryConditionVO;
import com.pyy.ihrm.system.model.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * ---------------------------
 * 用户 (UserMapper)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
public interface UserMapper extends Mapper<User> {

    List<User> selectByPageAndParam(UserQueryConditionVO queryConditionVO);
}