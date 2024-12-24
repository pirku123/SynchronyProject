package com.syf.project.repository;

import com.syf.project.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class EmployeeCustomRepositoryImpl implements EmployeeCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Employee findByIdAndDept(String id, String dept) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        query.where(builder.equal(root.get("emp_id"), id)).distinct(true);
        query.where(builder.equal(root.get("dept"), dept));
        query.orderBy(builder.desc(root.get("salary")));

        query.select(root);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(query);
        Employee employees = typedQuery.getSingleResult();

        if(!ObjectUtils.isEmpty(employees)) {
            return employees;
        }
        return null;
    }
}
