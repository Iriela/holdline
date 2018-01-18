/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author iriel
 */
public class DbConnect {
    public static Connection getConnection()throws Exception{
        try {
            Class.forName("org.postgresql.Driver");
            String url = System.getenv("JDBC_DATABASE_URL");
            return DriverManager.getConnection(url);
        } 
        catch(ClassNotFoundException | SQLException e) {
            throw e;
        }
    }
}
