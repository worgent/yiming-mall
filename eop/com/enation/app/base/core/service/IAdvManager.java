package com.enation.app.base.core.service;

import com.enation.app.base.core.model.Adv;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IAdvManager
{
  public abstract void updateAdv(Adv paramAdv);
  
  public abstract Adv getAdvDetail(Long paramLong);
  
  public abstract void addAdv(Adv paramAdv);
  
  public abstract void delAdvs(Integer[] paramArrayOfInteger);
  
  public abstract Page pageAdv(String paramString, int paramInt1, int paramInt2);
  
  public abstract List listAdv(Long paramLong);
  
  public abstract Page search(Long paramLong, String paramString1, int paramInt1, int paramInt2, String paramString2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\IAdvManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */