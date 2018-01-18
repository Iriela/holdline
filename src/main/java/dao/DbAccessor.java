/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import configuration.DbConnect;
import helpers.ClassHelper;
import helpers.DbHelper;
import helpers.StringHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import model.Kernel;
import model.QueryParameters;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author iriel
 */
public class DbAccessor implements IDbAccessor{

    @Override
    public void save(Kernel object) throws Exception {
        try(Connection connection = DbConnect.getConnection()) {
            connection.setAutoCommit(false);
            try{
                save(object,connection);
                connection.commit();
            }
            catch(Exception exception){
                connection.rollback();
            }
        }
    }

    @Override
    public void save(Kernel object, Connection connection) throws Exception{
        Accessor accessor = new Accessor(object, "");
        
        String columns = StringUtils.join(accessor.getColumnsList().toArray(), ',');
        String values = StringUtils.join(accessor.getValuesList().toArray(), ',');
        
        String query = "INSERT INTO " + accessor.getTable() + " (" + columns + ") VALUES (" + values + ")";
        
        System.out.println(query);
        
        try{
            DbHelper.executeUpdate(accessor, query, connection);
            System.out.println("Insertion to "+accessor.getTable()+" succefully");
        }
        catch(SQLException exception){
            System.out.println("Error while trying to insert in database");
            throw exception;
        }
    }
    
    @Override
    public void update(Kernel object) throws Exception {
        try(Connection connection = DbConnect.getConnection()){
            connection.setAutoCommit(false);
            try{
                update(object,connection);
                connection.commit();
            }
            catch(Exception ex){
                connection.rollback();
                throw ex;
            }
        }
        
    }

    @Override
    public void update(Kernel object, Connection connection) throws Exception {
        Accessor accessor = new Accessor(object, "=?");
        
        String columns = StringUtils.join(accessor.getColumnsList().toArray(), ',');
        
        Class c = object.getClass();
        
        Method method = c.getMethod("getId", (Class[]) null);
        
        Object id = method.invoke(object, (Object[]) null);
        accessor.addFieldValueToList(id);
        
        String query = "UPDATE " + accessor.getTable() + " SET " + columns + "  WHERE id_" + accessor.getTable() + " = ?";

        try{
            System.out.println(query);
            DbHelper.executeUpdate(accessor, query, connection);
        }
        catch(SQLException exception){
            System.out.println("Error while trying to update in database");
            throw exception;
        }
    }
    
    @Override
    public void delete(Kernel object) throws Exception {
        try(Connection connection = DbConnect.getConnection()){
            connection.setAutoCommit(false);
            try{
                delete(object,connection);
                connection.commit();
            }
            catch(Exception ex){
                connection.rollback();
                throw ex;
            }
        }
    }
    
    @Override
    public void delete(Kernel object, Connection connection) throws Exception {
        Class objectClass = object.getClass();
        
        String table = objectClass.getSimpleName().toLowerCase();
        
        Method method = objectClass.getMethod("getId", (Class[])null);
     
        Object id = method.invoke(object, (Object[])null);
        
        String query = "DELETE FROM " + table + " WHERE id_" + table + " = ?";
        
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            
            System.out.println("id delete " + id);
            
            preparedStatement.executeUpdate();
            System.out.println("Line succefully deleted from " + table);
        }
        catch(SQLException exception){
            throw exception;
        }
    }

    @Override
    public Kernel get(Kernel object, QueryParameters queryParameters) throws Exception {
        try(Connection connection = DbConnect.getConnection()) {
            return get(object, queryParameters, connection);
        }
    }

    @Override
    public Kernel get(Kernel object, QueryParameters queryParameters, Connection connection) throws Exception {
        return select(object, queryParameters,connection).get(0);
    }
    
    @Override
    public Kernel get(Kernel object, boolean fetchAll) throws Exception {
        try(Connection connection = DbConnect.getConnection()) {
            return get(object, fetchAll, connection);
        }
    }
    
    @Override
    public Kernel get(Kernel object, boolean fetchAll, Connection connection) throws Exception {
        QueryParameters queryParameters = new QueryParameters("", fetchAll, 1, 1);
        return select(object, queryParameters,connection).get(0);
    }
    
    @Override
    public Kernel getObjectById(Kernel object, int id, boolean fetchAll) throws Exception{
        try(Connection conn = DbConnect.getConnection()) {
            return getObjectById(object, id, fetchAll, conn);
        }
    }
    
    @Override
    public Kernel getObjectById(Kernel object,
            int id, 
            boolean fetchAll,
            Connection connection) throws Exception{
        Class cls = object.getClass();
        
        Object result = null;
        
        Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        String table = cls.getSimpleName().toLowerCase();
        
        String query = "SELECT * FROM " + table + " WHERE id_" + table + "=" + id;
        
        ResultSet resultSet = null;
        
        try{
            System.out.println(query);
            resultSet = state.executeQuery(query);
            
            List<Field> fields = Arrays.asList(ClassHelper.getFields(cls));
            
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            
            while(resultSet.next()){
                result = cls.newInstance();
                
                for(int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    String columnName = resultSetMetaData.getColumnName(i);
                    String key = StringHelper.removeId(columnName);
                    String normalizedKey = StringHelper.normalize(key);
                    
                    if(columnName.startsWith("id_")){
                        // if primary key
                        if(cls.getSimpleName()
                            .toLowerCase()
                            .equals(key)){
                            
                            Method method = cls.getMethod("setId", Integer.class);
                            method.invoke(result, resultSet.getObject(i));
                        }
                        // if foreign key
                        else{
                            Class columnClass = ClassHelper.getClassColumn(key, new ArrayList(fields));
                            
                            Object obj = columnClass.newInstance();
                            Method method = cls.getMethod("set"+normalizedKey, columnClass);
                            if(fetchAll){
                                method.invoke(result, getObjectById((Kernel)obj, resultSet.getInt(i), fetchAll, connection));
                            }
                            else{
                                Object childobject = cls.newInstance();
                                method.invoke(result,childobject);
                                method = cls.getMethod("setId",Integer.class);
                                method.invoke(childobject, resultSet.getInt(i));
                            }
                        }
                    }
                    else{
                        Method method = cls.getMethod("set"+normalizedKey, ClassHelper.getClassColumn(columnName, fields));
                        method.invoke(result, resultSet.getObject(i));
                    }
                }
            }
            
            return (Kernel)result;
        }
        catch(SQLException exception){
            throw exception;
        }
        finally{
            if(resultSet!=null) resultSet.close();
            if(state!=null) state.close();
        }
    }

    @Override
    public List<Kernel> select(Kernel object, QueryParameters parameters) throws Exception{
        try(Connection connection = DbConnect.getConnection()) {
            return select(object, parameters, connection);
        }
        catch(Exception exception){
            throw exception;
        }
    }
    
    @Override
    public List<Kernel> select(Kernel object, QueryParameters parameters, Connection connection) throws Exception{
        String additionalQuery = parameters.getAdditionalQuery();
        int limit = parameters.getLimit();
        int offset = parameters.getOffset();
        boolean fetchAll = parameters.isFetchAll();
        Boolean isStrictMatching = parameters.isStrictMatching();
        
        Class c = object.getClass();
        List<Kernel> result = new ArrayList<>();
        Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String table = c.getSimpleName().toLowerCase();
        
        String query = "SELECT * FROM " + table + " WHERE 1<2 ";
        
        query += setConditionQuery(object, isStrictMatching);
        
        String primaryKey = "id_" + table;
        
        if (additionalQuery == null || additionalQuery.isEmpty()) {
            query += " ORDER BY " + primaryKey + " DESC";
        } 
        else {
            query += " " + additionalQuery;
        }
        offset--;
        if (limit > 0 && offset >= 0) {
            query += " LIMIT " + limit + " OFFSET " + offset;
        }
        System.out.println(query);
        ResultSet resultset = state.executeQuery(query);
        
        while (resultset.next()) {
            result.add(getObjectById(object, resultset.getInt(primaryKey), fetchAll, connection));
        }
        return result;
        
    }
    
    @Override
    public String setConditionQuery(Kernel object, Boolean isStrictMatching)throws Exception{
        Boolean strictMatch = isStrictMatching != null && isStrictMatching;
        
        HashMap hashMap = DbHelper.buildConditionQuery(object);
        
        if(hashMap.isEmpty()){
            return "";
        }
        
        String condition= "";
        
        List<Field> champs = DbHelper.getAllFields(object.getClass());
        String key;
        for (Field champ : champs) {
            if(Class.forName(champ.getType().getName()).getSuperclass().equals(Kernel.class)) {
               key = "id_" + champ.getName();
                if(hashMap.get(key)!=null) {
                    condition += "AND " + key + "='" + hashMap.get(key) + "' ";
                }
            }
            else {
                key = champ.getName();
                
                System.out.println("key " + key + " strictMatch "+strictMatch);
                
                if(hashMap.get(key) != null) {
                    if(champ.getType().equals(String.class) && !strictMatch){
                        condition += "AND UPPER(" + key + ") LIKE UPPER('%" + StringHelper.replaceQuotes(hashMap.get(key).toString()) + "%') ";
                    }
                    else condition+= "AND " + key + "='" + hashMap.get(key) + "' ";
                }
            }
        }
        key = "id_" + object.getClass().getSimpleName().toLowerCase();
        if(hashMap.get(key)!=null) {
            condition += "AND " + key + "=" + hashMap.get(key);
        }
        
        System.out.println(condition);
        
        return condition;
    }
    
    @Override
    public Kernel create(Kernel object, boolean fetchAll, Connection connection) throws Exception{
        try{
            connection.setAutoCommit(false);
            save(object, connection);
            connection.commit();
            
            return get(object, fetchAll,connection);
        }
        catch(Exception exception){
            exception.printStackTrace();
            connection.rollback();
            throw exception;
        }
    }
    
    @Override
    public Kernel create(Kernel object, boolean fetchAll) throws Exception{
        try(Connection conn = DbConnect.getConnection()) {
            return create(object, fetchAll, conn);
        }
    }
    
    @Override
    public Kernel edit(Kernel object, Connection connection) throws Exception{
        try{
            connection.setAutoCommit(false);
            update(object);
            connection.commit();
            return get(object, false, connection);
        }
        catch(Exception exception){
            connection.rollback();
            throw exception;
        }
    }
    
    @Override
    public Kernel edit(Kernel object) throws Exception{
        try(Connection conn = DbConnect.getConnection()) {
            return edit(object, conn);
        }
    }
}
