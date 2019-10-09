/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sinensia.modelo.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sinensia.modelo.User;

/**
 *
 * @author alumno
 */
public class UserDAO_DerbyDB implements IUserDAO {

    private static final String CONEX_DB = "jdbc:derby://localhost:1527/db_users";
    private static final String USER_DB = "root";
    private static final String PSSWD_DB = "1234";

    public UserDAO_DerbyDB() {
        // Cargamos el driver de Derby DB. Está basado en JDBC,
        // Una implementación de ODBC para Java

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        } catch (Exception ex) {
            Logger.getLogger(UserDAO_DerbyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public User create(User user) throws SQLException {
        Connection con = DriverManager.getConnection(CONEX_DB, USER_DB, PSSWD_DB);
        /* String sqlQuery = "INSERT INTO users (email, password, name, age) VALUES ("
                + " '" + user.getEmail()  + "', '" + user.getPassword() + "', '" + user.getName()  + "', " + user.getAge() + ")"; 
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sqlQuery);*/
        // Los ? son los parámetros de la sentencia SQL. Evitamos SQL Injection
        String sqlQuery = "INSERT INTO users (email, password, name, age) VALUES (?, ?, ?, ?)";
        PreparedStatement prepStmt = con.prepareCall(sqlQuery);
        prepStmt.setString(1, user.getEmail());
        prepStmt.setString(2, user.getPassword());
        prepStmt.setString(3, user.getName());
        prepStmt.setInt(4, user.getAge());
        prepStmt.executeUpdate();

        // Aquí buscamos el Id a través del email
        /*String sqlQueryBusqId = "SELECT id FROM users WHERE email=?";
        PreparedStatement prepStmtBusqId = con.prepareCall(sqlQueryBusqId);
        prepStmtBusqId.setString(1, user.getEmail());
        ResultSet res = prepStmtBusqId.executeQuery();
        while (res.next()) {
            int id = res.getInt("id");
            user.setId(id);
        }*/
        user.setId(getOneByEmail(user.getEmail()).getId());
        con.close();
        // Devolvemos el usuario, con el Id que lo hemos buscado en la bbdd
        return user;
    }

    public User getOneByEmail(String email) throws SQLException {
        try (Connection con = DriverManager.getConnection(CONEX_DB, USER_DB, PSSWD_DB)) {
            String sqlQueryBusqId = "SELECT id, password, name, age FROM users WHERE email=?";
            PreparedStatement prepStmtBusqId = con.prepareCall(sqlQueryBusqId);
            prepStmtBusqId.setString(1, email);
            ResultSet res = prepStmtBusqId.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String password = res.getString("password");
                String name = res.getString("name");
                int age = res.getInt("age");
                User user = new User(email, password, name, age);
                user.setId(id);
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        try (Connection con = DriverManager.getConnection(CONEX_DB, USER_DB, PSSWD_DB)) {

            ArrayList<User> usersList = new ArrayList<>();
            String sqlQuery = "SELECT id, email, password, name, age FROM users";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(sqlQuery);
            while (res.next()) {
                int id = res.getInt("id");
                String email = res.getString("email");
                String password = res.getString("password");
                String name = res.getString("name");
                int age = res.getInt("age");
                User newUser = new User(email, password, name, age);
                newUser.setId(id);
                usersList.add(newUser);
            }
            return usersList;
        }
    }

    @Override
    public boolean remove(int id) throws SQLException {
        Connection con = DriverManager.getConnection(CONEX_DB, USER_DB, PSSWD_DB);
        String sqlQuery = "DELETE FROM users WHERE id = ?";
        PreparedStatement prepStmt = con.prepareCall(sqlQuery);
        prepStmt.setInt(1, id);
        int count = prepStmt.executeUpdate();
        // if (count == 1) return true; else return false;
        return count == 1;
    }

    @Override
    public User update(User user) throws SQLException {
        try (Connection con = DriverManager.getConnection(CONEX_DB, USER_DB, PSSWD_DB)) {
            String sqlQuery = "UPDATE  users SET email = ?, password = ?, name = ?, age = ? "
                    + " WHERE id = ?";
            PreparedStatement prepStmt = con.prepareCall(sqlQuery);
            prepStmt.setString(1, user.getEmail());
            prepStmt.setString(2, user.getPassword());
            prepStmt.setString(3, user.getName());
            prepStmt.setInt(4, user.getAge());
            prepStmt.setInt(5, user.getId());
            int count = prepStmt.executeUpdate();
            if (count == 1) {
                return getOneByEmail(user.getEmail()); // ó return user;
            } else {
                return null;
            }
        }
    }
    @Override
    public User getValidUser(String email, String password) throws SQLException {
        try (Connection con = DriverManager.getConnection(CONEX_DB, USER_DB, PSSWD_DB)) {
            String sqlQueryBusqId = "SELECT id, name, age FROM users WHERE email=? AND password=?";
            PreparedStatement prepStmtBusqId = con.prepareCall(sqlQueryBusqId);
            prepStmtBusqId.setString(1, email);
            prepStmtBusqId.setString(2, password);
            ResultSet res = prepStmtBusqId.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                int age = res.getInt("age");
                User user = new User(email, password, name, age);
                user.setId(id);
                return user;
            }
        }
        return null;
    }
}
