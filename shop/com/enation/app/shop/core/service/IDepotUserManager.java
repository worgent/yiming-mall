package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.DepotUser;
import java.util.List;

public abstract interface IDepotUserManager
{
  public abstract void add(DepotUser paramDepotUser);
  
  public abstract void edit(DepotUser paramDepotUser);
  
  public abstract void delete(int paramInt);
  
  public abstract DepotUser get(int paramInt);
  
  public abstract List<DepotUser> listByDepotId(int paramInt);
  
  public abstract List<DepotUser> listByRoleId(int paramInt);
  
  public abstract List<DepotUser> listByRoleId(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IDepotUserManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */