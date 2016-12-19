package org.poc.employee.controller;

import org.poc.core.controller.PocAbstractController;
import org.poc.core.domain.EmployeeInfo;
import org.poc.core.service.AuthenticationService;
import org.poc.core.util.PocCommonUtil;
import org.poc.core.validation.RequestValidator;
import org.poc.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.poc.core.util.PocCommonUtil.*;

/**
 * This controller is sub class of PocAbstractController to do all CRUD operations.
 *
 * @author vadivel 12/19/2016
 */
@RestController
public class EmployeeController extends PocAbstractController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;
    private final AuthenticationService authenticationService;
    private final RequestValidator requestValidator;

    @Autowired
    public EmployeeController(
        final EmployeeService employeeService,
        final AuthenticationService authenticationService,
        final RequestValidator requestValidator) {

        this.employeeService = employeeService;
        this.authenticationService = authenticationService;
        this.requestValidator = requestValidator;
    }

    @Override
    protected Object doGetAllInfo() {
        return employeeService.findAll();
    }

    @Override
    protected void validateUser(final String userName, final String password) {
        authenticationService.authServiceAccess(userName, password);
    }

    @Override
    public EmployeeInfo doGetInfoById(final int id) {
        return employeeService.getEmployee(id);
    }

    @Override
    protected int doPostSaveInfo(final Object info) {

        logger.info("Content of Employee Info: " + info);
        final Map<String, Object> employeeRequestMap = (LinkedHashMap<String, Object>) info;
        requestValidator.validateRequest(employeeRequestMap, STR_EMPLOYEE_SERVICE);

        return employeeService.save(
            constructEmployeeInfo(employeeRequestMap));
    }

    @Override
    protected Integer doPutUpdateInfo(final Object info) {
        logger.info("Content of Student Info: " + info);
        final Map<String, Object> employeeRequestMap = (LinkedHashMap<String, Object>) info;
        final EmployeeInfo employeeInfo = constructEmployeeInfo(employeeRequestMap);
        employeeService.update(employeeInfo);
        return employeeInfo.getEmpNo();
    }

    @Override
    protected void doDeleteInfo(final int id) {
        employeeService.delete(id);
    }

    @Override
    protected int doDeleteAllInfo() {
        return employeeService.deleteAll();
    }

    private EmployeeInfo constructEmployeeInfo(final Map<String, Object> studentMap) {
        final Object empNo = studentMap.get(STR_EMP_NO);

        return EmployeeInfo.builder()
            .withEmpNo(empNo != null ? (Integer) empNo : null)
            .withName((String) studentMap.get(STR_NAME))
            .withAge((Integer) studentMap.get(STR_AGE))
            .withSalary(ifIntThenConvertIntToDouble(studentMap.get(STR_SALARY)))
            .build();
    }

    @Override
    public HttpHeaders constructResponseHeaders(final String operationType, final Integer primaryKey) {

        return PocCommonUtil.constructResponseHeaders(STR_EMPLOYEE_SERVICE_CONTEXT_PATH, operationType, primaryKey);

    }

    private Double ifIntThenConvertIntToDouble(final Object salary) {
        Double salaryValue;

        try {
            salaryValue = (Double) salary;
        } catch (final ClassCastException e) {
            final Integer value = (Integer) salary;
            salaryValue = value.doubleValue();
        }

        return salaryValue;
    }
}
