/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.holdline;

import dao.Datasource;
import java.sql.Timestamp;
import model.Kernel;

/**
 *
 * @author iriel
 */
public class Users extends Kernel{
    private String username;
    
    private String password;

    private String email;
    
    private Integer height;
    
    private String weight;
    
    private Timestamp registrationdate;
    
    private Timestamp latestupdate;
    
    public Users(){}
    
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(String username, String password, Timestamp registrationdate, Timestamp latestupdate) {
        this.username = username;
        this.password = password;
        this.registrationdate = registrationdate;
        this.latestupdate = latestupdate;
    }
    
    public Users(Datasource datasource){
        this.id = datasource.getInt("id_users");
        this.username = datasource.getString("username");
        this.password = datasource.getString("password");
        this.email = datasource.getString("email");
        this.height = datasource.getInt("height");
        this.weight = datasource.getString("weight");
        this.registrationdate = datasource.getDate("registrationtime");
        this.latestupdate = datasource.getDate("latestupdate");
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Timestamp getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(Timestamp registrationdate) {
        this.registrationdate = registrationdate;
    }

    public Timestamp getLatestupdate() {
        return latestupdate;
    }

    public void setLatestupdate(Timestamp latestupdate) {
        this.latestupdate = latestupdate;
    }
    
    
}
