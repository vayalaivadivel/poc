package org.poc.core.controller;

import io.swagger.annotations.*;
import org.poc.core.exception.InvalidRequestBodyException;
import org.poc.core.exception.NoDataFoundException;
import org.poc.core.exception.AuthenticationException;
import org.poc.core.exception.PocFailure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * It is a abstract controller used by both Student-Service and Employee-Service since those those two services
 * designed based on Micro service architecture
 *
 * @author vadivel 12/15/2016
 */
@Api("POC CRUD Controller both student and employee")
@RestController
public abstract class PocAbstractController {

    private static final Logger logger = LoggerFactory.getLogger(PocAbstractController.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoDataFoundException.class)
    public PocFailure handleNoDataError(final NoDataFoundException e) {
        return new PocFailure(PocFailure.Type.NOT_FOUND, e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public PocFailure handleAuthenticationError(final AuthenticationException e) {
        return new PocFailure(PocFailure.Type.UN_AUTHORIZED, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestBodyException.class)
    public PocFailure handleRequestBodyError(final InvalidRequestBodyException e) {
        return new PocFailure(PocFailure.Type.BAD_REQUEST, e.getMessage());
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "Get Information",
        notes = "Fetch information by primary key value"
    )
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Information not found"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal server error")
    })
    @RequestMapping(
        produces = {MediaType.APPLICATION_JSON_VALUE},
        value = "/get/{id}",
        method = RequestMethod.GET)
    public Object getInfo(
        @RequestHeader("username") final String username,
        @RequestHeader("password") final String password,
        @ApiParam(value = "Registration Number", required = true)
        @PathVariable("id") final Integer id) {

        logger.info(String.format("%s /get/%s", RequestMethod.GET, id));
        this.validateUser(username, password);
        return this.doGetInfoById(id);
    }

    @ApiOperation(
        produces = MediaType.APPLICATION_JSON_VALUE,
        response = Object.class,
        responseContainer = "List",
        value = "Retrieve information")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    @RequestMapping(
        value = "/getAll",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public Object getAllInfo(
        @RequestHeader("username") final String username,
        @RequestHeader("password") final String password) {

        logger.info(String.format("%s /getAll", RequestMethod.GET));
        this.validateUser(username, password);

        return this.doGetAllInfo();
    }

    @ApiOperation(
        value = "Delete Information",
        notes = "Delete information by primary key value"
    )
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Information not found"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal server error")
    })
    @RequestMapping(
        value = "/delete/{id}",
        method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteStudent(
        @RequestHeader("username") final String username,
        @RequestHeader("password") final String password,
        @ApiParam(value = "Registration Number", required = true)
        @PathVariable("id") final Integer id) {

        logger.info(String.format("%s /delete/%s", RequestMethod.DELETE, id));
        this.validateUser(username, password);
        this.doDeleteInfo(id);
        final String responseString = String.format(
            "Information for the ID: %s is deleted successfully.",
            id);
        logger.info(responseString);
        return ResponseEntity.status(HttpStatus.OK).body(responseString);
    }

    @ApiOperation(
        value = "Delete All Information",
        notes = "Delete all information"
    )
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_OK, message = "OK"),
        @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Information not found"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal server error")
    })
    @RequestMapping(
        value = "/deleteAll",
        method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteAllStudent(
        @RequestHeader("username") final String username,
        @RequestHeader("password") final String password) {

        logger.info(String.format("%s /deleteAll", RequestMethod.DELETE));
        this.validateUser(username, password);
        final int total = this.doDeleteAllInfo();
        final String responseString = String.format("Total all %s record(s) deleted successfully.", total);
        logger.info(responseString);
        return ResponseEntity.status(HttpStatus.OK).body(responseString);
    }

    @ApiOperation(
        produces = MediaType.ALL_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "Create a Info")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Created (no response content)"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    @RequestMapping(
        value = "/save",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> saveStudent(
        @RequestHeader("username") final String username,
        @RequestHeader("password") final String password,
        @ApiParam(value = "The Information storing", required = true)
        @RequestBody final Object info) {

        logger.info(String.format("%s /save: request=%s", RequestMethod.POST, info));

        this.validateUser(username, password);
        final int primaryKey = this.doPostSaveInfo(info);

        final String responseString = String.format("Details stored successfully with id: %s.", primaryKey);
        logger.info(responseString);

        final HttpHeaders responseHeaders = constructResponseHeaders("save", primaryKey);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .headers(responseHeaders)
            .body(responseString);
    }

    @ApiOperation(
        produces = MediaType.ALL_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        value = "Update Info")
    @ApiResponses({
        @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Updated (no response content)"),
        @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid Request"),
        @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    @RequestMapping(
        value = "/update",
        method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateStudent(
        @RequestHeader("username") final String username,
        @RequestHeader("password") final String password,
        @ApiParam(value = "Information to update", required = true)
        @RequestBody final Object info) {

        logger.info(String.format("%s /update: request=%s", RequestMethod.PUT, info));

        this.validateUser(username, password);
        final Integer primaryKey = doPutUpdateInfo(info);

        logger.info(
            String.format(
                "Details updated successfully with id: %s.",
                primaryKey));

        final HttpHeaders responseHeaders = constructResponseHeaders("update", primaryKey);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(responseHeaders).build();
    }

    protected abstract void validateUser(final String userName, final String password);

    protected abstract Object doGetAllInfo();

    protected abstract Object doGetInfoById(final int id);

    protected abstract int doPostSaveInfo(final Object info);

    protected abstract Integer doPutUpdateInfo(final Object info);

    protected abstract void doDeleteInfo(final int id);

    protected abstract int doDeleteAllInfo();

    protected abstract HttpHeaders constructResponseHeaders(final String operationType, final Integer primaryKey);
}