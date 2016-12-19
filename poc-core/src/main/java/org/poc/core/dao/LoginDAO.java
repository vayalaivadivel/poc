package org.poc.core.dao;

/**
 * A Data Access Object to validate the user
 *
 * @author vadivel 12/15/2016
 */
public interface LoginDAO {
    boolean isValidUser(String userName, String password);
}
