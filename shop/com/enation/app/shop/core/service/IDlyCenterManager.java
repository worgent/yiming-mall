package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.DlyCenter;
import java.util.List;

public abstract interface IDlyCenterManager
{
  public abstract List<DlyCenter> list();
  
  public abstract DlyCenter get(Integer paramInteger);
  
  public abstract void add(DlyCenter paramDlyCenter);
  
  public abstract void edit(DlyCenter paramDlyCenter);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IDlyCenterManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */