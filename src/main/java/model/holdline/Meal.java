/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.holdline;

import dao.Datasource;
import model.Kernel;

/**
 *
 * @author iriel
 */
public class Meal extends Kernel{
    private String name;

    public Meal(){}
    
    public Meal(String name) {
        this.name = name;
    }
    
    public Meal(Datasource datasource){
        this.id = datasource.getInt("id_meal");
        this.name = datasource.getString("name");
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
