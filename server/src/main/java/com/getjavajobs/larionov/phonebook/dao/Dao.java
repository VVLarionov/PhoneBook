package com.getjavajobs.larionov.phonebook.dao;

import com.getjavajobs.larionov.phonebook.exceptions.DaoException;

import java.util.List;

/**
 * Created by Vlad on 07.08.2014.
 */
public interface Dao<T>{

    void add(T obj) throws DaoException;

    void delete(int id) throws DaoException;

    T get(int id) throws DaoException;

    void update(T obj) throws DaoException;

    List<T> getAll() throws DaoException;

}
