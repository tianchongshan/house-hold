package com.tcs.household.codeUtils.persistent.dao;

import cn.hutool.core.date.DateUtil;
import com.tcs.household.codeUtils.persistent.entity.CodeLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author xiaoj
 *
 */
public class CodeLogRepository {

    private static final String SQL_GET_CODE_LOG = "select id, table_name, column_name, generate_date, cur_num, remark from t_wm_system_code_log where flag = 0 and table_name = :tableNm and column_name = :columnNm";

    private static final String SQL_GET_CODE_LOG_BY_DATE = "select id, table_name, column_name, generate_date, cur_num, remark from t_wm_system_code_log where flag = 0 and table_name = :tableNm and column_name = :columnNm and generate_date = :date";

    private static final String SQL_LOCK_CODE_LOG = "insert into t_wm_system_code_log (table_name, column_name, generate_date, cur_num, create_time) select table_name, column_name, :bizDt, 0, current_date() from t_wm_system_code where table_name = :tableNm and column_name = :columnNm and flag = 0 on duplicate key update version = version + 1";

    private static final String SQL_SAVE_CODE_LOG = "update t_wm_system_code_log set cur_num = :curNum where id = :id";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     *
     * @param tableNm
     * @param columnNm
     * @param bizDt
     * @return
     */
    public CodeLogEntity getCodeLog(String tableNm, String columnNm, Date bizDt) {
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("tableNm", tableNm);
        mapParam.put("columnNm", columnNm);
        if (bizDt != null) {
            mapParam.put("date", DateUtil.parse(DateUtil.format(bizDt, "yyyyMMdd"), "yyyyMMdd"));
        }
        String sql = bizDt != null ? SQL_GET_CODE_LOG_BY_DATE : SQL_GET_CODE_LOG;
        try {
            return jdbcTemplate.queryForObject(sql, mapParam, new RowMapper<CodeLogEntity>() {
                @Override
                public CodeLogEntity mapRow(ResultSet rs, int i) throws SQLException {
                    CodeLogEntity log = new CodeLogEntity();
                    log.setId(rs.getInt("id"));
                    log.setTableName(rs.getString("table_name"));
                    log.setTableName(rs.getString("column_name"));
                    log.setGenerateDate(rs.getDate("generate_date"));
                    log.setCurNum(rs.getLong("cur_num"));
                    log.setRemark(rs.getString("remark"));
                    return log;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * 锁定数据
     * @param tableNm
     * @param columnNm
     * @param bizDt
     */
    public int lockCodeLog(String tableNm, String columnNm, Date bizDt) {

        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("tableNm", tableNm);
        mapParam.put("columnNm", columnNm);
        mapParam.put("bizDt", bizDt == null ? new Date(0L) : bizDt);

        return jdbcTemplate.update(SQL_LOCK_CODE_LOG, mapParam);
    }

    public int saveCodeLog(CodeLogEntity codeLog) {

        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("id", codeLog.getId());
        mapParam.put("curNum", codeLog.getCurNum());
        return jdbcTemplate.update(SQL_SAVE_CODE_LOG, mapParam);
    }
}
