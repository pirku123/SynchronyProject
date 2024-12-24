package com.syf.project.repository;

import com.syf.project.entity.Employee;

import java.util.List;

public interface EmployeeCustomRepository {

    Employee findByIdAndDept(String id, String dept);
}
