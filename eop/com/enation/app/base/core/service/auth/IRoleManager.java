package com.enation.app.base.core.service.auth;

import com.enation.app.base.core.model.Role;
import java.util.List;

public abstract interface IRoleManager
{
  public abstract List<Role> list();
  
  public abstract void add(Role paramRole, int[] paramArrayOfInt);
  
  public abstract void edit(Role paramRole, int[] paramArrayOfInt);
  
  public abstract void delete(int paramInt);
  
  public abstract Role get(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\auth\IRoleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */