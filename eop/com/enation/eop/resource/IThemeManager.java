package com.enation.eop.resource;

import com.enation.eop.resource.model.Theme;
import java.util.List;

public abstract interface IThemeManager
{
  public abstract void addBlank(Theme paramTheme);
  
  public abstract Integer add(Theme paramTheme, boolean paramBoolean);
  
  public abstract List<Theme> list();
  
  public abstract List<Theme> list(int paramInt);
  
  public abstract Theme getTheme(Integer paramInteger);
  
  public abstract void clean();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IThemeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */