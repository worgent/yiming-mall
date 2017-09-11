package com.enation.eop.resource;

import com.enation.eop.resource.model.ThemeUri;
import java.util.List;
import java.util.Map;

public abstract interface IThemeUriManager
{
  public abstract ThemeUri get(Integer paramInteger);
  
  public abstract void add(ThemeUri paramThemeUri);
  
  public abstract List<ThemeUri> list(Map paramMap);
  
  public abstract ThemeUri getPath(String paramString);
  
  public abstract void edit(List<ThemeUri> paramList);
  
  public abstract void edit(ThemeUri paramThemeUri);
  
  public abstract void delete(int paramInt);
  
  public abstract void clean();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IThemeUriManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */