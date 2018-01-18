/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.DbAccessor;
import dao.IDbAccessor;
import java.util.List;
import model.Kernel;
import model.QueryParameters;

/**
 *
 * @author iriel
 */
public class Service {
    private static final IDbAccessor DBACCESS = new DbAccessor();
    
    static void save(Kernel object) throws Exception{
        DBACCESS.save(object);
    }
    
    static void update(Kernel object) throws Exception{
        DBACCESS.update(object);
    }
    
    static void delete(Kernel object) throws Exception{
        DBACCESS.delete(object);
    }
    
    static Kernel create(Kernel object, boolean fetchAll) throws Exception{
        try{
            return DBACCESS.create(object, fetchAll);
        }
        catch(Exception exception){
            throw exception;
        }
    }
    
    static Kernel edit(Kernel object, boolean fetchAll) throws Exception{
        try{
            return DBACCESS.edit(object);
        }
        catch(Exception exception){
            throw exception;
        }
    }
    
    static Kernel get(Kernel object, boolean fetchAll) throws Exception{
        try{
            return DBACCESS.get(object, fetchAll);
        }
        catch(Exception exception){
            throw exception;
        }
    }
    
    static Kernel get(Kernel object, QueryParameters queryParameters) throws Exception{
        try{
            return DBACCESS.get(object, queryParameters);
        }
        catch(Exception exception){
            throw exception;
        }
    }
    
    static Kernel selectFirst(Kernel object, boolean fetchAll) throws Exception{
        try{
            return DBACCESS.get(object, fetchAll);
        }
        catch(Exception exception){
            throw exception;
        }
    }
    
    static List<Kernel> select(Kernel object, QueryParameters queryParameters) throws Exception{
        try{
            return DBACCESS.select(object, queryParameters);
        }
        catch(Exception exception){
            throw exception;
        }
    }
    
}
