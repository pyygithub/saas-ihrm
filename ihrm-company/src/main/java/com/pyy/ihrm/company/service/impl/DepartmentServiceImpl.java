package com.pyy.ihrm.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.common.response.ResultCode;
import com.pyy.ihrm.common.utils.SnowflakeId;
import com.pyy.ihrm.company.constants.CommonConstants;
import com.pyy.ihrm.company.exception.CustomException;
import com.pyy.ihrm.company.mapper.DepartmentMapper;
import com.pyy.ihrm.company.po.Department;
import com.pyy.ihrm.company.service.DepartmentService;
import com.pyy.ihrm.company.utils.UserUtil;
import com.pyy.ihrm.domain.company.vo.DepartmentQueryConditionVO;
import com.pyy.ihrm.domain.company.vo.DepartmentSaveOrUpdateVO;
import com.pyy.ihrm.domain.company.vo.DepartmentVO;
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
 * 作者：  
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
        departmentModel.setCreateId(departmentSaveOrUpdateVO.getUserId());
        departmentModel.setCreateName(departmentSaveOrUpdateVO.getUsername());
        departmentModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
        departmentModel.setIsDeleted(CommonConstants.UN_DELETED);

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
        Department departmentModel = departmentMapper.selectByPrimaryKey(id);
        if (departmentModel == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "部门");
        }

        // 修改
        BeanUtils.copyProperties(departmentSaveOrUpdateVO, departmentModel);
        departmentModel.setUpdateId(departmentSaveOrUpdateVO.getUserId());
        departmentModel.setUpdateName(departmentSaveOrUpdateVO.getUsername());
        departmentModel.setUpdateTime(new Date());

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
        departmentModel.setIsDeleted(CommonConstants.DELETED);

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
     * 部门模糊查询
     * @param queryConditionVO
     * @return
     */
    @Override
    public List<DepartmentVO> listByParams(DepartmentQueryConditionVO queryConditionVO) {
        List<Department> departmentList = departmentMapper.selectByPageAndParam(queryConditionVO);
        log.info("### 部门Model模糊查询完毕，总条数：{}条###", departmentList.size());

        // 部门Model转换VO数据完毕
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
     * 部门分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
	@Override
    public QueryResult<DepartmentVO> listByPageAndParams(DepartmentQueryConditionVO queryConditionVO, Integer page, Integer size) {
        // 分页查询
        PageHelper.startPage(page, size);
        List<Department> departmentList = departmentMapper.selectByPageAndParam(queryConditionVO);
        // 获取分页后数据
        PageInfo<Department> pageInfo = new PageInfo<>(departmentList);
        log.info("### 部门分页查询完毕,总条数：{} ###", pageInfo.getTotal());

        List<DepartmentVO> departmentVOList = new ArrayList<>();
        // 补全数据
        departmentList.forEach(department -> {
            DepartmentVO departmentVO = new DepartmentVO();
            BeanUtils.copyProperties(department, departmentVO);
            departmentVOList.add(departmentVO);
        });
        log.info("### 部门Model转换VO数据完毕###");

        // 封装需要返回的实体数据
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(pageInfo.getTotal());
        queryResult.setList(departmentVOList);

        return queryResult;
    }
}
