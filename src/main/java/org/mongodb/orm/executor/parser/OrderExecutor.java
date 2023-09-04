package org.mongodb.orm.executor.parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mongodb.exception.MongoORMException;
import org.mongodb.orm.MqlMapConfiguration;
import org.mongodb.orm.engine.config.MappingConfig;
import org.mongodb.orm.engine.entry.Entry;
import org.mongodb.orm.engine.entry.NodeEntry;
import org.mongodb.orm.engine.type.TypeHandler;
import org.mongodb.orm.executor.MqlExecutor;
import org.mongodb.orm.executor.strategy.StrategyChain;
import org.mongodb.orm.executor.strategy.StrategyContext;
import org.mongodb.util.ObjectUtils;

/**
 * Mongodb MQL order executor.
 * 
 * <select>
 *     <order>
 *         ......
 *     </order>
 * </select> 
 * 
 * @author yy
 */
public class  OrderExecutor implements MqlExecutor<Map<String, Object>> {

  @Override
  public Map<String, Object> parser(String namespace, MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
    List<Entry> entrys = entry.getNodeMappings();
    TypeHandler<?> typeHandler = entry.getTypeHandler();
    String mappingId = entry.getMappingId();
    if (mappingId != null) {
      MappingConfig mapping = (MappingConfig) configuration.getMapping(namespace, mappingId);
      entrys = mapping.getNodes();
      typeHandler = mapping.getTypeHandler();
    }
    
    Map<String, Object> order = new LinkedHashMap<String, Object>();
    for (Entry ety : entrys) {
      StrategyContext context = new StrategyContext(ety, target, callback);
      context.setTypeHandler(typeHandler);

      new StrategyChain().doStrategy(namespace, configuration, context);

      Object value = context.getValue();
      if(!ObjectUtils.checkValueNull(value, ety)) {
        String column = ety.getColumn();
        order.put(column, value);
      }
    }
    return order;
  }
  
  CallBack<Map<String, Object>> callback = new CallBack<Map<String, Object>>() {
    @Override
    public Map<String, Object> callback(String namespace, MqlMapConfiguration configuration, NodeEntry entry, Object target) throws MongoORMException {
      return parser(namespace, configuration, entry, target);
    }
  };
  
}
