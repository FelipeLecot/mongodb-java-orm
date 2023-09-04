package org.mongodb.orm;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.NestedIOException;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.mongodb.exception.StatementException;
import org.mongodb.orm.builder.Nodelet;
import org.mongodb.orm.builder.NodeletParser;
import org.mongodb.orm.builder.statement.AggregateStatement;
import org.mongodb.orm.builder.statement.CommandStatement;
import org.mongodb.orm.builder.statement.DeleteStatement;
import org.mongodb.orm.builder.statement.GroupStatement;
import org.mongodb.orm.builder.statement.InsertStatement;
import org.mongodb.orm.builder.statement.MapReduceStatement;
import org.mongodb.orm.builder.statement.MappingStatement;
import org.mongodb.orm.builder.statement.SelectStatement;
import org.mongodb.orm.builder.statement.UpdateStatement;
import org.mongodb.orm.engine.Config;
import org.mongodb.orm.engine.config.MappingConfig;
import org.mongodb.util.NodeletUtils;

/**
 * MQL map config parser
 * @author yy
 */
public class MqlMapConfigParser {

  private NodeletParser parser = new NodeletParser( );

  private MqlMapConfiguration configuration = new MqlMapConfiguration();

  public MqlMapConfigParser() {
    parser.setValidation(true);
    parser.setEntityResolver(new MqlMapEntityResolver());

    addMapNodelets();
    addSelectNodelets();
    addUpdateNodelets();
    addInsertNodelets();
    addDeleteNodelets();
    addCommandNodelets();
    addGroupNodelets();
    addAggregateNodelets();
    addMapReduceNodelets();
  }

  /**
   * Parse resource.
   * 
   * @param inputStream
   * @throws NestedIOException
   */
  public void parse(InputStream inputStream) throws NestedIOException {
    parser.parse(inputStream);
  }

  /**
   * Validate mql mapping configuration.
   */
  public void validateMapping() {
    Map<String, Config> mappings = configuration.getMappings();
    for(Map.Entry<String, Config> entry : mappings.entrySet()) {
      MappingConfig value = (MappingConfig)entry.getValue();
      getMappingExtendsNode(value);
      if(value.getNodes().isEmpty()) {
        throw new StatementException("MQL mapping config can not empty property. Mapping id '"+value.getId()+"'");
      }
    }
  }
  
  /**
   * Return the mql configuration.
   */
  public MqlMapConfiguration getConfiguration() {
    return configuration;
  }
  
  private void addMapNodelets() {
    parser.addNodelet("/mql/mapping", new Nodelet() {
      @Override
      public void process(String namespace, Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addMapping(namespace, id, new MappingStatement(id).handler(namespace, node));
        if (logger.isDebugEnabled()) {
          logger.debug("Mql configuration load [mapping] '" + id + "' has finished.");
        }
      }
    });
  }

  private void addSelectNodelets() {
    parser.addNodelet("/mql/select", new Nodelet() {
      @Override
      public void process(String namespace, Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(namespace, id, new SelectStatement(id).handler(namespace, node));
        if (logger.isDebugEnabled()) {
          logger.debug("Mql configuration load [select] '" + id + "' has finished.");
        }
      }
    });
  }

  private void addUpdateNodelets() {
    parser.addNodelet("/mql/update", new Nodelet() {
      @Override
      public void process(String namespace, Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(namespace, id, new UpdateStatement(id).handler(namespace, node));
        if (logger.isDebugEnabled()) {
          logger.debug("Mql configuration load [update] '" + id + "' has finished.");
        }
      }
    });
  }

  private void addInsertNodelets() {
    parser.addNodelet("/mql/insert", new Nodelet() {
      @Override
      public void process(String namespace, Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(namespace, id, new InsertStatement(id).handler(namespace, node));
        if (logger.isDebugEnabled()) {
          logger.debug("Mql configuration load [insert] '" + id + "' has finished.");
        }
      }
    });
  }

  private void addDeleteNodelets() {
    parser.addNodelet("/mql/delete", new Nodelet() {
      @Override
      public void process(String namespace, Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(namespace, id, new DeleteStatement(id).handler(namespace, node));
        if (logger.isDebugEnabled()) {
          logger.debug("Mql configuration load [delete] '" + id + "' has finished.");
        }
      }
    });
  }

