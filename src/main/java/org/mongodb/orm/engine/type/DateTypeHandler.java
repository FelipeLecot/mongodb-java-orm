package org.mongodb.orm.engine.type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mongodb.exception.MongoORMException;

/**
 * Date implementation of TypeHandler
 * @author yy
 */
public class DateTypeHandler implements TypeHandler<Date>, ColumnHandler<Date> {

  private static final String DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";

  @Override
  public Object getParameter(String name, Date instance) {
    return instance;
  }

  @Override
  public Date getResult(String name, Object instance, Object value) {
    if (value instanceof Number) {
      return new Date(((Number) value).longValue());
    }

    if (value instanceof Date) {
      return (Date) value;
    }

    try {
      return new SimpleDateFormat(DATE_FROMAT).parse(value.toString());
    } catch (ParseException pex) {
      logger.error("Resovle column value type has exception. Cause:", pex);
      throw new MongoORMException("Resovle column value type has exception. Cause:", pex);
    }
  }

  @Override
  public Date resovleColumn(Object value) {
    if (value instanceof Number) {
      return new Date(((Number) value).longValue());
    }

    if (value instanceof Date) {
      return (Date) value;
    }

    try {
      return new SimpleDateFormat(DATE_FROMAT).parse(value.toString());
    } catch (ParseException pex) {
      logger.error("Resovle column value type has exception. Cause:", pex);
      throw new MongoORMException("Resovle column value type has exception. Cause:", pex);
    }
  }

  @Override
  public Object resovleValue(Date target) {
    return target;
  }

}
