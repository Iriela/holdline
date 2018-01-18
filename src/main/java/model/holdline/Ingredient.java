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
public class Ingredient extends Kernel{
    private String name;
    
    private Category category;
    
    private String caloricintake;

    public Ingredient(){}
    
    public Ingredient(String name, Category category, String caloricintake) {
        this.name = name;
        this.category = category;
        this.caloricintake = caloricintake;
    }

    public Ingredient(Datasource datasource, Category category){
        this.id = datasource.getInt("id_ingredient");
        this.category = category;
        this.caloricintake = datasource.getString("caloricintake");
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCaloricintake() {
        return caloricintake;
    }

    public void setCaloricintake(String caloricintake) {
        this.caloricintake = caloricintake;
    }
    
    
}
