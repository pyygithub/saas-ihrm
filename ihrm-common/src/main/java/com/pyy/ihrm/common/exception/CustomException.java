package com.pyy.ihrm.common.exception;

import com.pyy.ihrm.common.response.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

/**
 * ========================
 * 自定义异常类
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/11 17:16
 * Version: v1.0
 * ========================
 */
@Setter
@Getter
public class CustomException extends RuntimeException {

    //错误代码
    private ResultCode resultCode;

    private int code;

    private String message;

    public CustomException(ResultCode resultCode){
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public CustomException(ResultCode resultCode, Object... args){
        code = resultCode.code();
        message = MessageFormat.format(resultCode.message(), args);
    }

}
