package com.enation.app.shop.core.service;

import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IFavoriteManager
{
  public abstract Page list(int paramInt1, int paramInt2);
  
  public abstract Page list(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List list();
  
  public abstract void delete(int paramInt);
  
  public abstract void add(Integer paramInteger);
  
  public abstract int getCount(Integer paramInteger1, Integer paramInteger2);
  
  public abstract int getCount(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IFavoriteManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */