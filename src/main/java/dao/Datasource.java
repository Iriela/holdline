/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 *
 * @author iriel
 */
public class Datasource {
    HashMap entry;
    
    public Datasource(ResultSet resultSet) throws SQLException{
        this.entry = new HashMap<>();
        
        ResultSetMetaData metaData = resultSet.getMetaData();
        
        for(int i = 1; i <= metaData.getColumnCount(); i++)
        {
            this.entry.put(metaData.getColumnName(i).toLowerCase(), resultSet.getObject(i));
        }
    }
    
    public Object getObject(String column)
    {
        return this.entry.get(column);
    }

    public Integer getInt(String column)
    {
        try
        {
            return Integer.parseInt(this.entry.get(column).toString());
        }
        catch(NumberFormatException n)
        {
            return 0;
        }
        catch(NullPointerException nn)
        {
            return null;
        }
    }
    
    public Float getFloat(String column)
    {
        try
        {
            return Float.parseFloat(this.entry.get(column).toString());
        }
        catch(NumberFormatException n)
        {
            return 0f;
        }
        catch(NullPointerException nn)
        {
            return null;
        }
    }
    
    public double getDouble(String column)
    {
        return Double.parseDouble(this.entry.get(column).toString());
    }
    
    public String getString(String column)
    {
        try{
            return this.entry.get(column).toString();
        }
        catch(Exception ex){
            return null;
        }
    }
    
    public Timestamp getDate(String column){
        try{
            String columnValue = this.getString(column);
            System.out.println("Date value " + columnValue);
            return Timestamp.valueOf(this.getString(column));
        }
        catch(Exception exception){
            System.out.println(exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }
    
    public boolean getBoolean(String column)
    {
        return Boolean.parseBoolean(this.entry.get(column).toString());
    }
}
