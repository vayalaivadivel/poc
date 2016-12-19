package org.poc.core.service;

import org.apache.commons.lang.StringUtils;
import org.poc.core.dao.LoginDAO;
import org.poc.core.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;;

/**
 * It is a authentication service used both Student and Employee service.
 * This method called each on every request from the client.
 *
 * I tried to implement in Filters. But it is not feasible.
 * And also tried to implement using AOP. But it is not supporting Abstract class.
 *
 *
 * @author vadivel 12/15/2016
 */
@Component
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final LoginDAO loginDAO;

    @Autowired
    public AuthenticationService(final LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    public void authServiceAccess(final String userName, final String password) {
        logger.info("Authenticating user....");
        boolean isValid = false;
        if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)) {
            isValid = loginDAO.isValidUser(userName, password);
        }
        if (!isValid) {
            logger.info("Invalid User");
            throw new AuthenticationException("Invalid User");
        }
        logger.info("Valid User");
    }

}
