package com.enation.framework.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public abstract interface IRowMapperColumnFilter
{
  public abstract void filter(Map paramMap, ResultSet paramResultSet)
    throws SQLException;
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\impl\IRowMapperColumnFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */