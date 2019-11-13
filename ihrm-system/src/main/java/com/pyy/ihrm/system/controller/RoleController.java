package com.pyy.ihrm.system.controller;

import java.util.List;

import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.common.response.Result;
import com.pyy.ihrm.domain.system.vo.RoleQueryConditionVO;
import com.pyy.ihrm.domain.system.vo.RoleSaveOrUpdateVO;
import com.pyy.ihrm.domain.system.vo.RoleVO;
import com.pyy.ihrm.system.service.RoleService;
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
 * 角色 (RoleController)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Api(tags = "RoleController", description = "角色相关接口")
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
public class RoleController {

	@Autowired
	private RoleService roleService;

   /**
     * 保存角色
     * @param record
     * @return
     */
    @ApiOperation(value = "保存角色", notes = "创建新角色")
    @ApiImplicitParam(name = "record", value = "角色对象", required = true, dataType = "Role", paramType = "body")
    @PostMapping("/role")
    public Result save(@Valid @RequestBody RoleSaveOrUpdateVO record) {
        roleService.save(record);
        return Result.SUCCESS();
    }

   /**
     * 修改角色
     * @param id
     * @param record
     * @return
     */
    @ApiOperation(value = "修改角色", notes = "根据ID修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "record", value = "角色对象", required = true, dataType = "Role", paramType = "body")
    })
    @PutMapping("/role/{id}")
    public Result update(@Valid @PathVariable(value = "id") String id, @RequestBody RoleSaveOrUpdateVO record) {
        roleService.update(id, record);
        return Result.SUCCESS();
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
	@ApiOperation(value = "删除角色", notes = "根据ID角色")
    @ApiImplicitParam(name = "id", value = "角色ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/role/{id}")
    public Result delete(@Valid @PathVariable(value = "id") String id) {
        roleService.delete(id,"admin", "admin");
        return Result.SUCCESS();
    }

    /**
     * 根据ID查询角色
     * @param id
     * @return
     */
    @ApiOperation(value = "角色查询", notes = "根据ID角色查询")
    @ApiImplicitParam(name = "id", value = "角色ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/role")
    public Result<RoleVO> findById(@Valid @PathVariable(value = "id") String id) {
        RoleVO queryResult = roleService.findById(id);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 角色模糊查询
     * @param queryConditionVO
     * @return
     */
    @ApiOperation(value = "角色模糊查询", notes = "角色不带分页模糊查询")
    @GetMapping("/roles")
    public Result<List<RoleVO>> listByParams(RoleQueryConditionVO queryConditionVO) {
        List<RoleVO> queryResult = roleService.listByParams(queryConditionVO);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 角色分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "角色分页查询", notes = "角色分页模糊查询")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "Integer", paramType = "query"),
             @ApiImplicitParam(name = "size", value = "分页尺寸", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping("/roles/page")
    public Result<QueryResult<RoleVO>> listByPageAndParams(RoleQueryConditionVO queryConditionVO,
                                                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        QueryResult<RoleVO> queryResult = roleService.listByPageAndParams(queryConditionVO, page, size);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 分配权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @ApiOperation(value = "分配权限", notes = "为角色分配权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "permissionIds", value = "权限ID列表", required = true, dataType = "List", paramType = "body")
    })
    @PutMapping("/role/{roleId}/assignPermissions")
    public Result assignPermissions(@Valid @PathVariable(value = "roleId") String roleId, @RequestBody List<String> permissionIds) {
        roleService.assignPermissions(roleId, permissionIds);
        return Result.SUCCESS();
    }

}
