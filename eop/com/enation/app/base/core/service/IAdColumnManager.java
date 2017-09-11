package com.enation.app.base.core.service;

import com.enation.app.base.core.model.AdColumn;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IAdColumnManager
{
  public abstract void updateAdvc(AdColumn paramAdColumn);
  
  public abstract AdColumn getADcolumnDetail(Long paramLong);
  
  public abstract void addAdvc(AdColumn paramAdColumn);
  
  public abstract void delAdcs(Integer[] paramArrayOfInteger);
  
  public abstract Page pageAdvPos(int paramInt1, int paramInt2);
  
  public abstract List listAllAdvPos();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\IAdColumnManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */