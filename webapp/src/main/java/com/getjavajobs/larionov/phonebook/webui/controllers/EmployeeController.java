package com.getjavajobs.larionov.phonebook.webui.controllers;

import com.getjavajobs.larionov.phonebook.exceptions.ServiceException;
import com.getjavajobs.larionov.phonebook.model.Employee;
import com.getjavajobs.larionov.phonebook.model.Phone;
import com.getjavajobs.larionov.phonebook.service.DepartmentService;
import com.getjavajobs.larionov.phonebook.service.EmployeeService;
import com.getjavajobs.larionov.phonebook.webui.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Vlad on 14.10.2014.
 */
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;

    UserService userService = UserService.getInstance();

    @RequestMapping("/employees/add_phone")
    public String addPhone() {
        return "/WEB-INF/jsp/AddPhonePage.jsp";
    }

    @RequestMapping("/employees")
    public String showAll(HttpServletRequest request) throws ServiceException {
        request.setAttribute("employees", employeeService.getAll());
        return "/WEB-INF/jsp/Employees.jsp";
    }
    @RequestMapping("/employees/edit")
    public String goToEditForm(HttpServletRequest request) throws ServiceException {
        request.setAttribute("departments", departmentService.getAll());
        String employeeId = request.getParameter("employeeId");

        if (employeeId != null) {
            request.setAttribute("employee", employeeService.get(Integer.valueOf(employeeId)));
        }
        return "/WEB-INF/jsp/EmployeeEditForm.jsp";
    }
    @RequestMapping("/employees/do_edit")
    public String edit(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException, ServletException {
        String action = request.getParameter("action");
        if ("add".equals(action) || "update".equals(action)) {
            Employee employee = new Employee();
            employee.setFirstName(request.getParameter("firstName"));
            employee.setLastName(request.getParameter("lastName"));
            employee.setProject(request.getParameter("project"));
            employee.setEmail(request.getParameter("email"));
            String departmentId=request.getParameter("departmentId");
            if(departmentId!=null && !"".equals(departmentId)) {
                employee.setDepartment(departmentService.get(Integer.valueOf(departmentId)));
            }

            if("add".equals(action)) {
                employeeService.add(employee);
                employee.setPhoneList(new ArrayList<Phone>());

            } else {
                employee.setId(Integer.valueOf(request.getParameter("employeeId")));
                employee.setPhoneList(employeeService.get(employee.getId()).getPhoneList());
                employeeService.update(employee);
            }
        }else if ("delete".equals(action)) {
            employeeService.delete(Integer.valueOf(request.getParameter("employeeId")));
        }else if ("loadFromFile".equals(action)) {
            employeeService.loadFromInputStream(request.getPart("file").getInputStream());
        }else if("addPhone".equals(action)) {
            Employee employee = employeeService.get(Integer.valueOf(request.getParameter("employeeId")));
            Phone phone = new Phone();
            phone.setCountryCode(request.getParameter("countryCode"));
            phone.setCityCode(request.getParameter("cityCode"));
            phone.setPhoneNumber(request.getParameter("phoneNumber"));
            phone.setMobile(Boolean.valueOf(request.getParameter("isMobile")));
            employee.getPhoneList().add(phone);
            employeeService.update(employee);
        }else if("deletePhone".equals(action)) {
            Employee employee = employeeService.get(Integer.valueOf(request.getParameter("employeeId")));
            Phone phone = null;
            for (Phone currentPhone: employee.getPhoneList()) {
                if (currentPhone.getId()==Integer.valueOf(request.getParameter("phoneId"))){
                    phone=currentPhone;

                    break;
                }
            }
            employee.getPhoneList().remove(phone);
            employeeService.update(employee);
        }
        return "redirect:/employees";
    }

    @RequestMapping("/employees/load")
    public String load(HttpServletRequest request) throws IOException, ServletException, ServiceException, FileUploadException {
        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
        for(FileItem fileItem:upload.parseRequest(request)) {
            if (!"ignored".equals(fileItem.getFieldName())){
                employeeService.loadFromInputStream(fileItem.getInputStream());
            }
        }
        return "redirect:/employees";
    }
}
