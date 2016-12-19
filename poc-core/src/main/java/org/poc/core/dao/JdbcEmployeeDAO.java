package org.poc.core.dao;

import com.google.common.collect.ImmutableMap;
import org.poc.core.domain.EmployeeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.poc.core.dao.JdbcConstants.*;

/**
 * Spring JDBC implementation of Data Access Object for {@link EmployeeInfo}.
 *
 * @author vmuniyandi 12/19/2016
 */
public class JdbcEmployeeDAO implements EmployeeDAO {

    @Autowired
    private NamedParameterJdbcTemplate parameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<EmployeeInfo> rowMapper = new EmployeeRowMapper();

    private static final String FIND_EMPLOYEE_INFO_BY_EMP_NO =
        "SELECT * FROM employee WHERE emp_no= :emp_no";

    private static final String DELETE_EMPLOYEE_INFO_BY_EMP_NO =
        "DELETE FROM employee WHERE emp_no= :emp_no";

    private static final String DELETE_ALL_EMPLOYEES =
        "DELETE FROM employee";

    private static final String CREATE_EMPLOYEE_INFO =
        "INSERT INTO employee (name, age, salary) VALUES (:name, :age, :salary)";

    private static final String UPDATE_EMPLOYEE_INFO =
        "UPDATE employee SET name=:name , age= :age, salary= :salary WHERE emp_no= :emp_no";

    private static final String FIND_ALL_EMPLOYEES =
        "SELECT * FROM employee";

    @Override
    public int create(final EmployeeInfo employeeInfo) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        parameterJdbcTemplate.update(
            CREATE_EMPLOYEE_INFO,
            new MapSqlParameterSource()
                .addValue(NAME, employeeInfo.getName())
                .addValue(AGE, employeeInfo.getAge())
                .addValue(SALARY, employeeInfo.getSalary()),
            keyHolder,
            new String[]{EMP_NO}
        );

        return keyHolder.getKey().intValue();

    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeInfo findByPK(final Integer empNo) {
        return parameterJdbcTemplate.queryForObject(
            FIND_EMPLOYEE_INFO_BY_EMP_NO,
            ImmutableMap.of(EMP_NO, empNo),
            rowMapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeInfo> findAll() {
        return parameterJdbcTemplate.query(FIND_ALL_EMPLOYEES, rowMapper);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int delete(final Integer empNo) {
        return parameterJdbcTemplate.update(DELETE_EMPLOYEE_INFO_BY_EMP_NO, ImmutableMap.of(EMP_NO, empNo));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int deleteAll() {
        return jdbcTemplate.update(DELETE_ALL_EMPLOYEES);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int update(final EmployeeInfo employeeInfo) {

        return parameterJdbcTemplate.update(
            UPDATE_EMPLOYEE_INFO,
            new MapSqlParameterSource()
                .addValue(NAME, employeeInfo.getName())
                .addValue(AGE, employeeInfo.getAge())
                .addValue(SALARY, employeeInfo.getSalary())
                .addValue(EMP_NO, employeeInfo.getEmpNo())
        );
    }

}
