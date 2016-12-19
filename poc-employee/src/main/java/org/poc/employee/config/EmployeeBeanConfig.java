package org.poc.employee.config;

import org.poc.core.dao.*;
import org.poc.core.service.AuthenticationService;
import org.poc.core.validation.RequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Declared some of the Beans here since those are belongs
 * to some other modules. We have to import this class in main method.
 * So those objects will be wired automatically during the boot.
 *
 * @author vadivel 12/19/2016
 */
@Configuration
public class EmployeeBeanConfig {
    @Bean
    public EmployeeDAO studentDAO() {
        return new JdbcEmployeeDAO();
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
