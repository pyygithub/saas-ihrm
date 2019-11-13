package com.pyy.ihrm.company.service;


import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.domain.company.vo.CompanyQueryConditionVO;
import com.pyy.ihrm.domain.company.vo.CompanySaveOrUpdateVO;
import com.pyy.ihrm.domain.company.vo.CompanyVO;

import java.util.List;

/**
 * ---------------------------
 * 企业信息 (CompanyService)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-12 10:10:10
 * 版本：  v1.0
 * ---------------------------
 */
public interface CompanyService {

    /**
     * 企业信息保存
     * @param companySaveOrUpdateVO
     */
	void save(CompanySaveOrUpdateVO companySaveOrUpdateVO);

	/**
     * 企业信息修改
     * @param id
     * @param companySaveOrUpdateVO
     */
    void update(String id, CompanySaveOrUpdateVO companySaveOrUpdateVO);

    /**
     * 企业信息删除
     * @param id
     */
	void delete(String id, String userId, String username);

    /**
     * 根据企业信息ID查询
     * @param id
     */
	CompanyVO findById(String id);

    /**
     * 企业信息模糊查询
     * @param queryConditionVO
     * @return
     */
    List<CompanyVO> listByParams(CompanyQueryConditionVO queryConditionVO);


    /**
     * 企业信息分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
    QueryResult<CompanyVO> listByPageAndParams(CompanyQueryConditionVO queryConditionVO, Integer page, Integer size);
}
