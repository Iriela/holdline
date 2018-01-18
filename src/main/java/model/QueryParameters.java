/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author iriel
 */
public class QueryParameters {
    private String additionalQuery;
    private boolean fetchAll;
    private int limit; 
    private int offset;
    private Boolean strictMatching;

    public QueryParameters(String additionalQuery, boolean fetchAll, int limit, int offset, boolean strictMatching) {
        this.additionalQuery = additionalQuery;
        this.fetchAll = fetchAll;
        this.limit = limit;
        this.offset = offset;
        this.strictMatching = strictMatching;
    }
    
    
    
    public QueryParameters(String additionalQuery, boolean fetchAll, int limit, int offset) {
        this.additionalQuery = additionalQuery;
        this.fetchAll = fetchAll;
        this.limit = limit;
        this.offset = offset;
    }
    
    public String getAdditionalQuery() {
        return additionalQuery;
    }

    public void setAdditionalQuery(String additionalQuery) {
        this.additionalQuery = additionalQuery;
    }

    public boolean isFetchAll() {
        return fetchAll;
    }

    public void setFetchAll(boolean fetchAll) {
        this.fetchAll = fetchAll;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Boolean isStrictMatching() {
        return strictMatching;
    }

    public void setStrictMatching(Boolean strictMatching) {
        this.strictMatching = strictMatching;
    }
}
