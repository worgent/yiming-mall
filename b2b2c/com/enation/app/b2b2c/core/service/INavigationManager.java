package com.enation.app.b2b2c.core.service;

import com.enation.app.b2b2c.core.model.Navigation;
import java.util.List;

public abstract interface INavigationManager
{
  public abstract List getNavicationList(Integer paramInteger);
  
  public abstract void save(Navigation paramNavigation);
  
  public abstract void edit(Navigation paramNavigation);
  
  public abstract void delete(Integer paramInteger);
  
  public abstract Navigation getNavication(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\INavigationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */