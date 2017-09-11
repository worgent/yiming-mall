package com.enation.eop.resource;

import com.enation.eop.resource.model.AdminTheme;
import java.util.List;

public abstract interface IAdminThemeManager
{
  public abstract Integer add(AdminTheme paramAdminTheme, boolean paramBoolean);
  
  public abstract List<AdminTheme> list();
  
  public abstract AdminTheme get(Integer paramInteger);
  
  public abstract void clean();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\IAdminThemeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */