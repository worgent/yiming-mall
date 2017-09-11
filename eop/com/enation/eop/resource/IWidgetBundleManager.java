package com.enation.eop.resource;

import com.enation.eop.resource.model.WidgetBundle;
import java.util.List;

public abstract interface IWidgetBundleManager
{
  public abstract void add(WidgetBundle paramWidgetBundle);
  
  public abstract List<WidgetBundle> getWidgetbundleList();
  
  public abstract WidgetBundle getWidgetBundle(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IWidgetBundleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */