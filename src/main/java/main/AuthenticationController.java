/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import constants.StatusCode;
import exception.AuthenticationException;
import model.Mapper;
import model.holdline.Users;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.UserService;

/**
 *
 * @author iriel
 */
@RestController
public class AuthenticationController {
    
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public Object authenticateUser(
            @RequestParam(value="login") String login,
            @RequestParam(value="password") String password){
        try{
            return new Mapper(UserService.authenticateUser(login, password), StatusCode.SUCCESS);
        }
        catch(Exception exception){
            exception.printStackTrace();
            return new AuthenticationException("User not found", StatusCode.ERROR);
        }
    }
    
    @RequestMapping(value = "/getuserinfo", method = RequestMethod.POST)
    public Object getUserInfo(
            @RequestParam(value="login") String login,
            @RequestParam(value="password") String password){
        try{
            return new Mapper(UserService.findUser(login, password), StatusCode.SUCCESS);
        }
        catch(Exception ex){
            return new AuthenticationException("User not found", StatusCode.ERROR);
        }
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object registerUser(
            @RequestParam(value="login") String login,
            @RequestParam(value="password") String password) throws Exception{
        try{
            if(UserService.checkExistingUser(login)){
                return new AuthenticationException("User Already exists", StatusCode.ERROR);
            }
            return new Mapper(UserService.registerUser(login, password), StatusCode.SUCCESS);
        }
        catch(Exception ex){
            return new AuthenticationException("Error while register", StatusCode.ERROR);
        }
    }
    
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Object editPassword(
            @RequestParam(value="login") String login, 
            @RequestParam(value="oldPassword") String oldPassword,
            @RequestParam(value="newPassword") String newPassword) throws Exception{
        try{
            return new Mapper(UserService.updateUser(login, oldPassword, newPassword), StatusCode.SUCCESS);
        }
        catch(Exception exception){
            exception.printStackTrace();
            return new AuthenticationException("Error while changing password", StatusCode.ERROR);
        }
    }
    
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public void deleteUser(
            @RequestParam(value="login") String login,
            @RequestParam(value="password") String password) throws Exception{
        try{
            UserService.deleteUser(login, password);
        }
        catch(Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }
}
