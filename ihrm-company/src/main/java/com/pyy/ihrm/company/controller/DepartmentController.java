package com.pyy.ihrm.company.controller;

import java.util.List;

import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.common.response.Result;
import com.pyy.ihrm.company.service.DepartmentService;
import com.pyy.ihrm.company.utils.UserUtil;
import com.pyy.ihrm.domain.company.vo.CompanyDeptListVO;
import com.pyy.ihrm.domain.company.vo.DepartmentQueryConditionVO;
import com.pyy.ihrm.domain.company.vo.DepartmentSaveOrUpdateVO;
import com.pyy.ihrm.domain.company.vo.DepartmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;

/**
 * ---------------------------
 * 部门 (DepartmentController)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:03:44
 * 版本：  v1.0
 * ---------------------------
 */
@Api(tags = "DepartmentController", description = "部门相关接口")
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

   /**
     * 保存部门
     * @param record
     * @return
     */
    @ApiOperation(value = "保存部门", notes = "创建新部门")
    @ApiImplicitParam(name = "record", value = "部门对象", required = true, dataType = "Department", paramType = "body")
    @PostMapping("/department")
    public Result save(@Valid @RequestBody DepartmentSaveOrUpdateVO record) {
        // 后面集成JWT后完善
        String userId = "admin";
        String username = "admin";
        record.setOperaterId(userId);
        record.setOperaterName(username);
        departmentService.save(record);
        return Result.SUCCESS();
    }

   /**
     * 修改部门
     * @param id
     * @param record
     * @return
     */
    @ApiOperation(value = "修改部门", notes = "根据ID修改部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "record", value = "部门对象", required = true, dataType = "Department", paramType = "body")
    })
    @PutMapping("/department/{id}")
    public Result update(@Valid @PathVariable(value = "id") String id, @RequestBody DepartmentSaveOrUpdateVO record) {
        // 后面集成JWT后完善
        String userId = "admin";
        String username = "admin";
        record.setOperaterId(userId);
        record.setOperaterName(username);
        departmentService.update(id, record);
        return Result.SUCCESS();
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
	@ApiOperation(value = "删除部门", notes = "根据ID部门")
    @ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/department/{id}")
    public Result delete(@Valid @PathVariable(value = "id") String id) {
        // 后面集成JWT后完善
        String userId = "admin";
        String username = "admin";
        departmentService.delete(id, userId, username);
        return Result.SUCCESS();
    }

    /**
     * 根据ID查询部门
     * @param id
     * @return
     */
    @ApiOperation(value = "部门查询", notes = "根据ID部门查询")
    @ApiImplicitParam(name = "id", value = "部门ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/department")
    public Result<DepartmentVO> findById(@Valid @PathVariable(value = "id") String id) {
        DepartmentVO queryResult = departmentService.findById(id);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 部门模糊查询
     * @param queryConditionVO
     * @return
     */
    @ApiOperation(value = "部门模糊查询", notes = "查询所有部门列表")
    @GetMapping("/departments")
    public Result<List<DepartmentVO>> findByParams(DepartmentQueryConditionVO queryConditionVO) {
        List<DepartmentVO> queryResult = departmentService.findByParams(queryConditionVO);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 某企业下部门模糊查询
     * @param companyId
     * @param queryConditionVO
     * @return
     */
    @ApiOperation(value = "某企业下部门模糊查询", notes = "根据企业ID查询部门列表")
    @ApiImplicitParam(name = "companyId", value = "企业ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{companyId}/departments")
    public Result<CompanyDeptListVO> findByCompanyIdAndParams(@PathVariable("companyId") String companyId, DepartmentQueryConditionVO queryConditionVO) {
        CompanyDeptListVO companyDeptListVO = departmentService.findByCompanyIdAndParams(companyId, queryConditionVO);
        return Result.SUCCESS(companyDeptListVO);
    }
	
}
