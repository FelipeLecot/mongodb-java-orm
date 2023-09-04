package org.mongodb.orm.executor.strategy;

import java.util.List;

import org.mongodb.orm.MqlMapConfiguration;
import org.mongodb.orm.engine.entry.Entry;
import org.mongodb.orm.engine.entry.NodeEntry;
import org.mongodb.orm.executor.MqlExecutor.CallBack;
import org.mongodb.util.ObjectUtils;

/**
 * Child node strategy 
 * @author yy
 */
public class NodeStrategy implements Strategy {

  @Override
  public void doStrategy(String namespace, MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
    Entry entry = context.getEntry();
    List<NodeEntry> nodes = entry.getNodes();
    if(!ObjectUtils.isEmpty(nodes)) {
      int count = nodes.size();
      Object[] array = new Object[count];
      CallBack<?> callback = context.getCallback();
      for(int i=0; i<count; i++) {
        array[i] = callback.callback(namespace, configuration, nodes.get(i), context.getTarget());
      }
      
      if(count == 1) {
        context.setValue(array[0]);
      } else {
        context.setValue(array);
      }
    }
    chain.doStrategy(namespace, configuration, context);
  }

}
