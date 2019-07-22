package com.tcs.household.codeUtils.persistent.entity;

import java.sql.Timestamp;

/**
 * 
 * @author xiaoj
 *
 */
public class CodeEntity {
    private int id;
    private String tableName;
    private String columnName;
    private String rule;
    private String dateFormat;
    private int numLength;
    private long maxNum;
    private byte isCache;
    private long cacheNum;
    private String remark;
    private byte flag;
    private Timestamp createTime;
    private Timestamp updateTime;

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

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public int getNumLength() {
        return numLength;
    }

    public void setNumLength(int numLength) {
        this.numLength = numLength;
    }

    public long getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(long maxNum) {
        this.maxNum = maxNum;
    }

    public byte getIsCache() {
        return isCache;
    }

    public void setIsCache(byte isCache) {
        this.isCache = isCache;
    }

    public long getCacheNum() {
        return cacheNum;
    }

    public void setCacheNum(long cacheNum) {
        this.cacheNum = cacheNum;
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
}
