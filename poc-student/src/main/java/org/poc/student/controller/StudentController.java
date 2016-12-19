package org.poc.student.controller;

import com.google.common.collect.ImmutableMap;
import org.poc.core.controller.PocAbstractController;
import org.poc.core.domain.StudentInfo;
import org.poc.core.service.AuthenticationService;
import org.poc.core.util.PocCommonUtil;
import org.poc.core.validation.RequestValidator;
import org.poc.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.poc.core.util.PocCommonUtil.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This controller is sub class of PocAbstractController to do all CRUD operations.
 *
 * @author vadivel 12/14/2016
 */
@RestController
public class StudentController extends PocAbstractController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;
    private final AuthenticationService authenticationService;
    private final RequestValidator requestValidator;

    @Autowired
    public StudentController(
        final StudentService studentService,
        final AuthenticationService authenticationService,
        final RequestValidator requestValidator) {

        this.studentService = studentService;
        this.authenticationService = authenticationService;
        this.requestValidator = requestValidator;
    }

    @Override
    protected Object doGetAllInfo() {
        return studentService.findAll();
    }

    @Override
    protected void validateUser(final String userName, final String password) {
        authenticationService.authServiceAccess(userName, password);
    }

    @Override
    public StudentInfo doGetInfoById(final int id) {
        return studentService.getStudent(id);
    }

    @Override
    protected int doPostSaveInfo(final Object info) {

        logger.info("Content of Student Info: " + info);
        final Map<String, Object> studentRequestMap = (LinkedHashMap<String, Object>) info;

        requestValidator.validateRequest(studentRequestMap, STR_STUDENT_SERVICE);

        return studentService.save(
            constructStudentInfo(studentRequestMap));
    }

    @Override
    protected Integer doPutUpdateInfo(final Object info) {
        logger.info("Content of Student Info: " + info);
        final Map<String, Object> studentRequestMap = (LinkedHashMap<String, Object>) info;
        final StudentInfo studentInfo = constructStudentInfo(studentRequestMap);
        studentService.update(studentInfo);
        return studentInfo.getRegNo();
    }

    @Override
    protected void doDeleteInfo(final int id) {
        studentService.delete(id);
    }

    @Override
    protected int doDeleteAllInfo() {
        return studentService.deleteAll();
    }

    private StudentInfo constructStudentInfo(final Map<String, Object> studentMap) {
        final Object regNo = studentMap.get(STR_REG_NO);

        return StudentInfo.builder()
            .withRegNo(regNo != null ? (Integer) regNo : null)
            .withName((String) studentMap.get(STR_NAME))
            .withAge((Integer) studentMap.get(STR_AGE))
            .withTotalMark((Integer) studentMap.get(STR_TOTAL_MARK))
            .build();
    }

    @Override
    public HttpHeaders constructResponseHeaders(final String operationType, final Integer primaryKey) {

        return PocCommonUtil.constructResponseHeaders(STR_STUDENT_SERVICE_CONTEXT_PATH, operationType, primaryKey);

    }
}