package com.getjavajobs.larionov.phonebook.dao.sql;

import com.getjavajobs.larionov.phonebook.dao.Dao;
import com.getjavajobs.larionov.phonebook.dao.sql.mappers.EmployeeRowMapper;
import com.getjavajobs.larionov.phonebook.exceptions.DaoException;
import com.getjavajobs.larionov.phonebook.model.Department;
import com.getjavajobs.larionov.phonebook.model.Employee;
import com.getjavajobs.larionov.phonebook.model.Phone;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 01.09.2014.
 */
@Repository
public class EmployeeDao implements Dao<Employee> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Employee employee) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        try{
            int id = (Integer)session.save(employee);
            employee.setId(id);
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
    public Employee get(int id) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        try{
            return (Employee) session.get(Employee.class, id);
        }catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Employee employee) throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        try{
            session.update(employee);
        }catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Employee> getAll() throws DaoException {
        Session session = sessionFactory.getCurrentSession();
        try{
            return (List<Employee>)session.createCriteria(Employee.class).list();
        }catch (Exception e) {
            throw new DaoException(e);
        }
    }










    /*
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    private EmployeeRowMapper employeeRowMapper;


    @Transactional
    public void add(Employee employee) {
        String request = "INSERT INTO employees(first_name, last_name, department_id, project, email) VALUES(?,?,?,?,?);";
        Object[] params = new Object[]{
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartment() != null ? employee.getDepartment().getId() : null,
                employee.getProject(),
                employee.getEmail()
        };

        jdbcTemplate.update(request, params);
        addPhones(employee.getId(), employee.getPhoneList());
    }

    @Transactional
    public void delete(int id) {
        String request = "DELETE FROM employees WHERE id=?;";
        jdbcTemplate.update(request, new Object[]{id});
    }

    @Transactional
    public Employee get(int id) {
        String request = "SELECT id, first_name, last_name, department_id, project, email FROM employees WHERE id=?;";
        Employee employee = jdbcTemplate.queryForObject(request, new Object[]{id}, employeeRowMapper);
        employee.setPhoneList(getPhones(employee.getId()));
        return employee;
    }

    @Transactional
    public  void update(Employee employee) {
        String request = "UPDATE employees SET first_name=?, last_name=?, department_id=?, project=?, email=? WHERE id=?;";
        Object[] params = new Object[]{
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartment() != null ? employee.getDepartment().getId() : null,
                employee.getProject(),
                employee.getEmail(),
                employee.getId()
        };
        jdbcTemplate.update(request, params);
        deletePhones(employee.getId());
        addPhones(employee.getId(), employee.getPhoneList());
    }

    @Transactional
    public List<Employee> getAll() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM employees");
        List<Employee> employees = new ArrayList<>();
        for (Map row : rows) {
            Employee employee = new Employee();
            employee.setId((Integer)(row.get("id")));
            employee.setFirstName((String) row.get("first_name"));
            employee.setLastName((String) row.get("last_name"));
            employee.setProject((String) row.get("project"));
            employee.setEmail((String) row.get("email"));
            int departmentId = (int)row.get("department_id");
            if(departmentId!=0) {
                employee.setDepartment(new Department());
                employee.getDepartment().setId(departmentId);
            }
            employee.setPhoneList(getPhones(employee.getId()));
           employees.add(employee);
        }
        return employees;
    }

    private void addPhones(int employee_Id, List<Phone> phoneList) {
        String command = "INSERT INTO phones(employee_id, country_code, city_code, phone_number, is_mobile) VALUES (?,?,?,?,?);";
        for(Phone phone:phoneList) {
            Object[] params = new Object[]{
                    employee_Id,
                    phone.getCountryCode(),
                    phone.getCityCode(),
                    phone.getPhoneNumber(),
                    phone.isMobile()?1:0,
            };
            jdbcTemplate.update(command, params);
        }
    }
    private void deletePhones(int employeeId) {
        String request = "DELETE FROM phones WHERE employee_id=?";
        jdbcTemplate.update(request, new Object[]{employeeId});
    }
    private List<Phone> getPhones(int employeeId) {
        String command = "SELECT id, country_code, city_code, phone_number, is_mobile FROM phones WHERE employee_id=?;";
        List<Phone> phoneList = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(command, new Object[]{employeeId});
        for (Map row:rows) {
            Phone phone = new Phone();
            phone.setId((int)row.get("id"));
            phone.setCountryCode((String)row.get("country_code"));
            phone.setCityCode((String)row.get("city_code"));
            phone.setPhoneNumber((String)row.get("phone_number"));
            phone.setMobile((boolean)row.get("is_mobile"));
        }
        return phoneList;
    }


    private ConnectionHolder connectionHolder = ConnectionHolder.getInstance();

    @Override
    public Employee add(Employee employee) throws DaoException {
        boolean success = true;
        String request = "INSERT INTO employees(first_name, last_name, department_id, project, email) VALUES(?,?,?,?,?);";
        Connection connection = connectionHolder.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            employeeToStatement(statement, employee);
            statement.execute();
            employee.setId(getLastId(connection));
            addPhones(employee.getId(), employee.getPhoneList(), connection);
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
        return employee;
    }

    @Override
    public void delete(int id) throws DaoException {
        boolean success = true;
        String request = "DELETE FROM employees WHERE id=?;";
        Connection connection = connectionHolder.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1, id);
            statement.execute();
        }catch (SQLException e){
            success=false;
            throw new DaoException(e);
        }finally {
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
    public Employee get(int id) throws DaoException {
        boolean success = true;
        Employee employee = null;
        String request = "SELECT id, first_name, last_name, department_id, project, email FROM employees WHERE id=?;";
        Connection connection = connectionHolder.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(request)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                employee = new Employee();
                resultSetToEmployee(resultSet, employee);
                employee.setPhoneList(getPhones(id, connection));
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
        return employee;
    }

    @Override
    public Employee update(Employee employee) throws DaoException {
        boolean success=true;
        String request = "UPDATE employees SET first_name=?, last_name=?, department_id=?, project=?, email=? WHERE id=?;";
        Connection connection = connectionHolder.getConnection();
        try(PreparedStatement statement = connection.prepareStatement(request)){
            employeeToStatement(statement, employee);
            statement.setInt(6, employee.getId());
            statement.execute();
            deletePhones(employee.getId(), connection);
            addPhones(employee.getId(), employee.getPhoneList(), connection);

        } catch (SQLException e) {
            success = false;
            throw new DaoException(e);
        }finally {
            try{
                if(!connection.getAutoCommit()) {
                    if(success){
                        connection.commit();
                    }else {
                        connection.rollback();
                    }
                }
            }catch (SQLException e) {
                throw new DaoException(e);
            }finally {
                connectionHolder.releaseConnection(connection);
            }
        }
        return employee;
    }

    @Override
    public List<Employee> getAll() throws DaoException{
        boolean success = true;
        String request = "SELECT id, first_name, last_name, department_id, project, email FROM employees;";
        Connection connection = connectionHolder.getConnection();
        List<Employee> employees = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                Employee employee = new Employee();
                resultSetToEmployee(resultSet, employee);
                employee.setPhoneList(getPhones(employee.getId(), connection));
                employees.add(employee);
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
            } finally {
                connectionHolder.releaseConnection(connection);
            }
        }
        return employees;
    }
    private int getLastId(Connection connection) throws SQLException {
        Statement lastIdStatement = connection.createStatement();
        ResultSet resultSet = lastIdStatement.executeQuery("SELECT last_insert_id();");
        resultSet.next();
        int lastId = resultSet.getInt(1);
        lastIdStatement.close();
        return lastId;
    }

    private void employeeToStatement(PreparedStatement statement, Employee employee) throws SQLException{
        statement.setString(1, employee.getFirstName());
        statement.setString(2, employee.getLastName());
        if (employee.getDepartment()==null) {
            statement.setNull(3, Types.INTEGER);
        } else {
            statement.setInt(3, employee.getDepartment().getId());
        }
        statement.setString(4, employee.getProject());
        statement.setString(5, employee.getEmail());
    }
    private void resultSetToEmployee(ResultSet resultSet, Employee employee) throws SQLException {
        Department department = null;
        int departmentId = resultSet.getInt(4);
        if (departmentId!=0) {
            department = new Department();
            department.setId(departmentId);
        }
        employee.setId(resultSet.getInt(1));
        employee.setFirstName(resultSet.getString(2));
        employee.setLastName(resultSet.getString(3));
        employee.setDepartment(department);
        employee.setProject(resultSet.getString(5));
        employee.setEmail(resultSet.getString(6));
    }
    */







}
