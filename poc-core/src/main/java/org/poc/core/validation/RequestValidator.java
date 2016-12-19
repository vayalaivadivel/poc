package org.poc.core.validation;

import org.apache.commons.lang.StringUtils;
import org.poc.core.exception.InvalidRequestBodyException;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.poc.core.util.PocCommonUtil.*;

/**
 * It is a validation class to valid only save request
 * by applying minimum validation rules.
 *
 * @author vadivel 12/18/2016
 */
@Component
public class RequestValidator {
    private static final String ERROR_MESSAGE_HEADER = "Below data not found in the request: ";

    private static final String SAMPLE_STUDENT_JSON_REQUEST =
        ".  Sample Request: {\"name\": \"Mani\",  \"age\": 12,  \"totalMark\": 500}";

    private static final String SAMPLE_EMPLOYEE_JSON_REQUEST =
        ".  Sample Request: {\"name\": \"Mani\",  \"age\": 12,  \"salary\": 500}";

    public void validateRequest(
        final Map<String, Object> studentMap,
        final String serviceName) {

        final StringBuilder errorMessage = new StringBuilder();

        final Object name = studentMap.get(STR_NAME);
        final Object age = studentMap.get(STR_AGE);

        if (name == null) {
            errorMessage.append(StringUtils.EMPTY);
            errorMessage.append(STR_NAME);
            errorMessage.append(StringUtils.EMPTY);
        }

        if (age == null) {
            errorMessage.append(StringUtils.EMPTY);
            errorMessage.append(STR_AGE);
            errorMessage.append(StringUtils.EMPTY);
        }

        if (STR_STUDENT_SERVICE.equals(serviceName)) {
            final Object totalMark = studentMap.get(STR_TOTAL_MARK);
            if (totalMark == null) {
                errorMessage.append(StringUtils.EMPTY);
                errorMessage.append(STR_TOTAL_MARK);
                errorMessage.append(StringUtils.EMPTY);
            }

            if (errorMessage.length() != 0) {
                errorMessage.insert(0, ERROR_MESSAGE_HEADER);
                errorMessage.append(SAMPLE_STUDENT_JSON_REQUEST);
            }
        }

        if (STR_EMPLOYEE_SERVICE.equals(serviceName)) {
            final Object salary = studentMap.get(STR_SALARY);
            if (salary == null) {
                errorMessage.append(StringUtils.EMPTY);
                errorMessage.append(STR_SALARY);
                errorMessage.append(StringUtils.EMPTY);
            }
            if (errorMessage.length() != 0) {
                errorMessage.insert(0, ERROR_MESSAGE_HEADER);
                errorMessage.append(SAMPLE_EMPLOYEE_JSON_REQUEST);
            }
        }

        if (errorMessage.length() != 0) {
            throw new InvalidRequestBodyException(errorMessage.toString());
        }

    }
}