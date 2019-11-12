package com.pyy.ihrm.company.constants;

/**
 * ========================
 * 公共常量类
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/11/12 10:36
 * Version: v1.0
 * ========================
 */
public interface CommonConstants {

    // 删除标记： 0 未删除 1 删除
    Integer DELETED = 1;
    Integer UN_DELETED = 0;

    // 审核状态： 01 未审核  02已审核
    String UN_AUDITED = "01";
    String AUDITED = "02";


    // 状态： 0 未激活 1 已激活
    Integer UN_ACTIVATED = 0;
    Integer ACTIVATED = 1;
}
