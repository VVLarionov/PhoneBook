package com.getjavajobs.larionov.phonebook.webui.services;

import com.getjavajobs.larionov.phonebook.webui.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Vlad on 09.10.2014.
 */
public class UserService {
    private static UserService instance = new UserService();

    public static UserService getInstance() {
        return instance;
    }

    private UserService() {

    }

    public boolean validate(User user) {
        if("admin".equals(user.getLogin())&&"password".equals(user.getPassword())) {
            return true;
        }
        return false;
    }




}
