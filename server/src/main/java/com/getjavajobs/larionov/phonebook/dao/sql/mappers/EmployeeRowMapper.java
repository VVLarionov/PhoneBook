package com.getjavajobs.larionov.phonebook.dao.sql.mappers;

import com.getjavajobs.larionov.phonebook.model.Department;
import com.getjavajobs.larionov.phonebook.model.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vlad on 13.10.2014.
 */
@Component
public class EmployeeRowMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setProject(resultSet.getString("project"));
        employee.setEmail(resultSet.getString("email"));

        int departmentId = resultSet.getInt("department_id");
        if(departmentId!=0) {
            employee.setDepartment(new Department());
            employee.getDepartment().setId(departmentId);
        }
        return employee;
    }
}
