package org.poc.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author vmuniyandi 12/14/2016
 */
@ApiModel(description = "Student information Response")
public class StudentInfo {
    @ApiModelProperty(value = "Student registration number", required = false)
    @Valid
    private final Integer regNo;

    @ApiModelProperty(value = "Student name", required = true)
    @NotNull
    @Valid
    private final String name;

    @ApiModelProperty(value = "Student age", required = true)
    @NotNull
    @Valid
    private final Integer age;

    @ApiModelProperty(value = "Student total mark", required = true)
    @NotNull
    @Valid
    private final Integer totalMark;

    public StudentInfo(
        @JsonProperty("regNo") final Integer regNo,
        @JsonProperty("name") final String name,
        @JsonProperty("age") final Integer age,
        @JsonProperty("totalMark") final Integer totalMark) {

        this.regNo = regNo;
        this.name = name;
        this.age = age;
        this.totalMark = totalMark;
    }

    public Integer getRegNo() {
        return regNo;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getTotalMark() {
        return totalMark;
    }

    @Override
    public boolean equals(final Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        final StudentInfo ownObject = (StudentInfo) otherObject;

        return new EqualsBuilder()
            .append(regNo, ownObject.regNo)
            .append(name, ownObject.name)
            .append(age, ownObject.age)
            .append(totalMark, ownObject.totalMark)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(regNo)
            .append(name)
            .append(age)
            .append(totalMark)
            .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {
        private Integer regNo;
        private String name;
        private Integer age;
        private Integer totalMark;

        private Builder() {
        }

        public Builder withRegNo(final Integer stuRegNo) {
            this.regNo = stuRegNo;
            return this;
        }

        public Builder withName(final String stuName) {
            this.name = stuName;
            return this;
        }

        public Builder withAge(final Integer stuAge) {
            this.age = stuAge;
            return this;
        }

        public Builder withTotalMark(final Integer stuTotalMark) {
            this.totalMark = stuTotalMark;
            return this;
        }

        public StudentInfo build() {
            return new StudentInfo(regNo, name, age, totalMark);
        }

    }

    public static Builder builder() {
        return new Builder();
    }
}