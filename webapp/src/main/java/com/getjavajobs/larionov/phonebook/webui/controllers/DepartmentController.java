package com.getjavajobs.larionov.phonebook.webui.controllers;

import com.getjavajobs.larionov.phonebook.exceptions.ServiceException;
import com.getjavajobs.larionov.phonebook.model.Department;
import com.getjavajobs.larionov.phonebook.service.DepartmentService;
import com.getjavajobs.larionov.phonebook.service.EmployeeService;
import com.getjavajobs.larionov.phonebook.webui.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Vlad on 16.10.2014.
 */
@Controller
public class DepartmentController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    UserService userService = UserService.getInstance();
    @RequestMapping("/departments")
    public String showAll(HttpServletRequest request) throws ServiceException {
        request.setAttribute("departments", departmentService.getAll());
        return "/WEB-INF/jsp/Departments.jsp";

    }
    @RequestMapping("/departments/edit")
    public String editForm(HttpServletRequest request) throws ServiceException {
        request.setAttribute("employees", employeeService.getAll());
        String departmentId = request.getParameter("departmentId");

        if (departmentId != null) {
            request.setAttribute("department", departmentService.get(Integer.valueOf(departmentId)));
        }
        return "/WEB-INF/jsp/DepartmentEditForm.jsp";
    }
    @RequestMapping("/departments/do_edit")
    public String edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {
        String action = request.getParameter("action");
        if ("add".equals(action) || "update".equals(action)) {
            Department department = new Department();
            department.setName(request.getParameter("name"));
            String managerId=request.getParameter("managerId");

            department.setManager(employeeService.get(Integer.valueOf(managerId)));
            if("add".equals(action)) {
                departmentService.add(department);
            } else {
                department.setId(Integer.valueOf(request.getParameter("departmentId")));
                departmentService.update(department);
            }
        }else if ("delete".equals(action)) {
            departmentService.delete(Integer.valueOf(request.getParameter("departmentId")));
        }
        return "redirect:/departments";

    }
}
