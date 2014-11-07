package com.getjavajobs.larionov.phonebook.service.validators;

import com.getjavajobs.larionov.phonebook.model.Department;
import org.springframework.stereotype.Component;

/**
 * Created by Vlad on 22.09.2014.
 */
@Component
public class DepartmentValidator {
    public boolean validate(Department department){
        return true;
    }
}
