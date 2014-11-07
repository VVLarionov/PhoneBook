package com.getjavajobs.larionov.phonebook.service.validators;

import com.getjavajobs.larionov.phonebook.model.Employee;
import org.springframework.stereotype.Component;

/**
 * Created by Vlad on 22.09.2014.
 */
@Component
public class EmployeeValidator {
    public boolean validate(Employee employee){
        return true;
    }
}
