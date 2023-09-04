package org.mongodb.orm.engine.entry;

import java.io.Serializable;
import java.util.List;

import org.mongodb.orm.MqlMapConfiguration;
import org.mongodb.orm.engine.type.TypeHandler;
import org.mongodb.orm.executor.MqlExecutor;

/**
 * Xml node entry.
 * @author yy
 */
public class NodeEntry implements Serializable {

  private static final long serialVersionUID = -6974784211299835940L;

  /**
   * Mapping id.
   */
  private String mappingId;

  /**
   * Node parameter class type.
   */
  private Class<?> clazz;

  /**
   * Node elements mapping entry.
   */
  private List<Entry> nodeMappings;
  
  /**
   * Getting data into, and out of a mapped statement.
   */
  private TypeHandler<?> typeHandler;
  
  /**
   * Mql node executor
   */
  private MqlExecutor<?> executor;
  
  public Object executorNode(String namespace, MqlMapConfiguration configuration, Object target) {
    return executor.parser(namespace, configuration, this, target);
  }
  
  public String getMappingId() {
    return mappingId;
  }

  public void setMappingId(String mappingId) {
    this.mappingId = mappingId;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public void setClazz(Class<?> clazz) {
    this.clazz = clazz;
  }

  public List<Entry> getNodeMappings() {
    return nodeMappings;
  }

  public void setNodeMappings(List<Entry> nodeMappings) {
    this.nodeMappings = nodeMappings;
  }

  public TypeHandler<?> getTypeHandler() {
    return typeHandler;
  }

  public void setTypeHandler(TypeHandler<?> typeHandler) {
    this.typeHandler = typeHandler;
  }

  public void setExecutor(MqlExecutor<?> executor) {
    this.executor = executor;
  }

}
