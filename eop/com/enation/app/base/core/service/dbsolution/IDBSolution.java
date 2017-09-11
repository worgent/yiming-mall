package com.enation.app.base.core.service.dbsolution;

import java.sql.Connection;

public abstract interface IDBSolution
{
  public abstract void setConnection(Connection paramConnection);
  
  public abstract boolean dbImport(String paramString);
  
  public abstract boolean dbExport(String[] paramArrayOfString, String paramString);
  
  public abstract String dbExport(String[] paramArrayOfString, boolean paramBoolean);
  
  public abstract String dbSaasExport(String[] paramArrayOfString, boolean paramBoolean, int paramInt1, int paramInt2);
  
  public abstract int dropTable(String paramString);
  
  public abstract boolean dbSaasImport(String paramString, int paramInt1, int paramInt2);
  
  public abstract void setPrefix(String paramString);
  
  public abstract String toLocalType(String paramString1, String paramString2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\dbsolution\IDBSolution.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */