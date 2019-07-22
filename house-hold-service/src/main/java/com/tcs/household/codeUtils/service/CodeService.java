package com.tcs.household.codeUtils.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.tcs.household.codeUtils.persistent.dao.CodeLogRepository;
import com.tcs.household.codeUtils.persistent.dao.CodeRepository;
import com.tcs.household.codeUtils.persistent.entity.CodeEntity;
import com.tcs.household.codeUtils.persistent.entity.CodeLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 取号服务
 * @author xiaoj
 *
 */
public class CodeService {

    private static final String DATE = "%D";
    private static final String NUM = "%N";
    private static final String RANDOM = "%R";

    @Autowired
    private CodeRepository codeDao;

    @Autowired
    private CodeLogRepository codeLogDao;

    @Autowired
    @Qualifier("txTemplate_require_new")
    private TransactionTemplate txTemplate;

    /**
     * 取号对象缓存
     */
    private ConcurrentHashMap<String, Version> mapVersion = new ConcurrentHashMap<>();

    /**
     * 模板数据缓存
     */
    private final ConcurrentHashMap<String, CodeEntity> mapTemplate = new ConcurrentHashMap<String, CodeEntity>();


    /**
     * 生成Key
     * @param tableNm
     * @param columnNm
     * @param bizDate
     * @return
     */
    public String generateCode(String tableNm, String columnNm, Date bizDate, Map<String, String> params) {
        if (StrUtil.isEmpty(tableNm) || StrUtil.isEmpty(columnNm)) {
            throw new RuntimeException("Invalid Parameter Exception");
        }

        //取得模板
        CodeEntity codeTemplate = getTemplate(tableNm, columnNm);

        // 判断缓存
        // 生成缓存Key
        String key = getVersionKey(bizDate, codeTemplate);

        // 取得缓存Version对象
        Version version = mapVersion.get(key);
        if (version == null) {
            synchronized (this) {
                version = mapVersion.get(key);
                if (version == null) {
                    version = new Version(tableNm, columnNm, bizDate, codeTemplate.getDateFormat(), key,
                            codeTemplate.getCacheNum(), codeTemplate.getIsCache() == 1);
                    mapVersion.putIfAbsent(key, version);
                }
            }
        }
        // 取得数据（最新值）
        long nextval = getNextValue(version);

        // 根据规则生成Key
        return getFormatCode(nextval, bizDate, codeTemplate, params);
    }

    /**
     * 取得数据（最新值）
     * @param v
     * @return
     */
    private long getNextValue(Version v) {
        synchronized (v) {
            if (v.checkVal()) {
                return v.getNextVal(1L);
            } else {
                // 数据库重新获取
                long nextMaxNum = getNextMaxNumFromDb(v);
                // 设置当前值
                v.setMaxVal(nextMaxNum);
                v.setCurrVal(nextMaxNum - v.getCacheNum());
                return v.getNextVal(1L);
            }
        }
    }

    /**
     * 从数据库中取得最新的值
     * @param version
     * @return
     */
    private Long getNextMaxNumFromDb(final Version version) {
        return txTemplate.execute(new TransactionCallback<Long>() {
            @Override
            public Long doInTransaction(TransactionStatus transactionStatus) {
                codeLogDao.lockCodeLog(version.getTableNm(), version.getColumnNm(), version.getBizDt());
                CodeEntity codeTemplate = getTemplate(version.getTableNm(), version.getColumnNm());
                Version v = mapVersion.get(version.getKey());
                if (v.checkVal()) {
                    return v.getCurrNum();
                }

                CodeLogEntity codeLog = codeLogDao.getCodeLog(v.getTableNm(), v.getColumnNm(), v.getBizDt());
                if (1 == codeTemplate.getIsCache()) {
                    codeLog.setCurNum(codeLog.getCurNum() + codeTemplate.getCacheNum());
                } else {
                    codeLog.setCurNum(codeLog.getCurNum() + 1);
                }
                if (codeLog.getCurNum() > codeTemplate.getMaxNum()) {
                    throw new RuntimeException("号码资源已用完");
                }
                // 更新回数据库
                codeLogDao.saveCodeLog(codeLog);
                return codeLog.getCurNum();
            }
        });
    }


    /**
     * 取得模板数据
     *
     * @param tableNm
     * @param columnNm
     * @return
     */
    private CodeEntity getTemplate(String tableNm, String columnNm) {

        StringBuilder sb = new StringBuilder();
        sb.append(tableNm);
        sb.append("_");
        sb.append(columnNm);
        String key = sb.toString();
        if (!mapTemplate.containsKey(key)) {
            CodeEntity code = codeDao.getCodeTemplateByName(tableNm, columnNm);
            if (code == null) {
                throw new RuntimeException("code Template Not Exists");
            }
            mapTemplate.putIfAbsent(key, code);
        }
        return mapTemplate.get(key);
    }

