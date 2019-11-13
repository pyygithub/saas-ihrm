package com.pyy.ihrm.company.controller;

import java.util.List;

import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.common.response.Result;
import com.pyy.ihrm.company.service.DepartmentService;
import com.pyy.ihrm.company.utils.UserUtil;
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
 * 作者：  
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
        record.setUserId(userId);
        record.setUsername(username);
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
        record.setUserId(userId);
        record.setUsername(username);
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
    @ApiOperation(value = "部门模糊查询", notes = "部门不带分页模糊查询")
    @GetMapping("/departments")
    public Result<List<DepartmentVO>> listByParams(DepartmentQueryConditionVO queryConditionVO) {
        List<DepartmentVO> queryResult = departmentService.listByParams(queryConditionVO);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 部门分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "部门分页查询", notes = "部门分页模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "分页尺寸", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping("/departments/page")
    public Result<QueryResult<DepartmentVO>> listByPageAndParams(DepartmentQueryConditionVO queryConditionVO,
                                                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {
        QueryResult<DepartmentVO> queryResult = departmentService.listByPageAndParams(queryConditionVO, page, size);
        return Result.SUCCESS(queryResult);
    }
	
}
