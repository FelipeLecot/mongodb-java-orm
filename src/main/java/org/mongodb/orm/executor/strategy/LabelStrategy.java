package org.mongodb.orm.executor.strategy;

import org.mongodb.constant.ORM;
import org.mongodb.orm.MqlMapConfiguration;
import org.mongodb.orm.engine.entry.Entry;

/**
 * Label strategy  ${value} 
 * @author yy
 */
public class LabelStrategy implements Strategy {

  @Override
  public void doStrategy(String namespace, MqlMapConfiguration configuration, StrategyContext context, StrategyChain chain) {
    Entry entry = context.getEntry();
    Object value = entry.getValue();
    if(value != null && ORM.LABEL_VALUE.equals(value)) {
      context.setValue(context.getTarget());
    }
    chain.doStrategy(namespace, configuration, context);
  }

}
