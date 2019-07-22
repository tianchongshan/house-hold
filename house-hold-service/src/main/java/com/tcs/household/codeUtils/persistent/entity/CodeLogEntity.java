package com.tcs.household.codeUtils.persistent.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * @author xiaoj
 *
 */
public class CodeLogEntity {
    private int id;
    private String tableName;
    private String columnName;
    private Date generateDate;
    private long curNum;
    private String remark;
    private byte flag;
    private Timestamp createTime;
    private Timestamp updateTime;
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

    public long getCurNum() {
        return curNum;
    }

    public void setCurNum(long curNum) {
        this.curNum = curNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
