package org.poc.student.service;

import org.poc.core.dao.StudentDAO;
import org.poc.core.domain.StudentInfo;
import org.poc.core.exception.NoDataFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * It is as service/business class contains all business logic and
 * also interface between contRoller and DAO
 *
 * @author vadivel 12/14/2016
 */
@Component
public class StudentService {
    private final StudentDAO studentDAO;

    @Autowired
    public StudentService(final StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public StudentInfo getStudent(final Integer regNo) {
        final StudentInfo studentInfo;
        try {
            studentInfo = studentDAO.findByPK(regNo);
        } catch (final EmptyResultDataAccessException e) {
            throw new NoDataFoundException("Student details not found for the regNo: " + regNo);
        }
        return studentInfo;
    }

    public List<StudentInfo> findAll() {
        final List<StudentInfo> studentInfoList = studentDAO.findAll();
        if (studentInfoList == null || studentInfoList.isEmpty()) {
            throw new NoDataFoundException("No data found");
        }
        return studentInfoList;
    }

    public int save(final StudentInfo studentInfo) {
        return studentDAO.create(studentInfo);
    }

    public void delete(final Integer regNo) {
        final int result = studentDAO.delete(regNo);

        if (result == 0) {
            throw new NoDataFoundException(String.format("No data found with id: %s to delete.", regNo));
        }
    }

    public int deleteAll() {
        final int result = studentDAO.deleteAll();
        if (result == 0) {
            throw new NoDataFoundException("No data found to delete.");
        }

        return result;
    }

    public void update(final StudentInfo studentInfo) {
        final int result = studentDAO.update(studentInfo);
        if (result == 0) {
            throw new NoDataFoundException(
                String.format(
                    "No data found for the id: %s to update.",
                    studentInfo.getRegNo()));
        }
    }

}