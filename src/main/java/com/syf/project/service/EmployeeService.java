package com.syf.project.service;

import com.syf.project.dto.EmployeeDto;
import com.syf.project.entity.Employee;
import com.syf.project.util.EmployeeUtil;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeService {

    @Autowired
    EmployeeEntityService employeeEntityService;

    @Autowired
    EmployeeCacheService employeeCacheService;

    @Value("${timeout}")
    private long timeout;

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(EmployeeService.class);

    /**
     * This method is used to select records from database and stores them in cache
     * @param employeeList
     * @return
     */
    public List<EmployeeDto> selectEmployee(List<EmployeeDto> employeeList){
        logger.info("START_OF_PROCESSING_SELECT_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
        List<CompletableFuture<Employee>> futures = new ArrayList<>();
        List<Employee> employeeCacheList = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        try{
            employeeList.stream().forEach(employee -> {
                Object emp= employeeCacheService.readFromLocalRedis(employee.getEmp_id());
                if(ObjectUtils.isEmpty(emp)) {
                    CompletableFuture<Employee> futureResp = CompletableFuture.supplyAsync(
                            () -> employeeEntityService.findByIdAndDept(employee.getEmp_id(), employee.getDept()));
                    futures.add(futureResp);
                }else{
                    employeeCacheList.add((Employee) emp);
                }
            });
            futures.stream().forEach(resp->{
                try {
                    Employee emp = resp.get(timeout, TimeUnit.MILLISECONDS);
                    employees.add(emp);
                    employeeCacheService.insetIntoLocalRedis(emp.getEmp_id(),emp);
                } catch (Exception e) {
                    logger.error("TIMEOUT_ERROR", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
                    throw new RuntimeException(e);
                }
            });
            employees.addAll(employeeCacheList);
            logger.info("END_OF_PROCESSING_SELECT_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            return EmployeeUtil.convertEntityToDto(employees) ;
        }catch(Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR_SELECT_METHOD", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    /**
     * This method is used to update records in database and also update cache
     * @param employeeList
     * @return
     */
    public boolean updateEmployee(List<EmployeeDto> employeeList){
        try{
            logger.info("START_OF_PROCESSING_UPDATE_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            employeeEntityService.updateEmployee(EmployeeUtil.convertDtoToEntity(employeeList));
            employeeList.forEach(emp->{
                employeeCacheService.updateIntoLocalRedis(emp.getEmp_id(),emp);
            });
            logger.info("END_OF_PROCESSING_UPDATE_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            return true;
        }catch (Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR_UPDATE_METHOD", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    /**
     * This method is used to delete records from database and also from cache
     * @param emp_id
     * @return
     */
    public boolean deleteEmployee(String emp_id){
        try{
            logger.info("START_OF_PROCESSING_DELETE_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            employeeEntityService.deleteEmployee(emp_id);
            employeeCacheService.deleteFromLocalRedis(emp_id);
            logger.info("END_OF_PROCESSING_DELETE_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            return true;
        }catch(Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR_DELETE_METHOD", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    /**
     * This method is used to insert records in database
     * @param employeeList
     * @return
     */
    public boolean insertEmployees(List<EmployeeDto> employeeList){
        try{
            logger.info("START_OF_PROCESSING_INSERT_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            List<Employee> employees = EmployeeUtil.convertDtoToEntity(employeeList);
            employees.parallelStream().forEach(employee -> {
                employeeEntityService.insertEmployee(employee);
            });
            logger.info("END_OF_PROCESSING_INSERT_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            return true;
        }catch(Exception e){
            logger.error("INTERMEDIATE_PROCESSING_ERROR_INSERT_METHOD", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }
}
