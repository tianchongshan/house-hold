package com.tcs.household.mgr.persistent.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 角色授权信息
 * Created by chongshan.tian01.
 */
@Table(name = "t_wm_system_role_permission")
public class SystemRolePermission {


    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "`id`",updatable = false)
    private Integer id;

    @Column(name = "`role_id`")
    private Integer roleId;

    @Column(name = "`permission_id`")
    private Integer permissionId;

    @Column(name = "create_time")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public SystemRolePermission setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public SystemRolePermission setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public SystemRolePermission setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SystemRolePermission setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
