/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import helpers.StringHelper;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.QueryParameters;
import model.holdline.Users;

/**
 *
 * @author iriel
 */
public class UserService extends Service{
    public static Users registerUser(String login, String password) throws Exception{
        String storedPassword = StringHelper.hash(password);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Users user = new Users(login, storedPassword, now, now);
        return (Users) create(user, false);
    }
    
    public static boolean authenticateUser(String login, String password)throws Exception{
        return findUser(login, password) != null;
    }
    
    public static Users findUser(String login, String password) throws Exception{
        String storedPassword = StringHelper.hash(password);
        Users user = new Users(login, storedPassword);
        QueryParameters queryParameters = new QueryParameters("", false, 1, 1, true);
        return (Users)get(user, queryParameters);
    }
    
    public static Users updateUser(String login,String oldPassword,String newPassword) throws Exception{
        String hashedNewPassword = StringHelper.hash(newPassword);
        Users user = findUser(login, oldPassword);
        user.setPassword(hashedNewPassword);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        user.setLatestupdate(now);
        return (Users) edit(user,false);
    }
    
    public static void deleteUser(String login, String password) throws Exception{
        Users user = findUser(login,password);
        delete(user);
    }
    
    public static boolean checkExistingUser(String login) throws Exception{
        try{
            Users user = new Users();
            user.setUsername(login);
            QueryParameters queryParameters = new QueryParameters("", false, 1, 1, true);
            return get(user, queryParameters).getId() != null;
        }
        catch(Exception ex){
            return false;
        }
    }
}
