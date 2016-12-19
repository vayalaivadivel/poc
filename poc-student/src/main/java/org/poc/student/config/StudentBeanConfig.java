package org.poc.student.config;

import org.poc.core.dao.JdbcLoginDAO;
import org.poc.core.dao.JdbcStudentDAO;
import org.poc.core.dao.LoginDAO;
import org.poc.core.dao.StudentDAO;
import org.poc.core.service.AuthenticationService;
import org.poc.core.validation.RequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Declared some of the Beans here since those are belongs
 * to some other modules. We have to import this class in main method.
 * So those objects will be wired automatically during the boot.
 *
 * @author vmuniyandi 12/18/2016
 */
@Configuration
public class StudentBeanConfig {
    @Bean
    public StudentDAO studentDAO() {
        return new JdbcStudentDAO();
    }

    @Bean
    public LoginDAO loginDAO() {
        return new JdbcLoginDAO();
    }

    @Bean
    public AuthenticationService authenticationService() {
        return new AuthenticationService(loginDAO());
    }

    @Bean
    public RequestValidator requestValidator() {
        return new RequestValidator();
    }
}