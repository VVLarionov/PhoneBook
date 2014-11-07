package com.getjavajobs.larionov.phonebook.webui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vlad on 16.10.2014.
 */
@Controller
public class ErrorController {
    @RequestMapping("/error")
    public String printError() {
        return "/WEB-INF/jsp/Error.jsp";
    }
}
