package com.pyy.ihrm.system.constants;

/**
 * ========================
 * 公共常量类
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/12 10:36
 * Version: v1.0
 * ========================
 */
public interface SystemConstants {

    /** 删除标记： 0=未删除 1=删除 */
    Integer DELETED = 1;
    Integer UN_DELETED = 0;

    /** 审核状态： 01=未审核  02=已审核 */
    String UN_AUDITED = "01";
    String AUDITED = "02";


    /** 状态： 0=未激活 1=已激活 */
    Integer UN_ACTIVATED = 0;
    Integer ACTIVATED = 1;

    /** 在职状态: 1=在职  2=离职 */
    String IN_SERVICE = "1";
    String LEAVE_OFFICE = "2";

    /** 启用状态： 0=禁用  1=启用 */
    String DISABLED = "0";
    String ENABLE = "1";



    /** 企业可见状态: 0=不可见 1=可见 */
    String NO_VISIBLE = "0";
    String EN_VISIBLE = "1";

    /** 权限类型： 1=菜单 2=按钮 3=API */
    String MENU = "1";
    String BUTTON = "2";
    String API = "3";

    /** 用户级别：1 平台管理员 2 企业管理员 3 企业用户 */
    String SAAS_ADMIN = "1";
    String COMPANY_ADMIN = "2";
    String COMPANY_USER = "3";
}
