package org.poc.core.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Json based validation error model
 *
 * @author vadivel 12/14/2016
 */
@ApiModel
public class PocFailure {
    public enum Type {BAD_REQUEST, NOT_FOUND, UN_AUTHORIZED}

    private final Type type;
    private final String message;

    public PocFailure(
        @JsonProperty("type") final Type type,
        @JsonProperty("message") final String message) {
        this.type = type;
        this.message = message;
    }

    @ApiModelProperty(required = true)
    public Type getType() {
        return type;
    }

    @ApiModelProperty(required = true)
    public String getMessage() {
        return message;
    }
}