package com.enation.app.base.core.plugin.database;

import java.sql.ResultSet;
import java.util.Map;

public abstract interface IColumnFilterEvent
{
  public abstract void filter(Map paramMap, ResultSet paramResultSet);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\plugin\database\IColumnFilterEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */