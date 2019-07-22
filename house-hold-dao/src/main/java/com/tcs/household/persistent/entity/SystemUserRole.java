package com.tcs.household.persistent.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by chongshan.tian01.
 */

@Table(name = "t_wm_system_user_role")
public class SystemUserRole implements Serializable {
    private static final long serialVersionUID = 1166343439247911666L;

    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`user_id`")
    private Integer userId;

    @Column(name = "`role_id`")
    private Integer roleId;

    @Column(name = "`create_time`")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public SystemUserRole setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public SystemUserRole setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public SystemUserRole setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SystemUserRole setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
