package com.getjavajobs.larionov.phonebook.webui.filters;

import com.getjavajobs.larionov.phonebook.webui.model.User;
import com.getjavajobs.larionov.phonebook.webui.services.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
/**
 * Created by Vlad on 02.11.2014.
 */
public class LoginVerifierFilter implements Filter{
    private FilterConfig config = null;
    private boolean active = false;
    private UserService userService = UserService.getInstance();

    @Override
    public void init (FilterConfig config) throws ServletException
    {
        this.config = config;
        String act = config.getInitParameter("active");
        if (act != null)
            active = (act.toUpperCase().equals("TRUE"));
    }

    @Override
    public void doFilter (ServletRequest request, ServletResponse response,
                          FilterChain chain) throws IOException,
            ServletException
    {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        if (active)
        {

            if(!isLogged(req)) {
                String auth = req.getParameter("authorization");
                if (auth != null && "true".equals(auth)) {
                    login(req, resp);

                } else {
                    if(!req.getRequestURL().toString().endsWith("/login")){
                        req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/LoginPage.jsp").forward(req, resp);
                    }
                }
            } else {
                if(req.getRequestURL().toString().endsWith("/logout")){
                    logout(req,resp);

                    //resp.sendRedirect("/login");
                    req.getServletContext().getRequestDispatcher("/WEB-INF/jsp/LoginPage.jsp").forward(req, resp);
                }
            }
        }
        chain.doFilter(req,resp);
    }

    @Override
    public void destroy()
    {
        config = null;
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));

        if(userService.validate(user)){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            if("true".equals(request.getParameter("remember"))){
                Cookie cookie = new Cookie("user", user.getLogin()+" "+user.getPassword());
                cookie.setMaxAge(10000);
                response.addCookie(cookie);
            }
        } else {
            response.sendRedirect("Login");
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        Cookie jSession = new Cookie("JSESSIONID", null);
        jSession.setMaxAge(0);
        response.addCookie(cookie);
        response.addCookie(jSession);
    }

    private boolean isLogged(HttpServletRequest request) {
        if(request.getSession().getAttribute("user")==null) {
            if(request.getCookies()!=null){
                for(Cookie cookie: request.getCookies()) {
                    if("user".equals(cookie.getName())){
                        User user = new User();
                        String[] data = cookie.getValue().split(" ");
                        if (data.length==2) {
                            user.setLogin(data[0]);
                            user.setPassword(data[1]);
                            if(userService.validate(user)){
                                request.getSession().setAttribute("user", user);
                                return true;
                            }
                        }
                        break;
                    }
                }
            }
            return false;
        } else {
            User user = (User)request.getSession().getAttribute("user");
            return userService.validate(user);
        }
    }
}
