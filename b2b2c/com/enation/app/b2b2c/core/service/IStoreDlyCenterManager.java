package com.enation.app.b2b2c.core.service;

import com.enation.app.b2b2c.core.model.StoreDlyCenter;
import java.util.List;

public abstract interface IStoreDlyCenterManager
{
  public abstract List getDlyCenterList(Integer paramInteger);
  
  public abstract void addDlyCenter(StoreDlyCenter paramStoreDlyCenter);
  
  public abstract void editDlyCenter(StoreDlyCenter paramStoreDlyCenter);
  
  public abstract StoreDlyCenter getDlyCenter(Integer paramInteger1, Integer paramInteger2);
  
  public abstract void delete(Integer paramInteger);
  
  public abstract void site_default(Integer paramInteger1, Integer paramInteger2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\IStoreDlyCenterManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */