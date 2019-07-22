package com.tcs.household.persistent.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_wm_system_user_info")
public class SystemUserInfo {

    @Id
    @GeneratedValue(generator="JDBC")
    @Column(name = "`id`",updatable =false)
    private Integer id;

    @Column(name = "`login_name`")
    private String loginName;

    @Column(name = "`user_name`")
    private String userName;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`user_code`")
    private String userCode;

    @Column(name = "`type`")
    private Integer type;

    @Column(name = "`mobile_no`")
    private String mobileNo;

    @Column(name = "`last_login_time")
    private Date lastLoginTime;

    @Column(name = "`last_login_ip")
    private String lastLoginIp;

    @Column(name = "`is_freeze`")
    private Integer isFreeze;

    @Column(name = "`flag`")
    private Integer flag;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "`updateTime`")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public SystemUserInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public SystemUserInfo setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SystemUserInfo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SystemUserInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserCode() {
        return userCode;
    }

    public SystemUserInfo setUserCode(String userCode) {
        this.userCode = userCode;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public SystemUserInfo setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public SystemUserInfo setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public SystemUserInfo setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public SystemUserInfo setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
        return this;
    }

    public Integer getIsFreeze() {
        return isFreeze;
    }

    public SystemUserInfo setIsFreeze(Integer isFreeze) {
        this.isFreeze = isFreeze;
        return this;
    }

    public Integer getFlag() {
        return flag;
    }

    public SystemUserInfo setFlag(Integer flag) {
        this.flag = flag;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SystemUserInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public SystemUserInfo setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
