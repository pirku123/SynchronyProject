package com.syf.project.service;

import com.syf.project.entity.Employee;
import com.syf.project.repository.EmployeeRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeEntityService {

    @Autowired
    EmployeeRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(EmployeeEntityService.class);

    public Employee findByIdAndDept(String id,String dept) {
        try {
            logger.info("START_OF_PROCESSING_FIND_METHOD", Thread.currentThread().getStackTrace()[1].getMethodName());
            return repository.findByIdAndDept(id, dept);
        } catch (Exception e) {
            logger.error("INTERMEDIATE_PROCESSING_ERROR_FIND_METHOD", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    @Transactional
    public void updateEmployee(List<Employee> employee) {
        try {
            logger.info("START_OF_PROCESSING_UPDATE_EMPLOYEE", Thread.currentThread().getStackTrace()[1].getMethodName());
            repository.saveAll(employee);
        }catch(Exception e){
            logger.error("Intermediate_PROCESSING_ERROR_UPDATE_EMPLOYEE", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    @Transactional
    public void deleteEmployee(String id) {
        try {
            logger.info("START_OF_PROCESSING_DELETE_EMPLOYEE", Thread.currentThread().getStackTrace()[1].getMethodName());
            repository.deleteById(id);
        }catch(Exception e){
            logger.error("Intermediate_PROCESSING_ERROR_DELETE_EMPLOYEE", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }

    @Transactional
    public void insertEmployee(Employee employee) {
        try {
            logger.info("START_OF_PROCESSING_INSERT_EMPLOYEE", Thread.currentThread().getStackTrace()[1].getMethodName());
            entityManager.persist(employee);
        }catch(Exception e){
            logger.error("Intermediate_PROCESSING_ERROR_INSERT_EMPLOYEE", Thread.currentThread().getStackTrace()[1],e.getLocalizedMessage());
            throw e;
        }
    }
}
