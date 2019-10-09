/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sinensia.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sinensia.model.logic.UserService;
import sinensia.modelo.User;
import sinensia.modelo.persistence.IUserDAO;
import sinensia.modelo.persistence.UserDAO_DerbyDB;

/**
 *
 * @author alumno
 */
@WebServlet(asyncSupported = true, urlPatterns = "/users.do")
public class UsersController extends HttpServlet {

    UserService userSrv;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.

        IUserDAO usersDAO = new UserDAO_DerbyDB();
        this.userSrv = new UserService(usersDAO);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String id = req.getParameter("id");
            String email = req.getParameter("email");     
            String passwordEncript = req.getParameter("password_encript");
      
            String name = req.getParameter("name");
            String age = req.getParameter("age");

            String method = req.getParameter("method");
            if ("Delete".equals(method)) {
                userSrv.remove(Integer.parseInt(id));
            } else if ("Update".equals(method)) {
                User updUsr = userSrv.update(Integer.parseInt(id), email, passwordEncript, name, age);
                req.setAttribute("user", updUsr);
            } else {
                User newUser = userSrv.create(email, passwordEncript, name, age);
                req.setAttribute("user", newUser);
            }
        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("errorMessage", ex.getMessage());
        } finally {
            req.getRequestDispatcher("result.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String passwordEncript = req.getParameter("password_encript");
            if (email != null && passwordEncript != null) {
                User user = userSrv.getValidUser(email, passwordEncript);
                if (user != null) {
                    req.getSession().setAttribute("userLogged", user);
                    resp.addCookie(new Cookie("email", email));
                    req.getRequestDispatcher("result.jsp").forward(req, resp);
                } else {
                    req.getSession().removeAttribute("userLogged");
                    Cookie coEmail = new Cookie("email", null);
                    coEmail.setMaxAge(0);
                    resp.addCookie(coEmail);
                    throw new Exception("Error en email y password");
                }
            } else {
                List<User> listUsers = userSrv.getAll();
                req.setAttribute("usersList", listUsers);
                req.getRequestDispatcher("listUsers.jsp").forward(req, resp);
            }
        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
            req.setAttribute("errorMessage", ex.getMessage());
        } finally {
            req.getRequestDispatcher("result.jsp").forward(req, resp);
        }
    }

}
