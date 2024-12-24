package com.syf.project.entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name= "employees")
public class Employee {

    @Id
    @Basic
    @Column(name= "emp_id ",nullable = false)
    private String emp_id ;

    @Basic
    @Column(name= "first_name",nullable = false)
    private String firstname;

    @Basic
    @Column(name = "last_name",nullable = false)
    private String lastname;

    @Basic
    @Column(name= "salary")
    private int salary ;

    @Basic
    @Column(name = "gender",nullable = false,length = 6)
    private String gender;

    @Basic
    @Column(name = "dept",nullable = false)
    private String dept;
}

