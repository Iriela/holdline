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
public class Category extends Kernel{
    private String name;
    
    public Category(){}
    
    public Category(String name) {
        this.name = name;
    }

    public Category(Datasource datasource){
        this.id = datasource.getInt("id_category");
        this.name = datasource.getString("name");
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
