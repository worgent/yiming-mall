package com.enation.app.shop.core.service;

import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IWarnTaskManager
{
  public abstract List<Map> listColor(Integer paramInteger);
  
  public abstract Page listdepot(Integer paramInteger, String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Map listTask(Integer paramInteger);
  
  public abstract void saveTask(Map paramMap);
  
  public abstract Page listAll(Integer paramInteger1, Integer paramInteger2);
  
  public abstract Integer getProductId(Integer paramInteger);
  
  public abstract void updateTask(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IWarnTaskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */