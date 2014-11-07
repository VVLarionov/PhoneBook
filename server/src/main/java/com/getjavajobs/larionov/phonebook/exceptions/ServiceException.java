package com.getjavajobs.larionov.phonebook.exceptions;

/**
 * Created by Vlad on 22.09.2014.
 */
public class ServiceException extends Exception{
    public ServiceException(String message, Exception e){
        super(message,e);
    }
    public ServiceException(Exception e){
        super(e);
    }
    public ServiceException(String s) {
        super(s);
    }
}
