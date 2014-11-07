package com.getjavajobs.larionov.phonebook.dao.sql;

import com.getjavajobs.larionov.phonebook.dao.Dao;
import com.getjavajobs.larionov.phonebook.dao.sql.mappers.DepartmentRowMapper;
import com.getjavajobs.larionov.phonebook.exceptions.DaoException;
import com.getjavajobs.larionov.phonebook.model.Department;
import com.getjavajobs.larionov.phonebook.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 08.10.2014.
 */
@Repository
public class DepartmentDao implements Dao<Department>{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Department department) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        try{
            int id = (Integer)session.save(department);
            department.setId(id);
        }catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.delete(get(id));
        }catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Department get(int id) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        try{
            return (Department) session.get(Department.class, id);
        }catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Department department) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.update(department);
        }catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Department> getAll() throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        try{
            return (List<Department>)session.createCriteria(Department.class).list();
        }catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /*
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DepartmentRowMapper departmentRowMapper;


    @Transactional
    public void add(Department department) throws DaoException {
        String request = "INSERT INTO departments(name, manager) VALUES(?,?);";
        Object[] params = new Object[]{
                department.getName(),
                department.getManager().getId()
        };
        jdbcTemplate.update(request, params);
    }

    @Override
    public void delete(int id) throws DaoException {
        String request = "DELETE FROM departments WHERE id=?;";
        jdbcTemplate.update(request, new Object[]{id});
    }

    @Override
    public Department get(int id) throws DaoException {
        String request = "SELECT * FROM departments WHERE id=?;";
        return jdbcTemplate.queryForObject(request, new Object[]{id}, departmentRowMapper);
    }

    @Override
    public void update(Department department) throws DaoException {
        String request = "UPDATE departments SET name=?, manager=? WHERE id=?;";
        Object[] params = new Object[]{
                department.getName(),
                department.getManager().getId(),
                department.getId()
        };
        jdbcTemplate.update(request, params);
    }

    @Override
    public List<Department> getAll() throws DaoException {
        String request = "SELECT id, name, manager FROM departments;";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(request);
        List<Department> departments = new ArrayList<>();
        for (Map<String, Object> row: rows) {
            Department department = new Department();
            department.setId((int)row.get("id"));
            department.setName((String) row.get("name"));
            department.setManager(new Employee());
            department.getManager().setId((int)row.get("manager"));
            departments.add(department);
        }
        return departments;
    }


    /*
    ConnectionHolder connectionHolder = ConnectionHolder.getInstance();


    @Override
    public void add(Department department) throws DaoException {
        boolean success = true;
        String request = "INSERT INTO departments(name, manager) VALUES(?,?);";
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1,department.getName());
            statement.setInt(2, department.getManager().getId());
            statement.execute();
            department.setId(getLastId(connection));
        } catch (SQLException e) {
            success = false;
            throw new DaoException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }finally {
                connectionHolder.releaseConnection(connection);
            }
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        boolean success = true;
        String request = "DELETE FROM departments WHERE id=?;";
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            success = false;
            throw new DaoException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }finally {
                connectionHolder.releaseConnection(connection);
            }
        }
    }

    @Override
    public Department get(int id) throws DaoException{
        boolean success = true;
        String request = "SELECT name, manager FROM departments WHERE id=?;";
        Connection connection = connectionHolder.getConnection();
        Department department = null;
        try(PreparedStatement statement = connection.prepareStatement(request)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                department = new Department();
                Employee manager = new Employee();
                manager.setId(resultSet.getInt(2));
                department.setId(id);
                department.setName(resultSet.getString(1));
                department.setManager(manager);
            }
        } catch (SQLException e) {
            success = false;
            throw new DaoException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }finally {
                connectionHolder.releaseConnection(connection);
            }
        }
        return department;
    }

    @Override
    public void update(Department department) throws DaoException {
        boolean success = true;
        String request = "UPDATE departments SET name=?, manager=? WHERE id=?;";
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1,department.getName());
            statement.setInt(2, department.getManager().getId());
            statement.setInt(3, department.getId());
            statement.execute();
        } catch (SQLException e) {
            success = false;
            throw new DaoException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }finally {
                connectionHolder.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Department> getAll() throws DaoException {
        boolean success = true;
        String request = "SELECT id, name, manager FROM departments;";
        Connection connection = connectionHolder.getConnection();
        List<Department> departments = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                Employee manager = new Employee();
                manager.setId(resultSet.getInt(3));

                Department department = new Department();
                department.setId(resultSet.getInt(1));
                department.setName(resultSet.getString(2));
                department.setManager(manager);

                departments.add(department);
            }
        } catch (SQLException e) {
            success = false;
            throw new DaoException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }finally {
                connectionHolder.releaseConnection(connection);
            }
        }
        return departments;
    }

    private int getLastId(Connection connection) throws SQLException {
        Statement lastIdStatement = connection.createStatement();
        ResultSet resultSet = lastIdStatement.executeQuery("SELECT last_insert_id();");
        resultSet.next();
        int lastId = resultSet.getInt(1);
        lastIdStatement.close();
        return lastId;
    }
    */
}
