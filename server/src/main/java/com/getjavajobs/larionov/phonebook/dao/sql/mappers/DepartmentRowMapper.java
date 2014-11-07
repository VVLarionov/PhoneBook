package com.getjavajobs.larionov.phonebook.dao.sql.mappers;

import com.getjavajobs.larionov.phonebook.dao.Dao;
import com.getjavajobs.larionov.phonebook.model.Department;
import com.getjavajobs.larionov.phonebook.model.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vlad on 14.10.2014.
 */
@Component
public class DepartmentRowMapper implements RowMapper<Department>{
    @Override
    public Department mapRow(ResultSet resultSet, int i) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("id"));
        department.setName(resultSet.getString("name"));
        department.setManager(new Employee());
        department.getManager().setId(resultSet.getInt("manager"));
        return department;
    }
}
