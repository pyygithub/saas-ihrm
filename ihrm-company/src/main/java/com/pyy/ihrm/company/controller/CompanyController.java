package com.pyy.ihrm.company.controller;

import com.pyy.ihrm.common.response.QueryResult;
import com.pyy.ihrm.common.response.Result;
import com.pyy.ihrm.company.service.CompanyService;
import com.pyy.ihrm.domain.company.vo.CompanyQueryConditionVO;
import com.pyy.ihrm.domain.company.vo.CompanySaveOrUpdateVO;
import com.pyy.ihrm.domain.company.vo.CompanyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * ---------------------------
 * 企业信息 (CompanyController)         
 * ---------------------------
 * 作者：  pyy
 * 时间：  2019-11-12 10:10:10
 * 版本：  v1.0
 * ---------------------------
 */
@Api(tags = "CompanyController", description = "企业信息相关接口")
@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
public class CompanyController {

	@Autowired
	private CompanyService companyService;

   /**
     * 保存企业信息
     * @param record
     * @return
     */
    @ApiOperation(value = "保存企业信息", notes = "创建新企业信息")
    @ApiImplicitParam(name = "record", value = "企业信息对象", required = true, dataType = "CompanySaveOrUpdateVO", paramType = "body")
    @PostMapping("/company")
    public Result save(@Valid @RequestBody CompanySaveOrUpdateVO record) {
        // 后面集成JWT后完善
        String userId = "admin";
        String username = "admin";

        record.setOperaterId(userId);
        record.setOperaterName(username);
        companyService.save(record);
        return Result.SUCCESS();
    }

   /**
     * 修改企业信息
     * @param id
     * @param record
     * @return
     */
    @ApiOperation(value = "修改企业信息", notes = "根据ID修改企业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "企业信息ID", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "record", value = "企业信息对象", required = true, dataType = "CompanySaveOrUpdateVO", paramType = "body")
    })
    @PutMapping("/company/{id}")
    public Result update(@Valid @PathVariable(value = "id") String id, @RequestBody CompanySaveOrUpdateVO record) {
        // 后面集成JWT后完善
        String userId = "admin";
        String username = "admin";

        record.setOperaterId(userId);
        record.setOperaterName(username);
        companyService.update(id, record);
        return Result.SUCCESS();
    }

    /**
     * 删除企业信息
     * @param id
     * @return
     */
	@ApiOperation(value = "删除企业信息", notes = "根据ID企业信息")
    @ApiImplicitParam(name = "id", value = "企业信息ID", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/company/{id}")
    public Result delete(@Valid @PathVariable(value = "id") String id) {
	    // 后面集成JWT后完善
	    String userId = "admin";
	    String username = "admin";
        companyService.delete(id, userId, username);
        return Result.SUCCESS();
    }

    /**
     * 根据ID查询企业信息
     * @param id
     * @return
     */
    @ApiOperation(value = "企业信息查询", notes = "根据ID企业信息查询")
    @ApiImplicitParam(name = "id", value = "企业信息ID", required = true, dataType = "String", paramType = "path")
    @GetMapping("/company/{id}")
    public Result<CompanyVO> findById(@Valid @PathVariable(value = "id") String id) {
        CompanyVO queryResult = companyService.findById(id);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 企业信息模糊查询
     * @param queryConditionVO
     * @return
     */
    @ApiOperation(value = "企业信息模糊查询", notes = "企业信息不带分页模糊查询")
    @GetMapping("/companys")
    public Result<List<CompanyVO>> listByParams(CompanyQueryConditionVO queryConditionVO) {
        List<CompanyVO> queryResult = companyService.listByParams(queryConditionVO);
        return Result.SUCCESS(queryResult);
    }

    /**
     * 企业信息分页模糊查询
     * @param queryConditionVO
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "企业信息分页查询", notes = "企业信息分页模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "分页尺寸", required = true, dataType = "Integer", paramType = "query")
    })
    @GetMapping("/companys/page")
    public Result<QueryResult<CompanyVO>> listByPageAndParams(CompanyQueryConditionVO queryConditionVO,
                                                              @RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size) {
        QueryResult<CompanyVO> queryResult = companyService.listByPageAndParams(queryConditionVO, page, size);
        return Result.SUCCESS(queryResult);
    }
	
}
