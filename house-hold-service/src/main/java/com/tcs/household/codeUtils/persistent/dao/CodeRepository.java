package com.tcs.household.codeUtils.persistent.dao;


import com.tcs.household.codeUtils.persistent.entity.CodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author xiaoj
 *
 */
public class CodeRepository {

    private static final String SQL_GET_TEMPLATE_BY_NAME = "select table_name, column_name, rule, date_format, num_length, max_num, is_cache, cache_num from t_wm_system_code where table_name = :tableNm and column_name = :columnNm and flag = 0";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public CodeEntity getCodeTemplateByName(String tableNm, String columnNm) {
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("tableNm", tableNm);
        mapParam.put("columnNm", columnNm);

        try {
            return jdbcTemplate.queryForObject(SQL_GET_TEMPLATE_BY_NAME, mapParam, new RowMapper<CodeEntity>() {
                @Override
                public CodeEntity mapRow(ResultSet rs, int i) throws SQLException {
                    CodeEntity c = new CodeEntity();
                    c.setTableName(rs.getString("table_name"));
                    c.setColumnName(rs.getString("column_name"));
                    c.setRule(rs.getString("rule"));
                    c.setDateFormat(rs.getString("date_format"));
                    c.setNumLength(rs.getInt("num_length"));
                    c.setMaxNum(rs.getLong("max_num"));
                    c.setIsCache(rs.getByte("is_cache"));
                    c.setCacheNum(rs.getInt("cache_num"));
                    return c;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
