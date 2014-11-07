package com.getjavajobs.larionov.phonebook.exceptions;

import java.sql.SQLException;

/**
 * Created by Vlad on 02.09.2014.
 */
public class DaoException extends Exception{
    public DaoException(String message, Exception e) {
        super(message,e);
    }
    public DaoException(Exception e) {
        super(e);
    }
}
