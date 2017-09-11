package com.enation.framework.component;

import java.util.List;

public abstract interface IComponentManager
{
  public abstract void startComponents();
  
  public abstract void saasStartComponents();
  
  public abstract List<ComponentView> list();
  
  public abstract void install(String paramString);
  
  public abstract void unInstall(String paramString);
  
  public abstract void start(String paramString);
  
  public abstract void stop(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\component\IComponentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */