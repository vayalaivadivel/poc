package org.poc.core.util;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

/**
 * It contains common constants and methods
 *
 *
 * @author vadivel 12/16/2016
 */
public final class PocCommonUtil {
    public static final String STR_REG_NO = "regNo";
    public static final String STR_NAME = "name";
    public static final String STR_AGE = "age";
    public static final String STR_TOTAL_MARK = "totalMark";
    public static final String STR_STUDENT_SERVICE = "Student_Service";
    public static final String STR_EMPLOYEE_SERVICE = "Employee_Service";
    public static final String STR_EMPLOYEE_SERVICE_CONTEXT_PATH = "employee";
    public static final String STR_STUDENT_SERVICE_CONTEXT_PATH = "student";
    private static final String STR_BACK_SLASH = "/";
    private static final String STR_PK = "primaryKey";
    public static final String STR_EMP_NO = "empNo";



    public static final String STR_SALARY = "salary";

    public static HttpHeaders constructResponseHeaders(
        final String serviceContextPath,
        final String operationType,final Integer primaryKey){

        final StringBuilder uri=new StringBuilder(STR_BACK_SLASH);
        uri.append(serviceContextPath);
        uri.append(STR_BACK_SLASH);
        uri.append(operationType);
        uri.append(STR_BACK_SLASH);
        uri.append("{primaryKey}");

        final UriTemplate uriTemplate = new UriTemplate(uri.toString());
        final URI responseUri = uriTemplate.expand(ImmutableMap.of(STR_PK, primaryKey));
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(responseUri);

        return responseHeaders;
    }
}
