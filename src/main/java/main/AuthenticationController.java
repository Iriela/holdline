/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import model.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iriel
 */
@RestController
public class AuthenticationController {
    
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public Authentication authenticate(
            @RequestParam(value="login", defaultValue="log") String login,
            @RequestParam(value="password", defaultValue="pass") String password){
        Authentication auth = new Authentication(login,password);
        return auth;
    }
}
