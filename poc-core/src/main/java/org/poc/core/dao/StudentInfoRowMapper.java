package org.poc.core.dao;
import org.poc.core.domain.StudentInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import static org.poc.core.dao.JdbcConstants.*;

/**
 * Maps a database row to an {@link StudentInfo} object.
 *
 * @author vmuniyandi 12/14/2016
 */
public class StudentInfoRowMapper implements RowMapper<StudentInfo> {

    @Override
    public StudentInfo mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return StudentInfo
            .builder()
            .withRegNo(rs.getInt(REG_NO))
            .withName(rs.getString(NAME))
            .withAge((rs.getInt(AGE)))
            .withTotalMark(rs.getInt(TOTAL_MARK))
            .build();
    }
}
