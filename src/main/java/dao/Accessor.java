/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helpers.ClassHelper;
import helpers.StringHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import model.Kernel;

/**
 *
 * @author iriel
 */
public final class Accessor {
    private List<String> columnsList;
        
    private List<String> valuesList;

    private List<Object> fieldsValues;
    
    private String table;
    
    public Accessor(Kernel object, String additionalQuery) throws Exception{
        Class kernelClass = object.getClass();
        
        Field[] fields = ClassHelper.getFields(kernelClass);
        
        List<String> columnList = new ArrayList<>();
        
        List<String> valueList = new ArrayList<>();
        
        List<Object> fieldValues = new ArrayList<>();
        
        for(Field field : fields){
            if(!field.getName().equals("id")){
                Method method = kernelClass.getMethod("get" + StringHelper.normalize(field.getName()), (Class[]) null);
                
                Object result = method.invoke(object, (Object[]) null);
                
                if(result != null){
                    fieldValues.add(result);
                }
                else{
                    fieldValues.add(null);
                }
                
                columnList.add(field.getName()+additionalQuery);
                valueList.add("?");
            }
        }
        
        this.setColumnsList(columnList);
        
        this.setFieldsValues(fieldValues);
        
        this.setValuesList(valueList);
        
        this.setTable(kernelClass.getSimpleName().toLowerCase());
    }

    public List<String> getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(List<String> columnsList) {
        this.columnsList = columnsList;
    }

    public void addColumnToList(String column){
        this.columnsList.add(column);
    }
    
    public List<String> getValuesList() {
        return valuesList;
    }

    public void setValuesList(List<String> valuesList) {
        this.valuesList = valuesList;
    }
    
    public void addValueToList(String value){
        this.valuesList.add(value);
    }

    public List<Object> getFieldsValues() {
        return fieldsValues;
    }

    public void setFieldsValues(List<Object> fieldsValues) {
        this.fieldsValues = fieldsValues;
    }

    public void addFieldValueToList(Object object){
        this.fieldsValues.add(object);
    }
    
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
