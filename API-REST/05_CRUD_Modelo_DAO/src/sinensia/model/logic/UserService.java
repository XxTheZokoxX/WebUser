/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sinensia.model.logic;

import java.sql.SQLException;
import java.util.List;
import sinensia.modelo.User;
import sinensia.modelo.persistence.IUserDAO;
import sinensia.modelo.persistence.UserDAO_DerbyDB;

/**
 *
 * @author alumno
 */
public class UserService {

    IUserDAO daoUsers;

    public UserService(IUserDAO daoUsers) {
        this.daoUsers = daoUsers;
    }

    public boolean validate(String email, String password, String name, String strEdad) {
        if (email != null && password != null && name != null && strEdad != null) {
            if (!strEdad.matches("[0-9]{1,3}")) {
                throw new IllegalArgumentException("La edad no es un número válido");
            } else {
                int edad = Integer.parseInt(strEdad);
                if (email.length() > 3
                        && !password.equals("")
                        && !name.equals("")
                        && edad > 0) {

                    return true;
                }
            }
        }
        return false;
    }

    public User create(String email, String password, String name, String strEdad) throws SQLException {
        User u = null;
        if (validate(email, password, name, strEdad)) {
            int edad = Integer.parseInt(strEdad);
            u = new User(email, password, name, edad);
            u = daoUsers.create(u);
        }
        return u;
    }

    public List<User> getAll() throws SQLException {
        return daoUsers.getAll();
    }

    public boolean remove(int id) throws SQLException {
        return daoUsers.remove(id);
    }

    public User update(User user) throws SQLException {
        
        if (validate(user.getEmail(), user.getPassword(), user.getName(), 
                Integer.toString(user.getAge()))) {
            return daoUsers.update(user);
        } else {
            return null;
        }
    }

    public User update(int id, String email, String password, String name, String strEdad) throws SQLException {
        User u = null;
        if (validate(email, password, name, strEdad)) {
            int edad = Integer.parseInt(strEdad);
            u = new User(email, password, name, edad);
            u.setId(id);
            u = daoUsers.update(u);
        }
        return u;
    }
    
    public User getValidUser(String email, String password) 
        throws SQLException {
         return  daoUsers.getValidUser(email, password);
    }
}
