package com.syf.project.util;

import com.syf.project.dto.EmployeeDto;
import com.syf.project.entity.Employee;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeUtil {

    public static List<Employee> convertDtoToEntity(List<EmployeeDto> dtoList) {
        List<Employee> employees = new ArrayList<>();
        dtoList.parallelStream().forEach(employee -> {
            Employee employeeEntity = new Employee();
            employeeEntity.setEmp_id(employee.getEmp_id());
            employeeEntity.setFirstname(employee.getFirstname());
            employeeEntity.setLastname(employee.getLastname());
            employeeEntity.setGender(employee.getGender());
            employeeEntity.setSalary(employee.getSalary());
            employeeEntity.setDept(employee.getDept());
            employees.add(employeeEntity);
        });
        return employees;
    }

    public static List<EmployeeDto> convertEntityToDto(List<Employee> employeeEntity) {
        List<EmployeeDto> employees = new ArrayList<>();
        employeeEntity.parallelStream().forEach(employee -> {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmp_id(employee.getEmp_id());
            employeeDto.setFirstname(employee.getFirstname());
            employeeDto.setLastname(employee.getLastname());
            employeeDto.setGender(employee.getGender());
            employeeDto.setSalary(employee.getSalary());
            employeeDto.setDept(employee.getDept());
            employees.add(employeeDto);
        });
        return employees;
    }
}
