package com.pyy.ihrm.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

/**
 * ========================
 * 分页查询结果
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/6/6
 * Time：10:10
 * Version: v1.0
 * ========================
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "QueryResult",description = "分页结果")
public class QueryResult<T> {

    @ApiModelProperty("数据列表")
    private List<T> list;

    @ApiModelProperty("数据总数")
    private Long total;
}
