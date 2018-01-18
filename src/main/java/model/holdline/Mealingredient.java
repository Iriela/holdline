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
public class Mealingredient extends Kernel{
    private Meal meal;
    
    private Ingredient ingredient;

    public Mealingredient(){}
    
    public Mealingredient(Meal meal, Ingredient ingredient) {
        this.meal = meal;
        this.ingredient = ingredient;
    }

    public Mealingredient(Datasource datasource, Meal meal, Ingredient ingredient){
        this.id = datasource.getInt("id_mealingredient");
        this.meal = meal;
        this.ingredient = ingredient;
    }
    
    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    
    
}
