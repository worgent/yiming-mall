package com.enation.app.cms.core.service;

import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IDataManager
{
  public abstract Page search(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2);
  
  public abstract Page search(int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean);
  
  public abstract List search(int paramInt, String paramString);
  
  public abstract List search(int paramInt, String paramString, boolean paramBoolean);
  
  public abstract void add(Integer paramInteger1, Integer paramInteger2);
  
  public abstract void edit(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);
  
  public abstract void delete(Integer paramInteger1, Integer paramInteger2);
  
  public abstract Page list(Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract Page list(Integer paramInteger1, int paramInt1, int paramInt2, Integer paramInteger2);
  
  public abstract void importdata(Integer paramInteger, Integer[] paramArrayOfInteger);
  
  public abstract List list(Integer paramInteger);
  
  public abstract Page listAll(Integer paramInteger, String paramString, int paramInt1, int paramInt2);
  
  public abstract Page listAll(Integer paramInteger, String paramString1, String paramString2, boolean paramBoolean, int paramInt1, int paramInt2);
  
  public abstract List listRelated(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString);
  
  public abstract Map get(Integer paramInteger1, Integer paramInteger2, boolean paramBoolean);
  
  public abstract void updateSort(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Integer paramInteger);
  
  public abstract void updateHit(Integer paramInteger1, Integer paramInteger2);
  
  public abstract Map census();
  
  public abstract int getNextId(Integer paramInteger1, Integer paramInteger2);
  
  public abstract int getPrevId(Integer paramInteger1, Integer paramInteger2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\service\IDataManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */