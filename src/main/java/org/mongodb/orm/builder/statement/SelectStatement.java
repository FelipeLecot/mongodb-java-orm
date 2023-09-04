package org.mongodb.orm.builder.statement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.mongodb.constant.ORM;
import org.mongodb.exception.StatementException;
import org.mongodb.orm.engine.Config;
import org.mongodb.orm.engine.config.SelectConfig;
import org.mongodb.orm.engine.entry.Entry;
import org.mongodb.orm.engine.entry.NodeEntry;
import org.mongodb.orm.engine.type.TypeHandlerFactory;
import org.mongodb.util.NodeletUtils;

/**
 * Transform SQL file for ORM, "select" node statement.
 * @author yy
 */
public class SelectStatement extends BaseStatement implements StatementHandler {

  private String id;

  /**
   * Select node analyzes.
   */
  @SuppressWarnings("serial")
  private final Map<String, NodeAnalyze<SelectConfig>> analyzes = new HashMap<String, NodeAnalyze<SelectConfig>>() {
    {
      put(ORM.NODE_QUERY, new QueryNodeAnalyze());
      put(ORM.NODE_FIELD, new FieldNodeAnalyze());
      put(ORM.NODE_ORDER, new OrderNodeAnalyze());
    }
  };

  public SelectStatement(String id) {
    this.id = id;
  }

  @Override
  public Config handler(String namespace, Node node) {
    Properties attributes = NodeletUtils.parseAttributes(node);
    String collection = attributes.getProperty(ORM.TAG_DB_COLLECTION);

    SelectConfig select = new SelectConfig(namespace, id, collection);

    NodeList childNodes = node.getChildNodes();
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node n = childNodes.item(i);
      String nodeName = n.getNodeName();

      NodeAnalyze<SelectConfig> analyze = analyzes.get(nodeName);
      if (analyze == null) {
        throw new StatementException("Error child node for 'select', id '" + id + "'.");
      }
      analyze.analyze(select, n);
    }

    logger.debug("Mongo config '" + id + "' has inited.");
    return select;
  }

  private class QueryNodeAnalyze implements NodeAnalyze<SelectConfig> {
    @Override
    public void analyze(SelectConfig config, Node node) {
      if (config.getQuery() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'query' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getQuery(config.getId(), node);
      config.setQuery(entry);
    }
  }

  private class FieldNodeAnalyze implements NodeAnalyze<SelectConfig> {
    @Override
    public void analyze(SelectConfig config, Node node) {
      if (config.getField() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'field' is already exists in '" + config.getId() + "'.");
      }

      NodeEntry entry = getField(config.getId(), node);
      config.setField(entry);
    }
  }

  private class OrderNodeAnalyze implements NodeAnalyze<SelectConfig> {
    @Override
    public void analyze(SelectConfig config, Node node) {
      if (config.getOrder() != null) {
        throw new StatementException("Alias name conflict occurred.  The node 'order' is already exists in '" + config.getId() + "'.");
      }

      Properties attributes = NodeletUtils.parseAttributes(node);
      String clzz = attributes.getProperty(ORM.ORM_CLASS);

      Class<?> clazz = null;
      if (clzz != null) {
        try {
          clazz = Class.forName(clzz);
        } catch (ClassNotFoundException e) {
          throw new StatementException("Class not found by name '" + clzz + "' for node 'order' in '" + config.getId() + "'.", e);
        }
      }

      NodeList childNodes = node.getChildNodes();
      List<Entry> entrys = new ArrayList<Entry>(childNodes.getLength());
      for (int i = 0; i < childNodes.getLength(); i++) {
        Node n = childNodes.item(i);
        Properties childAttributes = NodeletUtils.parseAttributes(n);
        String column = childAttributes.getProperty(ORM.TAG_CLUMN);
        String value = childAttributes.getProperty(ORM.TAG_VALUE);
        String name = attributes.getProperty(ORM.TAG_NAME);

        Entry entry = new Entry();
        entry.setColumn(column);
        entry.setName(name);
        entry.setValue(value == null ? 1 : config.getOrder(column, value));

        entrys.add(entry);
      }

      NodeEntry orderEntry = new NodeEntry();
      orderEntry.setExecutor(orderExecutor);
      orderEntry.setTypeHandler(TypeHandlerFactory.getTypeHandler(clazz));
      orderEntry.setClazz(clazz);
      orderEntry.setNodeMappings(entrys);
      config.setOrder(orderEntry);
    }
  }

}