  private void addCommandNodelets() {
    parser.addNodelet("/mql/command", new Nodelet() {
      @Override
      public void process(String namespace, Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(namespace, id, new CommandStatement(id).handler(namespace, node));
        if (logger.isDebugEnabled()) {
          logger.debug("Mql configuration load [command] '" + id + "' has finished.");
        }
      }
    });
  }

  private void addGroupNodelets() {
    parser.addNodelet("/mql/group", new Nodelet() {
      @Override
      public void process(String namespace, Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(namespace, id, new GroupStatement(id).handler(namespace, node));
        if (logger.isDebugEnabled()) {
          logger.debug("Mql configuration load [group] '" + id + "' has finished.");
        }
      }
    });
  }

  private void addAggregateNodelets() {
    parser.addNodelet("/mql/aggregate", new Nodelet() {
      @Override
      public void process(String namespace, Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(namespace, id, new AggregateStatement(id).handler(namespace, node));
        if (logger.isDebugEnabled()) {
          logger.debug("Mql configuration load [aggregate] '" + id + "' has finished.");
        }
      }
    });
  }
  
  private void addMapReduceNodelets() {
    parser.addNodelet("/mql/mapreduce", new Nodelet() {
      @Override
      public void process(String namespace, Node node) throws Exception {
        Properties attributes = NodeletUtils.parseAttributes(node);
        String id = attributes.getProperty("id");

        configuration.addConfig(namespace, id, new MapReduceStatement(id).handler(namespace, node));
        if (logger.isDebugEnabled()) {
          logger.debug("Mql configuration load [mapreduce] '" + id + "' has finished.");
        }
      }
    });
  }
  
  private void getMappingExtendsNode(MappingConfig target) {
    String extend = target.getExtend();
    if(extend != null) {
      MappingConfig mc = (MappingConfig)configuration.getMapping(target.getNamespace(), extend);
      if(mc == null) {
        throw new StatementException("This extends mapping '"+extend+"' not found. Mapping id '"+target.getId()+"'");
      }
      
      if(mc.getId().equals(target.getId())) {
        throw new StatementException("This extends mapping can not inherit their own. Mapping id '"+target.getId()+"'");
      }
      getMappingExtendsNode(mc);
      target.merge(mc.getNodes());
      if(target.getTypeHandler() == null) {
        target.setTypeHandler(mc.getTypeHandler());
      }
    }
  }

  /**
   * Offline entity resolver for the mongodb DTDs
   */
  private static class MqlMapEntityResolver implements EntityResolver {

    private static final String MQL_MAP_DTD = "https://raw.githubusercontent.com/yuxiangping/repository/master/mongodb-mql.dtd";

    private static final Map<String, String> doctypeMap = new HashMap<String, String>(2);

    static {
      doctypeMap.put("mongodb-mql.dtd".toUpperCase(), MQL_MAP_DTD);
      doctypeMap.put("-//eason.xp.yu@gmail.com//DTD MONGODB QL Config 1.0//zh-CN".toUpperCase(), MQL_MAP_DTD);
    }

    /**
     * Converts a public DTD into a local one
     * 
     * @param publicId Unused but required by EntityResolver interface
     * @param systemId The DTD that is being requested
     * @return The InputSource for the DTD
     * @throws SAXException If anything goes wrong
     */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
      if (publicId != null) {
        publicId = publicId.toUpperCase();
      }

      if (systemId != null) {
        systemId = systemId.toUpperCase();
      }

      InputSource source = null;
      try {
        String path = (String) doctypeMap.get(publicId);
        source = getInputSource(path);
        if (source == null) {
          path = (String) doctypeMap.get(systemId);
          source = getInputSource(path);
        }
        
        if (source == null) {
          throw new SAXException("Resolve entiry error. PID:" + publicId + ", SID:" + systemId);
        }
      } catch (Exception e) {
        throw new SAXException("Resolve entiry error. PID:"+publicId+", SID:"+systemId, e);
      }
      return source;
    }

    private InputSource getInputSource(String path) throws Exception {
      InputSource source = null;
      if (path != null) {
        InputStream in = ClassLoader.getSystemResourceAsStream(path);
        source = new InputSource(in);
      }
      
      if(source.getByteStream() == null) {
        URL location = new URL(path);
        URLConnection connect = location.openConnection();
        source = new InputSource(connect.getInputStream());
      }
      return source;
    }
  }
  
}
