package com.enation.app.base.core.service;

import com.enation.app.base.core.model.SiteMenu;
import java.util.List;

public abstract interface ISiteMenuManager
{
  public abstract void add(SiteMenu paramSiteMenu);
  
  public abstract SiteMenu get(Integer paramInteger);
  
  public abstract void edit(SiteMenu paramSiteMenu);
  
  public abstract void delete(Integer paramInteger);
  
  public abstract List<SiteMenu> list(Integer paramInteger);
  
  public abstract void updateSort(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\ISiteMenuManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */