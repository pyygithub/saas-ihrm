package com.pyy.ihrm.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.pyy.ihrm.common.response.ResultCode;
import com.pyy.ihrm.common.utils.SnowflakeId;
import com.pyy.ihrm.company.constants.CompanyConstants;
import com.pyy.ihrm.common.exception.CustomException;
import com.pyy.ihrm.company.mapper.DepartmentMapper;
import com.pyy.ihrm.company.model.Department;
import com.pyy.ihrm.company.service.CompanyService;
import com.pyy.ihrm.company.service.DepartmentService;
import com.pyy.ihrm.domain.company.vo.*;
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
 * 部门 (DepartmentServiceImpl)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:03:44
 * 版本：  v1.0
 * ---------------------------
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;

	@Autowired
    private CompanyService companyService;

    /**
     * 部门保存
     * @param departmentSaveOrUpdateVO
     */
	@Override
	public void save(DepartmentSaveOrUpdateVO departmentSaveOrUpdateVO) {
        // 保存部门
        Department departmentModel = new Department();
        BeanUtils.copyProperties(departmentSaveOrUpdateVO, departmentModel);
        departmentModel.setId(SnowflakeId.getId() + "");
        departmentModel.setCreateId(departmentSaveOrUpdateVO.getOperaterId());
        departmentModel.setCreateName(departmentSaveOrUpdateVO.getOperaterName());
        departmentModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        departmentModel.setIsDeleted(CompanyConstants.UN_DELETED);

        departmentMapper.insert(departmentModel);
        log.info("### 部门保存成功 ###");
	}

	/**
     * 部门修改
     * @param id
     * @param departmentSaveOrUpdateVO
     */
    @Override
    public void update(String id, DepartmentSaveOrUpdateVO departmentSaveOrUpdateVO) {
        // 1.根据ID查询部门
        Department departmentModel = departmentMapper.selectByPrimaryKey(id);
        if (departmentModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "部门");
        }

        // 2.设置部门属性
        BeanUtils.copyProperties(departmentSaveOrUpdateVO, departmentModel);
        departmentModel.setUpdateId(departmentSaveOrUpdateVO.getOperaterId());
        departmentModel.setUpdateName(departmentSaveOrUpdateVO.getOperaterName());
        departmentModel.setUpdateTime(new Date());

        // 3.更新部门
        departmentMapper.updateByPrimaryKey(departmentModel);
        log.info("### 部门修改成功 ###");
    }

   /**
    * 部门删除
    * @param id
    */
	@Override
	public void delete(String id, String userId, String username) {
	    Department departmentModel = departmentMapper.selectByPrimaryKey(id);
        if (departmentModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "部门,id=" + id);
        }

		// 逻辑删除
        departmentModel.setId(id);
        departmentModel.setUpdateId(userId);
        departmentModel.setUpdateName(username);
        departmentModel.setUpdateTime(new Date());
        departmentModel.setIsDeleted(CompanyConstants.DELETED);

        departmentMapper.updateByPrimaryKeySelective(departmentModel);
        log.info("### 部门逻辑删除成功 ###");
	}

   /**
    * 根据部门ID查询
    * @param id
    */
	@Override
	public DepartmentVO findById(String id) {
		Department departmentModel = departmentMapper.selectByPrimaryKey(id);
		if (departmentModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "部门,id=" + id);
        }
        log.info("### 部门查询成功, department={}###", JSON.toJSONString(departmentModel));
        // model转换vo
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(departmentModel, departmentVO);
        log.info("### 部门Model转换VO成功， departmentVO={}###", departmentVO);
        return departmentVO;
	}
    /**
     * 根据条件模糊查询所有部门信息
     * @param queryConditionVO
     * @return
     */
    @Override
    public List<DepartmentVO> findByParams(DepartmentQueryConditionVO queryConditionVO){
        // 1.根据条件查询部门列表
        List<Department> departmentList = departmentMapper.selectByPageAndParam(queryConditionVO);
        log.info("### 部门Model模糊查询完毕，总条数：{}条###", departmentList.size());

        // 1.1 部门Model转换VO数据
        List<DepartmentVO> departmentVOList = new ArrayList<>();
        departmentList.forEach(department -> {
            DepartmentVO departmentVO = new DepartmentVO();
            BeanUtils.copyProperties(department, departmentVO);
            departmentVOList.add(departmentVO);
        });
        log.info("### 部门Model转换VO数据完毕###");

        return departmentVOList;
    }


    /**
     * 根据条件模糊查询某企业下部门信息
     * @param companyId
     * @param queryConditionVO
     * @return
     */
    @Override
    public CompanyDeptListVO findByCompanyIdAndParams(String companyId, DepartmentQueryConditionVO queryConditionVO){
        // 1.根据条件查询部门列表
        List<Department> departmentList = departmentMapper.selectByPageAndParam(queryConditionVO);
        log.info("### 部门Model模糊查询完毕，总条数：{}条###", departmentList.size());

        // 1.1 部
        // 门Model转换VO数据
        List<DepartmentVO> departmentVOList = new ArrayList<>();
        departmentList.forEach(department -> {
            DepartmentVO departmentVO = new DepartmentVO();
            BeanUtils.copyProperties(department, departmentVO);
            departmentVOList.add(departmentVO);
        });
        log.info("### 部门Model转换VO数据完毕###");

        // 2.根据企业ID查询企业信息
        CompanyVO companyVO = companyService.findById(companyId);

        // 3.构造结果
        CompanyDeptListVO companyDeptListVO = new CompanyDeptListVO(companyVO, departmentVOList);

        return companyDeptListVO;
    }

}
