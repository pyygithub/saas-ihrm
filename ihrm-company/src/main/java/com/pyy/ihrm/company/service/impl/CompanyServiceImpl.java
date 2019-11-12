package com.pyy.ihrm.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.common.response.ResultCode;
import com.pyy.ihrm.common.utils.SnowflakeId;
import com.pyy.ihrm.company.constants.CommonConstants;
import com.pyy.ihrm.company.exception.CustomException;
import com.pyy.ihrm.company.mapper.CompanyMapper;
import com.pyy.ihrm.company.po.Company;
import com.pyy.ihrm.company.service.CompanyService;
import com.pyy.ihrm.company.utils.UserUtil;
import com.pyy.ihrm.domain.company.vo.CompanyQueryConditionVO;
import com.pyy.ihrm.domain.company.vo.CompanySaveOrUpdateVO;
import com.pyy.ihrm.domain.company.vo.CompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * ---------------------------
 * 企业信息 (CompanyServiceImpl)         
 * ---------------------------
 * 作者：  
 * 时间：  2019-11-12 10:10:10
 * 版本：  v1.0
 * ---------------------------
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyMapper companyMapper;

    /**
     * 企业信息保存
     * @param companySaveOrUpdateVO
     */
	@Override
	public void save(CompanySaveOrUpdateVO companySaveOrUpdateVO) {
        // 保存企业信息
        Company companyModel = new Company();
        BeanUtils.copyProperties(companySaveOrUpdateVO, companyModel);
        companyModel.setId(SnowflakeId.getId() + "");
        companyModel.setAuditState(CommonConstants.UN_AUDITED);// 未审核
        companyModel.setState(CommonConstants.ACTIVATED); // 已激活
        companyModel.setCreateId(companySaveOrUpdateVO.getUserId());
        companyModel.setCreateName(companySaveOrUpdateVO.getUsername());
        companyModel.setCreateTime(new Date());
        companyModel.setIsDeleted(CommonConstants.UN_DELETED);

        companyMapper.insert(companyModel);
        log.info("### 企业信息保存成功 ###");
	}

	/**
     * 企业信息修改
     * @param id
     * @param companySaveOrUpdateVO
     */
    @Override
    public void update(String id, CompanySaveOrUpdateVO companySaveOrUpdateVO) {
        Company companyModel = companyMapper.selectByPrimaryKey(id);
        if (companyModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "企业信息");
        }

        // 修改
        BeanUtils.copyProperties(companySaveOrUpdateVO, companyModel);
        companyModel.setUpdateTime(new Date());
        companyModel.setUpdateId(companySaveOrUpdateVO.getUserId());
        companyModel.setUpdateName(companySaveOrUpdateVO.getUsername());

        companyMapper.updateByPrimaryKey(companyModel);
        log.info("### 企业信息修改成功 ###");
    }

    /**
     * 企业信息删除
     * @param id
     * @param userId
     * @param username
     */
	@Override
	public void delete(String id, String userId, String username) {
	    Company companyModel = companyMapper.selectByPrimaryKey(id);
        if (companyModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "企业信息,id=" + id);
        }

		// 逻辑删除
        companyModel.setId(id);
        companyModel.setUpdateId(userId);
        companyModel.setUpdateName(username);
        companyModel.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        companyModel.setIsDeleted(CommonConstants.DELETED);

        companyMapper.updateByPrimaryKeySelective(companyModel);
        log.info("### 企业信息逻辑删除成功 ###");
	}

   /**
    * 根据企业信息ID查询
    * @param id
    */
	@Override
	public CompanyVO findById(String id) {
		Company companyModel = companyMapper.selectByPrimaryKey(id);
		if (companyModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "企业信息,id=" + id);
        }
        log.info("### 企业信息查询成功, company={}###", JSON.toJSONString(companyModel));

        // model转换vo
        CompanyVO companyVO = new CompanyVO();
        BeanUtils.copyProperties(companyModel, companyVO);
        log.info("### 企业信息Model转换VO成功， companyVO={}###", companyVO);
        return companyVO;
	}

  /**
     * 企业信息模糊查询
     * @param queryConditionVO
     * @return
     */
    @Override
    public List<CompanyVO> listByParams(CompanyQueryConditionVO queryConditionVO) {
        List<Company> companyList = companyMapper.selectByPageAndParam(queryConditionVO);
        log.info("### 企业信息Model模糊查询完毕，总条数：{}条###", companyList.size());

        // 企业信息Model转换VO数据完毕
        List<CompanyVO> companyVOList = new ArrayList<>();
        companyList.forEach(company -> {
            CompanyVO companyVO = new CompanyVO();
            BeanUtils.copyProperties(company, companyVO);
            companyVOList.add(companyVO);
        });
        log.info("### 企业信息Model转换VO数据完毕###");

        return companyVOList;
    }

    /**
     * 企业信息分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
	@Override
    public QueryResult<CompanyVO> listByPageAndParams(CompanyQueryConditionVO queryConditionVO, Integer page, Integer size) {
        // 分页查询
        PageHelper.startPage(page, size);

        List<Company> companyList = companyMapper.selectByPageAndParam(queryConditionVO);
        // 获取分页后数据
        PageInfo<Company> pageInfo = new PageInfo<>(companyList);
        log.info("### 企业信息分页查询完毕,总条数：{} ###", pageInfo.getTotal());

        List<CompanyVO> companyVOList = new ArrayList<>();
        // 补全数据
        companyList.forEach(company -> {
            CompanyVO companyVO = new CompanyVO();
            BeanUtils.copyProperties(company, companyVO);
            companyVOList.add(companyVO);
        });
        log.info("### 企业信息Model转换VO数据完毕###");

        // 封装需要返回的实体数据
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(pageInfo.getTotal());
        queryResult.setList(companyVOList);

        return queryResult;
    }
}
