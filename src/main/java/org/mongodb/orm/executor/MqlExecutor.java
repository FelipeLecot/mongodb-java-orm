package org.mongodb.orm.executor;

import org.mongodb.exception.MongoORMException;
import org.mongodb.orm.MqlMapConfiguration;
import org.mongodb.orm.engine.entry.NodeEntry;

/**
 * Mql executor 
 * @author yy
 */
public interface MqlExecutor<T> {
    
    public T parser(String namespace, MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException;
    
    /**
     * SqlExecutor callback 
     * @author yy
     */
    public static interface CallBack<T> {
        T callback(String namespace, MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException;
    }
    
}