package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.DlyArea;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IAreaManager
{
  public abstract void saveAdd(String paramString);
  
  public abstract void saveEdit(Integer paramInteger, String paramString);
  
  public abstract List getAll();
  
  public abstract Page pageArea(String paramString, int paramInt1, int paramInt2);
  
  public abstract void delete(String paramString);
  
  public abstract DlyArea getDlyAreaById(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IAreaManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */