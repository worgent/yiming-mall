package com.enation.app.base.core.service;

import com.enation.app.base.core.model.MultiSite;
import java.util.List;

public abstract interface IMultiSiteManager
{
  public abstract void open(String paramString);
  
  public abstract void close();
  
  public abstract void add(MultiSite paramMultiSite);
  
  public abstract void update(MultiSite paramMultiSite);
  
  public abstract void delete(int paramInt);
  
  public abstract List list();
  
  public abstract MultiSite get(int paramInt);
  
  public abstract MultiSite get(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\IMultiSiteManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */