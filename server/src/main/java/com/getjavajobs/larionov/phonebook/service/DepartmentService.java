package com.getjavajobs.larionov.phonebook.service;

import com.getjavajobs.larionov.phonebook.dao.Dao;
import com.getjavajobs.larionov.phonebook.exceptions.DaoException;
import com.getjavajobs.larionov.phonebook.exceptions.ServiceException;
import com.getjavajobs.larionov.phonebook.model.Department;

import com.getjavajobs.larionov.phonebook.model.Employee;
import com.getjavajobs.larionov.phonebook.service.validators.DepartmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by Vlad on 08.10.2014.
 */
@Service
public class DepartmentService {
    @Autowired
    private Dao<Employee> employeeDao;
    @Autowired
    private Dao<Department> departmentDao;
    @Autowired
    private DepartmentValidator departmentValidator;

    @Transactional
    public void add(Department department) throws ServiceException {
        if (departmentValidator.validate(department)){
            try {
                departmentDao.add(department);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }else {
            throw new ServiceException("Validate fail");
        }
    }
    @Transactional
    public void delete(int id) throws ServiceException {
        try{
            departmentDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    @Transactional
    public Department get(int id) throws ServiceException{
        try {
            Department department = departmentDao.get(id);
            Employee manager = employeeDao.get(department.getManager().getId());
            manager.setDepartment(department);
            department.setManager(manager);
            return department;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    @Transactional
    public void update(Department department) throws ServiceException{
        if (departmentValidator.validate(department)){
            try {
                departmentDao.update(department);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }else {
            throw new ServiceException("Validate fail");
        }
    }
    @Transactional
    public Collection<Department> getAll() throws ServiceException{
        try {
            List<Department> departments = departmentDao.getAll();
            for (Department department:departments) {
                Employee manager = employeeDao.get(department.getManager().getId());
                manager.setDepartment(department);
                department.setManager(manager);
            }
            return departments;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
