package org.poc.student;

import org.poc.core.config.SwaggerConfig;
import org.poc.student.config.StudentBeanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Main spring boot class for Student service to start the service
 * based on Micro service architecture
 *
 * @author vadivel 12/14/2016
 */
@SpringBootApplication
@Import({StudentBeanConfig.class,SwaggerConfig.class})
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}