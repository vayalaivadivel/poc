package org.poc.core.dao;

import org.poc.core.domain.EmployeeInfo;
import org.poc.core.domain.StudentInfo;

import java.util.List;

/**
 * A Data Access Object for CRUD operation on employee table.
 *
 * @author vmuniyandi 12/19/2016
 */
public interface EmployeeDAO {
    /**
     * Creates the employee in the database.
     *
     * @param employee the {@code EmployeeInfo} to create.
     * @return the primary key of the newly created record
     */
    int create(EmployeeInfo employee);

    /**
     * Returns the {@link AccountInfo} from the database matching the supplied {@code empNo}.
     *
     * @param empNo the regNo of the {@code Employee} to retrieve
     * @return {@code EmployeeInfo} from the database if there is one with a matching
     * {@code regNo}, otherwise null
     */
    EmployeeInfo findByPK(Integer empNo);

    /**
     * Returns the list of employee info
     *
     * @return {@code List<EmployeeInfo>}
     */
    List<EmployeeInfo> findAll();

    /**
     * Delete the Employee information based on the employee number and
     * return non-zero if it is deleted otherwise return zero.
     *
     * @param empNo
     * @return {@code int}
     */
    int delete(final Integer empNo);

    /**
     * Delete the Employee information based on the employee number and
     * return non-zero if it is deleted otherwise return zero.
     *
     * @return {@code int}
     */
    int deleteAll();

    /**
     * Update the employee in the database.
     *
     * @param student the {@code EmployeeInfo} to create.
     * @return {@code int}
     */
    int update(EmployeeInfo employeeInfo);
}
