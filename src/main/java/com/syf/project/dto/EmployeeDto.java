package com.syf.project.dto;

import lombok.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class EmployeeDto {

    @NotNull(message = "EmployeeId must not be null")
    private String emp_id ;
    private String firstname;
    private String lastname;
    private int salary ;
    private String gender;
    private String dept;
}
