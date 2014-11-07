package com.getjavajobs.larionov.phonebook.service;

import com.getjavajobs.larionov.phonebook.dao.Dao;
import com.getjavajobs.larionov.phonebook.exceptions.DaoException;
import com.getjavajobs.larionov.phonebook.exceptions.ServiceException;
import com.getjavajobs.larionov.phonebook.model.Department;
import com.getjavajobs.larionov.phonebook.model.Employee;
import com.getjavajobs.larionov.phonebook.model.Phone;
import com.getjavajobs.larionov.phonebook.service.validators.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 22.09.2014.
 */
@Service
public class EmployeeService {
    @Autowired
    private Dao<Employee> employeeDao;
    @Autowired
    private Dao<Department> departmentDao;
    @Autowired
    private EmployeeValidator employeeValidator;

    @Transactional
    public void add(Employee employee) throws ServiceException{
        if (employeeValidator.validate(employee)){
            try {
                employeeDao.add(employee);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }else {
            throw new ServiceException("Validate fail");
        }
    }
    @Transactional
    public void delete(int id) throws ServiceException{
        try {
            employeeDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    @Transactional
    public Employee get(int id) throws ServiceException{
        try {
            Employee employee = employeeDao.get(id);
            /*if (employee!=null && employee.getDepartment()!=null){
                Department department = departmentDao.get(employee.getDepartment().getId());
                Employee manager = employeeDao.get(department.getManager().getId());
                manager.setDepartment(department);
                department.setManager(manager);
                employee.setDepartment(department);
            }*/
            return employee;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    @Transactional
    public void update(Employee employee) throws ServiceException {
        if (employeeValidator.validate(employee)){
            try {
                employee.setPhoneList(employee.getPhoneList().subList(0,employee.getPhoneList().size()));
                employeeDao.update(employee);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }else {
            throw new ServiceException("Validate fail");
        }
    }
    @Transactional
    public List<Employee> getAll() throws ServiceException{
        try {
            List<Employee> employees = employeeDao.getAll();
            for (Employee employee:employees){
                if (employee!=null && employee.getDepartment()!=null){
                    Department department = departmentDao.get(employee.getDepartment().getId());
                    Employee manager = employeeDao.get(department.getManager().getId());
                    manager.setDepartment(department);
                    department.setManager(manager);
                    employee.setDepartment(department);
                }

            }
            return employees;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public void loadFromInputStream(InputStream inputStream) throws ServiceException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line=reader.readLine())!=null) {
                String[] args=line.split(",");
                Employee employee = new Employee();
                employee.setFirstName(args[0]);
                employee.setLastName(args[1]);
                employee.setProject(args[2]);
                if (!"".equals(args[3])) {
                    employee.setDepartment(departmentDao.get(Integer.valueOf(args[3])));
                }
                employee.setEmail(args[4]);
                employee.setPhoneList(new ArrayList<Phone>());
                for (int i=0; i<Integer.valueOf(args[5]);i++){
                    Phone phone = new Phone();
                    phone.setCountryCode(args[6+i]);
                    phone.setCityCode(args[7+i]);
                    phone.setPhoneNumber(args[8+i]);
                    phone.setMobile(Boolean.valueOf(args[9+i]));
                    employee.getPhoneList().add(phone);
                }
                add(employee);
            }
        } catch (IOException|DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional
    public List<Employee> search(String name)throws ServiceException{
        List<Employee> employees = getAll();
        List<Employee> desired = new ArrayList<>();
        for(Employee employee : employees){
            String firstName = employee.getFirstName();
            String lastName = employee.getLastName();
            if(firstName.contains(name)||lastName.contains(name)){
                desired.add(employee);
            }
        }
        return desired;
    }

}
