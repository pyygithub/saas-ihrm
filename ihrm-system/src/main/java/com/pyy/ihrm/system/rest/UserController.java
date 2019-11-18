package com.pyy.ihrm.system.rest;

import com.pyy.ihrm.common.controller.BaseController;
import com.pyy.ihrm.common.token.annonation.TokenIgnore;
import com.pyy.ihrm.common.token.annonation.RequiresPermissions;
import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.common.response.Result;
import com.pyy.ihrm.domain.system.vo.*;
import com.pyy.ihrm.system.service.UserService;
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
 * 用户 (UserController)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-13 10:35:07
 * 版本：  v1.0
 * ---------------------------
 */
@Api(tags = "UserController", description = "用户相关接口")
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

   /**
     * 保存用户
     * @param record
     * @return
     */
    @ApiOperation(value = "保存用户", notes = "创建新用户")
    @ApiImplicitParam(name = "record", value = "用户对象", required = true, dataType = "UserSaveOrUpdateVO", paramType = "body")
    @PostMapping("/user")
    @TokenIgnore
    public Result save(@Valid @RequestBody UserSaveOrUpdateVO record) {
        userService.save(record);
        return Result.SUCCESS();
    }

   /**
     * 修改用户
     * @param id
     * @param record
     * @return
     */
    @ApiOperation(value = "修改用户", notes = "根据ID修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "record", value = "用户对象", required = true, dataType = "UserSaveOrUpdateVO", paramType = "body")
    })
    @PutMapping("/user/{id}")
    public Result update(@Valid @PathVariable(value = "id") String id, @RequestBody UserSaveOrUpdateVO record) {
        userService.update(id, record);
        return Result.SUCCESS();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
	@ApiOperation(value = "删除用户", notes = "根据ID用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/user/{id}")
    public Result delete(@Valid @PathVariable(value = "id") String id) {
        userService.delete(id, "admin", "admin");
        return Result.SUCCESS();
    }

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @ApiOperation(value = "用户查询", notes = "根据ID用户查询")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/user")
    public Result<UserRolesVO> findById(@Valid @PathVariable(value = "id") String id) {
        UserRolesVO queryResult = userService.findById(id);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 用户模糊查询
     * @param queryConditionVO
     * @return
     */
    @ApiOperation(value = "用户模糊查询", notes = "用户不带分页模糊查询")
    @GetMapping("/users")
    @RequiresPermissions(value = "API-USER-LIST")
    public Result<List<UserVO>> listByParams(UserQueryConditionVO queryConditionVO) {
        List<UserVO> queryResult = userService.listByParams(queryConditionVO);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 用户分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "用户分页查询", notes = "用户分页模糊查询")
    @ApiImplicitParams({
             @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"),
             @ApiImplicitParam(name = "size", value = "分页尺寸", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/users/page")
    @RequiresPermissions(value = "API-USER-LIST")
    public Result<QueryResult<UserVO>> listByPageAndParams(UserQueryConditionVO queryConditionVO,
                                                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        QueryResult<UserVO> queryResult = userService.listByPageAndParams(queryConditionVO, page, size);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 分配角色
     * @param userId
     * @param roleIds
     * @return
     */
    @ApiOperation(value = "分配角色", notes = "为用户分配角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "roleIds", value = "角色ID列表", required = true, dataType = "List", paramType = "body")
    })
    @PutMapping("/user/{userId}/assignRoles")
    public Result assignRoles(@Valid @PathVariable(value = "userId") String userId, @RequestBody List<String> roleIds) {
        userService.assignRoles(userId, roleIds);
        return Result.SUCCESS();
    }


}
