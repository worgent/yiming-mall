package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Logi;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface ILogiManager
{
  public abstract void saveAdd(Logi paramLogi);
  
  public abstract void saveEdit(Logi paramLogi);
  
  public abstract Page pageLogi(String paramString, Integer paramInteger1, Integer paramInteger2);
  
  public abstract List list();
  
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  public abstract Logi getLogiById(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\ILogiManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */