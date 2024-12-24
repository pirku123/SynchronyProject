package com.syf.project.controller;

import com.syf.project.dto.EmployeeDto;
import com.syf.project.service.EmployeeService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * This method is used to search records in database
     * @param employee
     * @return
     */
    @PostMapping("/search")
    public List<EmployeeDto> selectEmployee(@Validated @RequestBody List<EmployeeDto> employee) {
        try {
            logger.info("START_OF_PROCESSING_SELECT_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            Instant startTime = Instant.now();
            List<EmployeeDto> response = employeeService.selectEmployee(employee);
            Instant endTime = Instant.now();
            logger.info("END_OF_PROCESSING_SELECT_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName(), Duration.between(startTime, endTime).toMillis());
            return response;
        }catch(Exception e){
                logger.error("INTERMEDIATE_PROCESSING_ERROR_SELECT_METHOD", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
                throw e;
        }
    }

    /**
     * This method is used to insert records into database
     * @param employee
     * @return
     */
    @PostMapping("/insert")
    public boolean insertEmployee(@Validated @RequestBody List<EmployeeDto> employee) {
        try {
            logger.info("START_OF_PROCESSING_INSERT_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            Instant startTime = Instant.now();
            Boolean response = employeeService.insertEmployees(employee);
            Instant endTime = Instant.now();
            logger.info("END_OF_PROCESSING_INSERT_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName(), Duration.between(startTime, endTime).toMillis());
            return response;
        }catch(Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR_INSERT_METHOD", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    /**
     * This method is used to update record in databse
     * @param employee
     * @return
     */
    @PostMapping("/update")
    public boolean updateEmployee(@Validated @RequestBody List<EmployeeDto> employee) {
        try {
            logger.info("START_OF_PROCESSING_UPDATE_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            Instant startTime = Instant.now();
            Boolean response = employeeService.updateEmployee(employee);
            Instant endTime = Instant.now();
            logger.info("END_OF_PROCESSING_UPDATE_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName(), Duration.between(startTime, endTime).toMillis());
            return response;
        }catch(Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR_UPDATE_METHOD", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    /**
     * This method is used to delete record from database
     * @param emp_id
     * @return
     */
    @GetMapping("/delete/{emp_id}")
    public boolean deleteEmployee(@PathVariable String emp_id) {
        try {
            logger.info("START_OF_PROCESSING_DELETE_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            Instant startTime = Instant.now();
            Boolean response = employeeService.deleteEmployee(emp_id);
            Instant endTime = Instant.now();
            logger.info("END_OF_PROCESSING_DELETE_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName(), Duration.between(startTime, endTime).toMillis());
            return response;
        }catch(Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR_DELETE_METHOD", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }
}
