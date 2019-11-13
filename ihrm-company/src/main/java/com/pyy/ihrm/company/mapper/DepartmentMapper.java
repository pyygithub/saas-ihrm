package com.pyy.ihrm.company.mapper;

import com.pyy.ihrm.company.po.Department;
import com.pyy.ihrm.domain.company.vo.DepartmentQueryConditionVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;



/**
 * ---------------------------
 * 部门 (DepartmentMapper)         
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-13 09:56:28
 * 版本：  v1.0
 * ---------------------------
 */
public interface DepartmentMapper extends Mapper<Department> {

    List<Department> selectByPageAndParam(DepartmentQueryConditionVO queryConditionVO);
}