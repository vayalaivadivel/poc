package org.poc.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author vmuniyandi 12/14/2016
 */
@ApiModel(description = "Employee information Response")
public class EmployeeInfo {
    @ApiModelProperty(value = "Employee registration number", required = false)
    @Valid
    private final Integer empNo;

    @ApiModelProperty(value = "Employee name", required = true)
    @NotNull
    @Valid
    private final String name;

    @ApiModelProperty(value = "Employee age", required = true)
    @NotNull
    @Valid
    private final Integer age;

    @ApiModelProperty(value = "Employee total mark", required = true)
    @NotNull
    @Valid
    private final Double salary;

    public EmployeeInfo(
        @JsonProperty("empNo") final Integer empNo,
        @JsonProperty("name") final String name,
        @JsonProperty("age") final Integer age,
        @JsonProperty("salary") final Double salary) {

        this.empNo = empNo;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(final Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        final EmployeeInfo ownObject = (EmployeeInfo) otherObject;

        return new EqualsBuilder()
            .append(empNo, ownObject.empNo)
            .append(name, ownObject.name)
            .append(age, ownObject.age)
            .append(salary, ownObject.salary)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(empNo)
            .append(name)
            .append(age)
            .append(salary)
            .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Builder {
        private Integer empNo;
        private String name;
        private Integer age;
        private Double salary;

        private Builder() {
        }

        public Builder withEmpNo(final Integer theEmpNo) {
            this.empNo = theEmpNo;
            return this;
        }

        public Builder withName(final String empName) {
            this.name = empName;
            return this;
        }

        public Builder withAge(final Integer empAge) {
            this.age = empAge;
            return this;
        }

        public Builder withSalary(final Double empSalary) {
            this.salary = empSalary;
            return this;
        }

        public EmployeeInfo build() {
            return new EmployeeInfo(empNo, name, age, salary);
        }

    }

    public static Builder builder() {
        return new Builder();
    }
}