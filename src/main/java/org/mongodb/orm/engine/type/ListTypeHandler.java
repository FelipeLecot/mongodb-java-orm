package org.mongodb.orm.engine.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mongodb.exception.MongoORMException;

/**
 * List implementation of TypeHandler
 * @author yy
 */
public class ListTypeHandler implements TypeHandler<List<?>> {

  private Class<?> clazz;
  
  public ListTypeHandler(Class<?> clazz) {
    this.clazz = clazz;
  }
  
  @Override
  public Object getParameter(String name, List<?> instance) {
    return instance;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public List<?> getResult(String name, Object instance, Object value) {
    try {
      List result = null;
      if(clazz.isInterface()) {
        result = new ArrayList<Object>();  
      } else {
        result = (List)clazz.newInstance();
      }

      if (value instanceof Collection) {
        result.addAll((Collection)value);
      } else {
        result.add(value);
      }
      return result;
    } catch (Exception ex) {
      throw new MongoORMException("Get result from "+instance.getClass()+" has error. Target property is "+name, ex);
    }
  }
  
}
