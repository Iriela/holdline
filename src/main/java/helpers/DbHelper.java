/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import dao.Accessor;
import model.Kernel;

/**
 *
 * @author iriel
 */
public class DbHelper {
    
    public static void executeUpdate(Accessor accessor, String query, Connection connection) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        
        try{
            int i = 1;
            for(Object fieldValue : accessor.getFieldsValues()){
                preparedStatement.setObject(i, fieldValue);
                i++;
            }
            
            preparedStatement.executeUpdate();
            
        }
        catch(SQLException exception){
            System.out.println("Error while executing update in database");
            throw exception;
        }
        finally{
            if(preparedStatement!=null)
            {
                preparedStatement.close();
            }
        }
    }
    
    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();

        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }
            
        return fields;
    }
    
    public static HashMap buildConditionQuery(Kernel object)throws Exception{
        HashMap hashMap = new HashMap();
        List<Field> fields = getAllFields(object.getClass());
        Method method;
        String key;
        for (Field field : fields) {
            Object o;
            String normalizedFieldName = StringHelper.normalize(field.getName());
            if(!field.getName().equals("id")&&!field.getName().equals("ref")){
                if (Class.forName(field.getType().getName()).getSuperclass().equals(Kernel.class)) {
                    key = "id_" + field.getName();
                    method = object.getClass().getMethod("get" + normalizedFieldName, (Class[]) null);
                    o = method.invoke(object, (Object[]) null);
                    if(o != null){
                        method = o.getClass().getMethod("getId", (Class<?>[]) null);
                        Object invoke = method.invoke(o, (Object[]) null);
                        if(invoke != null && (int)invoke !=0)
                        {
                            hashMap.put(key,(int)invoke);
                        }
                    }
                }
                else {
                    key = field.getName();
                    method = object.getClass().getMethod("get" + normalizedFieldName, (Class[]) null);
                    o = method.invoke(object, (Object[]) null);
                    if(o != null)
                    {
                        hashMap.put(key, o);
                    }
                }
            }
        }
        method = object.getClass().getMethod("getId", (Class<?>[]) null);
        Object invoke = method.invoke(object, (Object[]) null);
        if(invoke != null && (int)invoke != 0)
        {
            key = "id_" + object.getClass().getSimpleName().toLowerCase();
            hashMap.put(key, (int)invoke);
        }
        return hashMap;
    }
}
