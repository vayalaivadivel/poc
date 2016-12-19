package org.poc.employee;
import org.poc.employee.config.EmployeeBeanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Main spring boot class for Employee service to start the service
 * based on Micro service architecture
 *
 * @author vadivel 12/14/2016
 */
@SpringBootApplication
@Import({EmployeeBeanConfig.class})
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}