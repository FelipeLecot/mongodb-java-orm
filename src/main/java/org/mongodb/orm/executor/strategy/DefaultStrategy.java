package org.mongodb.orm.executor.strategy;

import org.mongodb.orm.MqlMapConfiguration;
import org.mongodb.orm.engine.entry.Entry;

/**
 * Default strategy  
 * @author yy
 */
public class DefaultStrategy implements Strategy {

  @Override
  public void doStrategy(String namespace, MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
    Entry entry = context.getEntry();
    Object value = entry.getValue();
    if(value != null) {
        context.setValue(value);
    }
    chain.doStrategy(namespace, configuration, context);
  }

}
