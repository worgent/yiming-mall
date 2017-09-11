package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.AdvanceLogs;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IAdvanceLogsManager
{
  public abstract Page pageAdvanceLogs(int paramInt1, int paramInt2);
  
  public abstract void add(AdvanceLogs paramAdvanceLogs);
  
  public abstract List listAdvanceLogsByMemberId(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IAdvanceLogsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */