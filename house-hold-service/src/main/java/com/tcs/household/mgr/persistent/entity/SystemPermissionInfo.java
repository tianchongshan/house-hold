package com.tcs.household.mgr.persistent.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统权限信息表
 * Created by chongshan.tian01.
 */
@ApiModel(value = "系统权限信息表")
@Table(name = "t_wm_system_permission_info")
public class SystemPermissionInfo {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "`id`",updatable = false)
    @ApiModelProperty(name = "id",notes = "id",dataType = "Integer",required = true)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "`perm_name`")
    @ApiModelProperty(name = "permName",notes = "名称",dataType = "String",required = true)
    private String permName;

    /**
     * 描述
     */
    @Column(name = "`remark`")
    @ApiModelProperty(name = "remark",notes = "描述",dataType = "String",required = true)
    private String remark;

    /**
     * URL/方法
     */
    @Column(name = "`url`")
    @ApiModelProperty(name = "url",notes = "url",dataType = "String",required = true)
    private String url;
    /**
     * 图标
     */
    @Column(name = "`icon`")
    @ApiModelProperty(name = "icon",notes = "图标",dataType = "String",required = true)
    private String icon;
    /**
     * 权限表示 (shiro/security 框架注解用)
     */
    @Column(name = "`permission`")
    @ApiModelProperty(name = "permission",notes = "权限表示",dataType = "String",required = true)
    private String permission;
    /**
     * 上级ID
     */
   @Column(name="`p_id`")
   @ApiModelProperty(name = "pid",notes = "上级ID",dataType = "Integer",required = true)
   private Integer pid;
    /**
     * 排序
     */
   @Column(name="`sort`")
   @ApiModelProperty(name = "sort",notes = "排序",dataType = "Integer",required = true)
   private Integer sort;
    /**
     * 类型 类型 1--菜单 2--动作 3--服务方法
     */
   @Column(name="`type`")
   @ApiModelProperty(name = "type",notes = "类型",dataType = "Integer",required = true)
   private Integer type;

   @Column(name = "`method`")
   @ApiModelProperty(name = "method",notes = "方法",dataType = "String",required = true)
   private String method;

   @Column(name = "`create_time`")
   @ApiModelProperty(name = "createTime",notes = "创建时间",dataType = "Date",required = true)
   private Date createTime;

   @Column(name = "`modify_time`")
   @ApiModelProperty(name = "modify_time",notes = "修改时间",dataType = "Date",required = true)
   private Date modifyTime;

   @Column(name = "`flag`")
   @ApiModelProperty(name = "flag",notes = "标志",dataType = "Integer",required = true)
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

