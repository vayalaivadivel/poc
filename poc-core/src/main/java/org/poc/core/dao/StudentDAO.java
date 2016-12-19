package org.poc.core.dao;

import org.poc.core.domain.StudentInfo;

import java.util.List;

/**
 * A Data Access Object for CRUD operation on student table.
 *
 * @author vmuniyandi 12/14/2016
 */
public interface StudentDAO {
    /**
     * Creates the student in the database.
     *
     * @param student the {@code StudentInfo} to create.
     * @return the primary key of the newly created record
     */
    int create(StudentInfo student);


    /**
     * Returns the {@link AccountInfo} from the database matching the supplied {@code accountId}.
     *
     * @param regNo the regNo of the {@code Student} to retrieve
     * @return {@code StudentInfo} from the database if there is one with a matching
     * {@code regNo}, otherwise null
     */
    StudentInfo findByPK(Integer regNo);

    /**
     *  Returns the list of student info
     *
     * @return {@code List<StudentInfo>}
     */
    List<StudentInfo> findAll();

    /**
     * Delete the Student information based on the register number and
     * return non-zero if it is deleted otherwise return zero.
     *
     * @param regNo
     * @return {@code int}
     */
    int delete(final Integer regNo);

    /**
     * Delete the Student information based on the register number and
     * return non-zero if it is deleted otherwise return zero.
     *
     * @return {@code int}
     */
    int deleteAll();

    /**
     * Update the student in the database.
     *
     * @param student the {@code StudentInfo} to create.
     *
     * @return {@code int}
     */
    int update(StudentInfo student);

}
