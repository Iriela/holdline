/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.holdline;

import dao.Datasource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.Kernel;

/**
 *
 * @author iriel
 */
public class Favorites extends Kernel{
    private Users users;
    
    private Meal meal;
    
    private Timestamp creationdate;

    public Favorites(){}
    
    public Favorites(Users users, Meal meal, Timestamp creationdate) {
        this.users = users;
        this.meal = meal;
        this.creationdate = creationdate;
    }

    public Favorites(Datasource datasource, Users users, Meal meal){
        this.id = datasource.getInt("id_favorites");
        this.users = users;
        this.meal = meal;
        this.creationdate = datasource.getDate("creationdate");
    }
    
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Timestamp getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Timestamp creationdate) {
        this.creationdate = creationdate;
    }
}
