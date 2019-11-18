package com.pyy.ihrm.system.rest;

import com.pyy.ihrm.common.response.Result;
import com.pyy.ihrm.domain.system.vo.PermissionQueryConditionVO;
import com.pyy.ihrm.domain.system.vo.PermissionSaveOrUpdateVO;
import com.pyy.ihrm.domain.system.vo.PermissionVO;
import com.pyy.ihrm.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import java.util.List;

/**
 * ---------------------------
 * 权限 (PermissionController)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Api(tags = "PermissionController", description = "权限相关接口")
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

   /**
     * 保存权限
     * @param record
     * @return
     */
    @ApiOperation(value = "保存权限", notes = "创建新权限")
    @ApiImplicitParam(name = "record", value = "权限对象", required = true, dataType = "PermissionSaveOrUpdateVO", paramType = "body")
    @PostMapping("/permission")
    public Result save(@Valid @RequestBody PermissionSaveOrUpdateVO record) {
        permissionService.save(record);
        return Result.SUCCESS();
    }

   /**
     * 修改权限
     * @param id
     * @param record
     * @return
     */
    @ApiOperation(value = "修改权限", notes = "根据ID修改权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "record", value = "权限对象", required = true, dataType = "PermissionSaveOrUpdateVO", paramType = "body")
    })
    @PutMapping("/permission/{id}")
    public Result update(@Valid @PathVariable(value = "id") String id, @RequestBody PermissionSaveOrUpdateVO record) {
        permissionService.update(id, record);
        return Result.SUCCESS();
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
	@ApiOperation(value = "删除权限", notes = "根据ID权限")
    @ApiImplicitParam(name = "id", value = "权限ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/permission/{id}")
    public Result delete(@Valid @PathVariable(value = "id") String id) {
        permissionService.delete(id, "admin", "admin");
        return Result.SUCCESS();
    }

    /**
     * 根据ID查询权限
     * @param id
     * @return
     */
    @ApiOperation(value = "权限查询", notes = "根据ID权限查询")
    @ApiImplicitParam(name = "id", value = "权限ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/permission")
    public Result<PermissionVO> findById(@Valid @PathVariable(value = "id") String id) {
        PermissionVO queryResult = permissionService.findById(id);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 权限模糊查询
     * @param queryConditionVO
     * @return
     */
    @ApiOperation(value = "权限模糊查询", notes = "权限不带分页模糊查询")
    @GetMapping("/permissions")
    public Result<List<PermissionVO>> listByParams(PermissionQueryConditionVO queryConditionVO) {
        List<PermissionVO> queryResult = permissionService.listByParams(queryConditionVO);
        return Result.SUCCESS(queryResult);
    }
}
