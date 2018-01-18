/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.List;
import model.Kernel;
import model.QueryParameters;

/**
 *
 * @author iriel
 */
public interface IDbAccessor {
    public void save(Kernel object) throws Exception;
    public void save(Kernel object, Connection connection) throws Exception;
    public void update(Kernel object) throws Exception;
    public void update(Kernel object, Connection connection) throws Exception;
    public void delete(Kernel object) throws Exception;
    public void delete(Kernel object, Connection connection) throws Exception;
    public Kernel get(Kernel object, QueryParameters queryParameters) throws Exception;
    public Kernel get(Kernel object, QueryParameters queryParameters, Connection connection) throws Exception;
    public Kernel get(Kernel object, boolean fetchAll) throws Exception;
    public Kernel get(Kernel object, boolean fetchAll, Connection connection) throws Exception;
    public Kernel getObjectById(Kernel object, int id, boolean fetchAll) throws Exception;
    public Kernel getObjectById(Kernel object, int id, boolean fetchAll, Connection connection) throws Exception;
    public List<Kernel> select(Kernel object, QueryParameters parameters) throws Exception;
    public List<Kernel> select(Kernel object, QueryParameters parameters, Connection connection) throws Exception;
    public String setConditionQuery(Kernel object, Boolean isStrictMatching)throws Exception;
    public Kernel create(Kernel object, boolean fetchAll) throws Exception;
    public Kernel create(Kernel object, boolean fetchAll, Connection connection) throws Exception;
    public Kernel edit(Kernel object) throws Exception;
    public Kernel edit(Kernel object, Connection connection) throws Exception;
}
