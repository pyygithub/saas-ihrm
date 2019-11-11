package com.pyy.ihrm.common.response;

/**
 * ========================
 * 通用响应状态
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/6/6
 * Time：10:10
 * Version: v1.0
 * ========================
 */
public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(200,"SUCCESS"),

    /* 错误状态码 */
    FAIL(500,"ERROR"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数格式错误"),
    ILLEGAL_PARAM(10004, "查询标识参数非法！（00=全部 01=结构化 02=非结构化）"),
    FILE_MAX_SIZE_OVERFLOW(10005, "上传尺寸过大"),
    FILE_ACCEPT_NOT_SUPPORT(10006, "上传文件格式不支持"),
    SET_UP_AT_LEAST_ONE_ADMIN(10007, "至少指定一个管理员"),
    URL_INVALID(10008, "地址不合法"),
    LINK_AND_LOGOUT_NO_MATCH(10009, "主页地址和注销地址IP不一致"),
    IP_AND_PORT_EXISTED(10010, "当前IP和端口已经被占中"),
    PWD_NO_VALID(100011, "密码必须是6-20 位，字母、数字、字符(`~!@#$%^&*)"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),

    /* 业务错误：30001-39999 */
    //BUSINESS_GROUP_NO_ALLOWED_DEL(30001, "应用分组已经被 {0} 个应用【{1}{2}】使用，不能删除"),
    NAME_EXISTED(30001, "{0}名称已存在"),
    CODE_EXISTED(30002, "{0}编码已存在"),
    BUSINESS_OUTER_DATASOURCE_NO_ALLOWED_DEL(30002, "数据源已经被 {0} 个资源【{1}{2}】使用，不能删除"),
    RESOURCE_CATEGORY_EXIST_DEPEND(30003, "当前分类下存在 {0} 个子分类【{1}{2}】，不能删除"),


    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),
    UPLOAD_ERROR(40002, "系统异常，上传文件失败"),

    /* 数据错误：50001-599999 */
    RESULT_DATA_NONE(50001, "【{0}】数据未找到"),
    DATA_YEAR_TO_LARGE(50002, "年份最大支持20年"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),


    /* 权限错误 */
    PERMISSION_UNAUTHENTICATED(70001,"此操作需要登陆系统！"),
    PERMISSION_UNAUTHORISE(70002,"权限不足，无权操作！"),
    PERMISSION_EXPIRE(401,"登录状态过期！"),
    PERMISSION_LIMIT(70004, "访问次数受限制");

    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
