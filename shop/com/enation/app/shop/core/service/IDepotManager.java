package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Depot;
import java.util.List;

public abstract interface IDepotManager
{
  public abstract void add(Depot paramDepot);
  
  public abstract void update(Depot paramDepot);
  
  public abstract Depot get(int paramInt);
  
  public abstract String delete(int paramInt);
  
  public abstract List<Depot> list();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IDepotManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */