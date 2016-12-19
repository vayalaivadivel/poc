package org.poc.employee.service;

import org.poc.core.dao.EmployeeDAO;
import org.poc.core.domain.EmployeeInfo;
import org.poc.core.exception.NoDataFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * It is as service/business class contains all business logic and
 * also interface between controller and DAO
 *
 * @author vadivel 12/19/2016
 */
@Component
public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeService(final EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public EmployeeInfo getEmployee(final Integer empNo) {
        final EmployeeInfo employeeInfo;
        try {
            employeeInfo = employeeDAO.findByPK(empNo);
        } catch (final EmptyResultDataAccessException e) {
            throw new NoDataFoundException("Employee details not found for the regNo: " + empNo);
        }
        return employeeInfo;
    }

    public List<EmployeeInfo> findAll() {
        final List<EmployeeInfo> employeeInfoList = employeeDAO.findAll();
        if (employeeInfoList == null || employeeInfoList.isEmpty()) {
            throw new NoDataFoundException("No data found");
        }
        return employeeInfoList;
    }

    public int save(final EmployeeInfo employeeInfo) {
        return employeeDAO.create(employeeInfo);
    }

    public void delete(final Integer regNo) {
        final int result = employeeDAO.delete(regNo);

        if (result == 0) {
            throw new NoDataFoundException(String.format("No data found with id: %s to delete.", regNo));
        }
    }

    public int deleteAll() {
        final int result = employeeDAO.deleteAll();
        if (result == 0) {
            throw new NoDataFoundException("No data found to delete.");
        }

        return result;
    }

    public void update(final EmployeeInfo employeeInfo) {
        final int result = employeeDAO.update(employeeInfo);
        if (result == 0) {
            throw new NoDataFoundException(
                String.format(
                    "No data found for the id: %s to update.",
                    employeeInfo.getEmpNo()));
        }
    }
}
