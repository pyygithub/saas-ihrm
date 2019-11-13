/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/11/13 14:38:35                          */
/*==============================================================*/


drop table if exists COMMON_COLUMN;

drop table if exists company;

drop table if exists department;

drop table if exists permission;

drop table if exists permission_api;

drop table if exists permission_button;

drop table if exists permission_menu;

drop table if exists role;

drop table if exists role_permission;

drop table if exists user;

drop table if exists user_role;

/*==============================================================*/
/* Table: COMMON_COLUMN                                         */
/*==============================================================*/
create table COMMON_COLUMN
(
   create_id            varchar(40) comment '创建人ID',
   create_name          varchar(40) comment '创建人名称',
   create_time          datetime comment '创建时间',
   update_id            varchar(40) comment '更新人ID',
   update_name          varchar(40) comment '更新人名称',
   update_time          datetime comment '更新时间',
   is_deleted           tinyint comment '删除标记 0：正常 1：删除'
);

/*==============================================================*/
/* Table: company                                               */
/*==============================================================*/
create table company
(
   id                   varchar(40) not null comment 'ID',
   name                 varchar(255) comment '公司名称',
   balance              decimal comment '当前余额',
   audit_state          varchar(255) comment '审核状态',
   industry             varchar(255) comment '所属行业',
   company_size         varchar(255) comment '公司规模',
   mailbox              varchar(255) comment '邮箱',
   company_phone        varchar(255) comment '公司电话',
   legal_representative varchar(255) comment '法人代表',
   business_license_id  varchar(255) comment '营业执照-图片ID',
   company_address      varchar(255) comment '公司地址',
   company_area         varchar(255) comment '公司地区',
   expiration_date      datetime comment '到期时间',
   remarks              text comment '备注',
   renewal_date         datetime comment '续期时间',
   version              varchar(255) comment '当前版本',
   manager_id           varchar(255) comment '企业登录账号ID',
   state                varchar(2) comment '状态',
   create_id            varchar(40) comment '创建人ID',
   create_name          varchar(40) comment '创建人名称',
   create_time          datetime comment '创建时间',
   update_id            varchar(40) comment '更新人ID',
   update_name          varchar(40) comment '更新人名称',
   update_time          datetime comment '更新时间',
   is_deleted           tinyint comment '删除标记 0：正常 1：删除',
   primary key (id)
);

alter table company comment '企业信息';

/*==============================================================*/
/* Table: department                                            */
/*==============================================================*/
create table department
(
   id                   varchar(40) not null comment 'ID',
   parent_id            varchar(40) comment '父级ID',
   company_id           varchar(40) comment '企业ID',
   code                 varchar(255) comment '部门编码',
   name                 varchar(255) comment '部门名称',
   manager_id           varchar(255) comment '负责人ID',
   manager_name         varchar(255) comment '负责人名称',
   introduce            text comment '介绍',
   create_id            varchar(40) comment '创建人ID',
   create_name          varchar(40) comment '创建人名称',
   create_time          datetime comment '创建时间',
   update_id            varchar(40) comment '更新人ID',
   update_name          varchar(40) comment '更新人名称',
   update_time          datetime comment '更新时间',
   is_deleted           tinyint comment '删除标记 0：正常 1：删除',
   primary key (id)
);

alter table department comment '部门';

/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table permission
(
   id                   varchar(40) not null comment 'ID',
   company_id           varchar(40) comment '企业ID',
   name                 varchar(255) comment '权限名称',
   code                 varchar(255) comment '权限标识',
   type                 varchar(2) comment '权限类型: 1为菜单 2为功能 3为API',
   description          varchar(255) comment '权限描述',
   en_visible           varchar(2) comment '可见状态: 0 不可见 1 可见',
   create_id            varchar(40) comment '创建人ID',
   create_name          varchar(40) comment '创建人名称',
   create_time          datetime comment '创建时间',
   update_id            varchar(40) comment '更新人ID',
   update_name          varchar(40) comment '更新人名称',
   update_time          datetime comment '更新时间',
   is_deleted           tinyint comment '删除标记 0：正常 1：删除',
   primary key (id)
);

alter table permission comment '权限';

/*==============================================================*/
/* Table: permission_api                                        */
/*==============================================================*/
create table permission_api
(
   id                   varchar(40) not null comment 'ID',
   permission_id        varchar(40) comment '权限ID',
   api_url              varchar(255) comment '链接',
   api_method           varchar(20) comment '请求类型',
   api_level            varchar(2) comment '权限等级，1为通用接口权限，2为需校验接口权限',
   primary key (id)
);

alter table permission_api comment 'API接口权限';

/*==============================================================*/
/* Table: permission_button                                     */
/*==============================================================*/
create table permission_button
(
   id                   varchar(40) not null comment 'ID',
   permission_id        varchar(40) comment '权限ID',
   icon                 varchar(255) comment '图标',
   primary key (id)
);

alter table permission_button comment '按钮权限';

/*==============================================================*/
/* Table: permission_menu                                       */
/*==============================================================*/
create table permission_menu
(
   id                   varchar(40) not null comment 'ID',
   permission_id        varchar(40) comment '权限ID',
   icon                 varchar(255) comment '图标',
   uri                  varchar(255) comment '地址',
   order_no             int comment '排序号',
   primary key (id)
);

alter table permission_menu comment '菜单权限';

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   varchar(40) not null comment 'ID',
   company_id           varchar(40) comment '企业id',
   description          varchar(255) comment '说明',
   name                 varchar(255) comment '角色名',
   create_id            varchar(40) comment '创建人ID',
   create_name          varchar(40) comment '创建人名称',
   create_time          datetime comment '创建时间',
   update_id            varchar(40) comment '更新人ID',
   update_name          varchar(40) comment '更新人名称',
   update_time          datetime comment '更新时间',
   is_deleted           tinyint comment '删除标记 0：正常 1：删除',
   primary key (id)
);

alter table role comment '角色';

/*==============================================================*/
/* Table: role_permission                                       */
/*==============================================================*/
create table role_permission
(
   role_id              varchar(40) not null comment '角色ID',
   permission_id        varchar(40) not null comment '权限ID',
   primary key (permission_id, role_id)
);

alter table role_permission comment '角色权限';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   varchar(40) not null comment 'ID',
   work_number          varchar(255) comment '工号',
   company_id           varchar(40) comment '企业ID',
   department_id        varchar(255) comment '部门ID',
   username             varchar(255) comment '用户名称',
   password             varchar(255) comment '密码',
   mobile               varchar(255) comment '手机号码',
   time_of_entry        datetime comment '入职时间',
   form_of_employment   varchar(255) comment '聘用形式',
   form_of_management   varchar(255) comment '管理形式',
   working_city         varchar(255) comment '工作城市',
   correction_time      datetime comment '转正时间',
   in_service_status    varchar(2) comment '在职状态 1：在职  2：离职',
   enable_state         varchar(2) comment '启用状态 0：禁用  1：启用',
   create_id            varchar(40) comment '创建人ID',
   create_name          varchar(40) comment '创建人名称',
   create_time          datetime comment '创建时间',
   update_id            varchar(40) comment '更新人ID',
   update_name          varchar(40) comment '更新人名称',
   update_time          datetime comment '更新时间',
   is_deleted           tinyint comment '删除标记 0：正常 1：删除',
   primary key (id)
);

alter table user comment '用户';

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role
(
   user_id              varchar(40) not null comment '用户ID',
   role_id              varchar(40) not null comment '角色ID',
   primary key (user_id, role_id)
);

alter table user_role comment '用户角色';

