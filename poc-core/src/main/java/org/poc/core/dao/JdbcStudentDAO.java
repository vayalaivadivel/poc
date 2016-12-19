package org.poc.core.dao;

import com.google.common.collect.ImmutableMap;
import org.poc.core.domain.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.poc.core.dao.JdbcConstants.*;

import java.util.List;

/**
 * Spring JDBC implementation of Data Access Object for {@link StudentInfo}.
 *
 * @author vadivel 12/14/2016
 */
@Repository
public class JdbcStudentDAO implements StudentDAO {
    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<StudentInfo> rowMapper = new StudentInfoRowMapper();

    private static final String FIND_STUDENT_INFO_BY_REG_NO =
        "SELECT * FROM student WHERE reg_no= :reg_no";

    private static final String DELETE_STUDENT_INFO_BY_REG_NO =
        "DELETE FROM student WHERE reg_no= :reg_no";

    private static final String DELETE_ALL_STUDENTS =
        "DELETE FROM student";

    private static final String CREATE_STUDENT_INFO =
        "INSERT INTO student (name, age, total_mark) VALUES (:name, :age, :total_mark)";

    private static final String UPDATE_STUDENT_INFO =
        "UPDATE student SET name=:name , age= :age, total_mark= :total_mark WHERE reg_no= :reg_no";

    private static final String FIND_ALL_STUDENTS =
        "SELECT * FROM student";

    @Override
    public int create(final StudentInfo student) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        parameterJdbcTemplate.update(
            CREATE_STUDENT_INFO,
            new MapSqlParameterSource()
                .addValue(NAME, student.getName())
                .addValue(AGE, student.getAge())
                .addValue(TOTAL_MARK, student.getTotalMark()),
            keyHolder,
            new String[]{REG_NO}
        );

        return keyHolder.getKey().intValue();

    }

    @Override
    @Transactional(readOnly = true)
    public StudentInfo findByPK(final Integer regNo) {
        return parameterJdbcTemplate.queryForObject(
            FIND_STUDENT_INFO_BY_REG_NO,
            ImmutableMap.of(REG_NO, regNo),
            rowMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentInfo> findAll() {
        return parameterJdbcTemplate.query(FIND_ALL_STUDENTS, rowMapper);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int delete(final Integer regNo) {
        return parameterJdbcTemplate.update(DELETE_STUDENT_INFO_BY_REG_NO, ImmutableMap.of(REG_NO, regNo));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int deleteAll() {
        return jdbcTemplate.update(DELETE_ALL_STUDENTS);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int update(final StudentInfo student) {

        return parameterJdbcTemplate.update(
            UPDATE_STUDENT_INFO,
            new MapSqlParameterSource()
                .addValue(NAME, student.getName())
                .addValue(AGE, student.getAge())
                .addValue(TOTAL_MARK, student.getTotalMark())
                .addValue(REG_NO, student.getRegNo())
        );
    }
}