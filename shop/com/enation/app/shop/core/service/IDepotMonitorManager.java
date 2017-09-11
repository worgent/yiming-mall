package com.enation.app.shop.core.service;

import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IDepotMonitorManager
{
  public abstract List getTaskList();
  
  public abstract List getAllocationList();
  
  public abstract List getSendList();
  
  public abstract int getTotalByStatus(int paramInt);
  
  public abstract List depotidDepotByGoodsid(int paramInt1, int paramInt2);
  
  public abstract List searchOrderSalesAmout(int paramInt1, int paramInt2);
  
  public abstract List searchOrderSaleNumber(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Page searchStoreLog(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IDepotMonitorManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */