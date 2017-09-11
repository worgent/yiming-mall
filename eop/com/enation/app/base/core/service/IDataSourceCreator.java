package com.enation.app.base.core.service;

import javax.sql.DataSource;

public abstract interface IDataSourceCreator
{
  public abstract DataSource createDataSource(String paramString1, String paramString2, String paramString3, String paramString4);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\IDataSourceCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */