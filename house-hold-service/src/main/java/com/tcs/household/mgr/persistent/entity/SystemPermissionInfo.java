package com.tcs.household.mgr.persistent.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统权限信息表
 * Created by chongshan.tian01.
 */

@Table(name = "t_wm_system_permission_info")
public class SystemPermissionInfo {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "`id`",updatable = false)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "`perm_name`")
    private String permName;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * URL/方法
     */
    @Column(name = "`url`")
    private String url;
    /**
     * 图标
     */
    @Column(name = "`icon`")
    private String icon;
    /**
     * 权限表示 (shiro/security 框架注解用)
     */
    @Column(name = "`permission`")
    private String permission;
    /**
     * 上级ID
     */
   @Column(name="`p_id`")
    private Integer pid;
    /**
     * 排序
     */
   @Column(name="`sort`")
   private Integer sort;
    /**
     * 类型 类型 1--菜单 2--动作 3--服务方法
     */
   @Column(name="`type`")
   private Integer type;

   @Column(name = "`method`")
   private String method;

   @Column(name = "`creaateTime`")
   private Date createTime;

   @Column(name = "`modify_time`")
   private Date modifyTime;

   @Column(name = "`flag`")
   private Integer flag;

    public Integer getId() {
        return id;
    }

    public SystemPermissionInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getPermName() {
        return permName;
    }

    public SystemPermissionInfo setPermName(String permName) {
        this.permName = permName;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public SystemPermissionInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SystemPermissionInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public SystemPermissionInfo setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getPermission() {
        return permission;
    }

    public SystemPermissionInfo setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public Integer getPid() {
        return pid;
    }

    public SystemPermissionInfo setPid(Integer pid) {
        this.pid = pid;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public SystemPermissionInfo setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public SystemPermissionInfo setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public SystemPermissionInfo setMethod(String method) {
        this.method = method;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SystemPermissionInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public SystemPermissionInfo setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public Integer getFlag() {
        return flag;
    }

    public SystemPermissionInfo setFlag(Integer flag) {
        this.flag = flag;
        return this;
    }
}

