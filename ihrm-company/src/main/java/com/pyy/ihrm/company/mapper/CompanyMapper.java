package com.pyy.ihrm.company.mapper;

import java.util.List;

import com.pyy.ihrm.company.model.Company;
import com.pyy.ihrm.domain.company.vo.CompanyQueryConditionVO;
import tk.mybatis.mapper.common.Mapper;

/**
 * ---------------------------
 * 企业信息 (CompanyMapper)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-12 10:10:10
 * 版本：  v1.0
 * ---------------------------
 */
public interface CompanyMapper extends Mapper<Company>{

    List<Company> selectByPageAndParam(CompanyQueryConditionVO queryConditionVO);
}