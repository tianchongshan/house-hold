package com.tcs.household.persistent.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * create by chongshan.tian01
 **/
@Table(name = "`t_customer`")
public class Customer {


    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "`id`", updatable=false)
    private Integer id;

    @Column(name = "`custom_code`")
    private String customCode;

    @Column(name = "`custom_name`")
    private String customName;

    @Column(name = "`custom_type`")
    private Integer CustomType;

    @Column(name = "`channel`")
    private String channel;

    @Column(name = "`create_time`")
    private Date createTime;

    @Column(name = "`update_time`")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public Customer setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCustomCode() {
        return customCode;
    }

    public Customer setCustomCode(String customCode) {
        this.customCode = customCode;
        return this;
    }

    public String getCustomName() {
        return customName;
    }

    public Customer setCustomName(String customName) {
        this.customName = customName;
        return this;
    }

    public Integer getCustomType() {
        return CustomType;
    }

    public Customer setCustomType(Integer customType) {
        CustomType = customType;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public Customer setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Customer setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Customer setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
