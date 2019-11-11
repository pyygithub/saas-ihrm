package com.pyy.ihrm.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * ========================
 * 统一响应结果集
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/6/6
 * Time：10:10
 * Version: v1.0
 * ========================
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // 非空数据不显示
@Setter
@Getter
@ApiModel(value = "Result",description = "API接口返回值")
public class Result<T> {

    @ApiModelProperty("操作编码")
    int code;

    @ApiModelProperty("响应信息")
    String message;

    @ApiModelProperty("结果数据")
    T data;

    @ApiModelProperty("请求响应时间戳")
    Long timestamp;

    public Result(ResultCode resultCode){
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.timestamp = System.currentTimeMillis();
    }

    public Result(int code, String msg){
        this.code = code;
        this.message = msg;
        this.timestamp = System.currentTimeMillis();
    }

    public Result(ResultCode resultCode, T data){
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static Result SUCCESS(){
        return new Result(ResultCode.SUCCESS);
    }

    public static <T> Result SUCCESS(T data){
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result FAIL(){
        return new Result(ResultCode.FAIL);
    }

}
