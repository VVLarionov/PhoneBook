package com.getjavajobs.larionov.phonebook.dao.sql;

import com.getjavajobs.larionov.phonebook.exceptions.DaoException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Vlad on 07.10.2014.
 */
public class ConnectionHolder {

    private static ConnectionHolder instance;

    public static ConnectionHolder getInstance() {
        if (instance == null) {
            instance = new ConnectionHolder();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/phonebook");
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Освободить соединение.
    public void releaseConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