    /**
     * 生成Version缓存Key
     * @param bizDate
     * @param codeTemplate
     * @return
     */
    private String getVersionKey(Date bizDate, CodeEntity codeTemplate) {
        StringBuilder sb = new StringBuilder();
        sb.append(codeTemplate.getTableName());
        sb.append("_");
        sb.append(codeTemplate.getColumnName());
        if (!StrUtil.isEmpty(codeTemplate.getDateFormat())) {
            if (bizDate == null) {
                throw new RuntimeException("Generate Exception : bizDt is null");
            }
            sb.append("_");
            sb.append(DateUtil.format(bizDate, codeTemplate.getDateFormat()));
        }
        return sb.toString();
    }

    /**
     * 生成GenerateKey
     *
     * @param code 当前号
     * @param d 日期
     * @return
     */
    private String getFormatCode(Long code, Date d, CodeEntity template, Map<String, String> params) {
        String rule = template.getRule();
        String fmtStr = rule;
        if (d != null && StrUtil.isNotEmpty(template.getDateFormat())) {
        	fmtStr = rule.replace(DATE, DateUtil.format(d, template.getDateFormat())).replace(NUM,
                    StrUtil.padPre(String.valueOf(code), 8, '0'));
        } else {
        	fmtStr = rule.replace(NUM, StrUtil.padPre(String.valueOf(code), template.getNumLength(), '0'));
        }
        if (fmtStr.indexOf(RANDOM) >= 0) {
            fmtStr = fmtStr.replace(RANDOM, RandomUtil.randomNumbers(2));
        }
        if (params != null && params.size() > 0) {
        	for (Entry<String, String> e : params.entrySet()) {
        		fmtStr = fmtStr.replace(e.getKey(), e.getValue());
        	}
        }
        return fmtStr;
    }

    class Version implements Serializable {

        private static final long serialVersionUID = 4703133792162702666L;

        /**
         * 表名
         */
        private String tableNm;

        /**
         * 字段名
         */
        private String columnNm;

        /**
         * 业务日期
         */
        private Date bizDt;

        /**
         * 缓存Key
         */
        private String key;

        /**
         * 缓存个数
         */
        private volatile long cacheNum;

        /**
         * 缓存是否开启
         */
        private boolean isCache = true;

        /**
         * 缓存最大值
         */
        private AtomicLong maxVal;

        /**
         * 当前值
         */
        private AtomicLong currVal;

        /**
         *
         * @param tableNm
         * @param columnNm
         * @param bizDt
         * @param dateFormat
         * @param key
         * @param cacheNum
         * @param isCache
         */
        public Version(String tableNm, String columnNm, Date bizDt, String dateFormat, String key,
            long cacheNum, boolean isCache) {
            this.tableNm = tableNm;
            this.columnNm = columnNm;
            if (bizDt != null && StrUtil.isNotEmpty(dateFormat)) {
				this.bizDt = DateUtil.parse(DateUtil.format(bizDt, dateFormat), dateFormat);
            }
            this.key = key;
            this.isCache = isCache;
            this.cacheNum = isCache ? cacheNum : 1;
            this.maxVal = new AtomicLong(0);
            this.currVal = new AtomicLong(0);
        }

            /**
             * 取得当前值
             * @return
             */
        public long getCurrNum() {
            return this.currVal.get();
        }

        /**
         * 设置当前值
         * @param currVal
         */
        public void setCurrVal(long currVal) {
            this.currVal.set(currVal);
        }

        /**
         * 取得最新值
         * @param num
         * @return
         */
        public long getNextVal(long num) {
            if (num <= 0) {
                num = 1L;
            }
            return this.currVal.addAndGet(num);
        }

        /**
         * 检查是否有余量可用
         * @return 返回成功或失败
         */
        public boolean checkVal() {
            // 启用缓存，并且当前值  < 缓存最大值
            return isCache && maxVal.get() > currVal.get();
        }

        /**
         * 取得表名
         * @return
         */
        public String getTableNm() {
            return tableNm;
        }

        /**
         * 取得字段名
         * @return
         */
        public String getColumnNm() {
            return columnNm;
        }

        /**
         * 取得缓存大小
         * @return
         */
        public long getCacheNum() {
            return cacheNum;
        }

        /**
         * 设置缓存最大值
         * @param maxVal
         */
        public void setMaxVal(long maxVal) {
            this.maxVal.set(maxVal);
        }

        /**
         * 取得缓存Key
         * @return
         */
        public String getKey() {
            return key;
        }

        /**
         * 取得业务日期
         * @return
         */
        public Date getBizDt() {
            return bizDt;
        }

    }
}
