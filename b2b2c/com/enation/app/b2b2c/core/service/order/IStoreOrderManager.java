package com.enation.app.b2b2c.core.service.order;

import com.enation.app.b2b2c.core.model.order.StoreOrder;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IStoreOrderManager
{
  public abstract Page storeOrderList(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Map paramMap);
  
  public abstract List<StoreOrder> storeOrderList(Integer paramInteger);
  
  public abstract StoreOrder get(Integer paramInteger);
  
  public abstract boolean saveShipInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt);
  
  public abstract Page pageOrders(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract StoreOrder get(String paramString);
  
  public abstract int getStoreOrderNum(int paramInt);
  
  public abstract Page listOrder(Map paramMap, int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract Map getStatusJson();
  
  public abstract Map getpPayStatusJson();
  
  public abstract Map getShipJson();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\order\IStoreOrderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */