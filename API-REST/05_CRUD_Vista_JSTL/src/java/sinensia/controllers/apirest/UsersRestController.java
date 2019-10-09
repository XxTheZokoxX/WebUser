/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sinensia.controllers.apirest;

import com.google.gson.Gson;
import java.io.BufferedReader;
import sinensia.controllers.*;
import java.io.IOException;
import java.lang.reflect.Field;
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
@WebServlet(asyncSupported = true, urlPatterns = "/api/users")
public class UsersRestController extends HttpServlet {

    UserService userSrv;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.

        IUserDAO usersDAO = new UserDAO_DerbyDB();
        this.userSrv = new UserService(usersDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");

        try {
            setAccessControlHeaders(resp);
            List<User> usersList = userSrv.getAll();
            // Serializamos el List en un JSON
            Gson gson = new Gson();
            String textJson = gson.toJson(usersList);
            // Devolverá [ {"id": 1, "email": "aaa@aaa.com"...},  ]
            resp.getWriter().print(textJson);
        } catch (Exception ex) {
            Logger.getLogger(UsersRestController.class.getName()).log(Level.SEVERE, null, ex);
            resp.getWriter().print("{\"error\": \""
                    + ex.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //Recicir el JSON como parámetro de FORMulario
        //String jsonUser = req.getParameter("json");
       /*BufferedReader bufRead = req.getReader();
        String jsonUser;
        jsonUser = bufRead.readLine();
        System.out.println(jsonUser);
       
        Logger.getLogger(UsersRestController.class.getName()).log(Level.SEVERE, null, jsonUser);
        */
        
        
        /*String li
         while (jsonUser != null) {
             jsonUser += bu
         }*/
       

        User userObject = new Gson().fromJson(req.getReader(), User.class);
        try {
            setAccessControlHeaders(resp);
            userObject = userSrv.create(
                    userObject.getEmail(),
                    userObject.getPassword(),
                    userObject.getName(),
                    Integer.toString(userObject.getAge()));
            resp.setContentType("application/json;charset=UTF-8");

            Gson gson = new Gson();
            String textJson = gson.toJson(userObject);
            resp.getWriter().print(textJson);
        } catch (SQLException ex) {
            Logger.getLogger(UsersRestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);
        try {
            String jsonUser = req.getReader().readLine();
            User userObject = new Gson().fromJson(jsonUser, User.class);
           userSrv.remove(userObject.getId());
            resp.getWriter().print("OK");
        } catch (SQLException ex) {
            Logger.getLogger(UsersRestController.class.getName()).log(Level.SEVERE, null, ex);
  
            resp.getWriter().print("ERROR: " + ex.getMessage());
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonUser = req.getReader().readLine();
        User userObject = new Gson().fromJson(jsonUser, User.class);
        setAccessControlHeaders(resp);
        try { // Debe venir ya con el id
            userObject = userSrv.update(userObject);
        } catch (SQLException ex) {
            Logger.getLogger(UsersRestController.class.getName()).log(Level.SEVERE, null, ex);
        }
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().print(new Gson().toJson(userObject));
    }
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK); // Devolvemos cod 200 = OK
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin",
                "*");
        
        resp.setHeader("Access-Control-Allow-Methods",
                "OPTIONS,HEAD,GET,POST,PUT,DELETE");
        
        resp.setHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept"); 
        resp.addHeader("Access-Control-Max-Age", "1728000"); // 20 días
    }
}
