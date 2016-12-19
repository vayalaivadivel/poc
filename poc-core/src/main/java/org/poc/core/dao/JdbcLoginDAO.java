package org.poc.core.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Spring JDBC implementation of Data Access Object.
 *
 * @author vadivel 12/15/2016
 */
public class JdbcLoginDAO implements LoginDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_USER_BY_USERNAME_AND_PWD =
        "SELECT * FROM login WHERE username='%s' AND password='%s'";

    @Override
    @Transactional(readOnly = true)
    public boolean isValidUser(final String userName, final String password) {
        final List<Map<String, Object>> list = jdbcTemplate.queryForList(
            String.format(
                SELECT_USER_BY_USERNAME_AND_PWD,
                userName,
                password));
        if (list == null || list.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
