/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.lang.reflect.Field;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author iriel
 */
public class ClassHelper {
    public static Field[] getFields(Class cls){
        Field[] superClassFields = cls.getSuperclass().getDeclaredFields();
        
        Field[] fields = cls.getDeclaredFields();
        
        return (Field[]) ArrayUtils.addAll(superClassFields, fields);
    }
    
    public static Class getClassColumn(String c, List<Field> champs){
        Field filteredField = champs.stream().filter((field) 
                -> c.toLowerCase().compareToIgnoreCase(field.getName()) == 0)
                .findFirst()
                .orElse(null);
                
        return filteredField != null ? filteredField.getType() : null;
    }
}
