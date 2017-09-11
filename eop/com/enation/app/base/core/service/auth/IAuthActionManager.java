package com.enation.app.base.core.service.auth;

import com.enation.app.base.core.model.AuthAction;
import java.util.List;

public abstract interface IAuthActionManager
{
  public abstract AuthAction get(int paramInt);
  
  public abstract List<AuthAction> list();
  
  public abstract int add(AuthAction paramAuthAction);
  
  public abstract void edit(AuthAction paramAuthAction);
  
  public abstract void delete(int paramInt);
  
  public abstract void addMenu(int paramInt, Integer[] paramArrayOfInteger);
  
  public abstract void deleteMenu(int paramInt, Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\auth\IAuthActionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */