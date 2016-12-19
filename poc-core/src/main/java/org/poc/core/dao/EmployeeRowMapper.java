package org.poc.core.dao;

import org.poc.core.domain.EmployeeInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.poc.core.dao.JdbcConstants.*;

/**
 * Maps a database row to an {@link EmployeeInfo} object.
 *
 * @author vmuniyandi 12/19/2016
 */
public class EmployeeRowMapper implements RowMapper<EmployeeInfo> {

    @Override
    public EmployeeInfo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return EmployeeInfo
            .builder()
            .withEmpNo(rs.getInt(EMP_NO))
            .withName(rs.getString(NAME))
            .withAge((rs.getInt(AGE)))
            .withSalary(rs.getDouble(SALARY))
            .build();
    }
}