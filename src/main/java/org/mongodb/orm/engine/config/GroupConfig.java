package org.mongodb.orm.engine.config;

import org.mongodb.orm.engine.Config;
import org.mongodb.orm.engine.entry.NodeEntry;
import org.mongodb.orm.engine.entry.Script;

/**
 * XML Group config
 * @author yy
 */
public class GroupConfig implements Config {

  private static final long serialVersionUID = 1289646863161628083L;

  /**
   * ORM config namespace.
   */
  private String namespace;
  
  /**
   * ORM group config id.
   */
  private String id;
  
  /**
   * Mongo db collection.
   */
  private String collection;
  
  /**
   * Group key.
   */
  private NodeEntry key;
  
  /**
   * Group keyf
   */
  private Script keyf;
  
  /**
   * Group condition.
   */
  private NodeEntry condition;
  
  /**
   * Group initial.
   */
  private NodeEntry initial;
  
  /**
   * Group reduce.
   */
  private Script reduce;
  
  /**
   * Group finalize.
   */
  private Script finalize;
  
  /**
   * Group return field.
   */
  private NodeEntry field;

  public GroupConfig(String namespace, String id, String collection) {
    this.namespace = namespace;
    this.id = id;
    this.collection = collection;
  }

  public NodeEntry getKey() {
    return key;
  }

  public void setKey(NodeEntry key) {
    this.key = key;
  }

  public Script getKeyf() {
    return keyf;
  }

  public void setKeyf(Script keyf) {
    this.keyf = keyf;
  }

  public NodeEntry getCondition() {
    return condition;
  }

  public void setCondition(NodeEntry condition) {
    this.condition = condition;
  }

  public NodeEntry getInitial() {
    return initial;
  }

  public void setInitial(NodeEntry initial) {
    this.initial = initial;
  }

  public Script getReduce() {
    return reduce;
  }

  public void setReduce(Script reduce) {
    this.reduce = reduce;
  }

  public Script getFinalize() {
    return finalize;
  }

  public void setFinalize(Script finalize) {
    this.finalize = finalize;
  }

  public NodeEntry getField() {
    return field;
  }

  public void setField(NodeEntry field) {
    this.field = field;
  }

  public String getId() {
    return id;
  }

  public String getCollection() {
    return collection;
  }
  
  public String getNamespace() {
    return namespace;
  }

}
