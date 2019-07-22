package com.tcs.household.mgr.persistent.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统角色表
 * Created by chongshan.tian01.
 */
@Table(name = "t_wm_system_role")
public class SystemRole {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "`id`",updatable =false)
    private Integer id;

    @Column(name = "`role_name`")
    private String roleName;

    @Column(name = "`nick_name")
    private String nickName;

    @Column(name = "`data_scope`")
    private Integer dataScope;

    @Column(name = "`remark`")
    private String remark;

    @Column(name = "`create_time`")
    private Date createTime;

    @Column(name = "`update_time`")
    private Date updateTime;

    @Column(name = "`falg`")
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public SystemRole setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public SystemRole setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public SystemRole setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public Integer getDataScope() {
        return dataScope;
    }

    public SystemRole setDataScope(Integer dataScope) {
        this.dataScope = dataScope;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public SystemRole setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SystemRole setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public SystemRole setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getFlag() {
        return flag;
    }

    public SystemRole setFlag(Integer flag) {
        this.flag = flag;
        return this;
    }
}
