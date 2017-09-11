package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.Order;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IMemberOrderManager
{
  public abstract Page pageOrders(int paramInt1, int paramInt2);
  
  public abstract Page pageOrders(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract Page pageGoods(int paramInt1, int paramInt2, String paramString);
  
  public abstract Order getOrder(int paramInt);
  
  public abstract Delivery getOrderDelivery(int paramInt);
  
  public abstract List listOrderLog(int paramInt);
  
  public abstract List listGoodsItems(int paramInt);
  
  public abstract List listGiftItems(int paramInt);
  
  public abstract boolean isBuy(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IMemberOrderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */