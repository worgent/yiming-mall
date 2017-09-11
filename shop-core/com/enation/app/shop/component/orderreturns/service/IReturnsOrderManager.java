package com.enation.app.shop.component.orderreturns.service;

import com.enation.app.shop.core.model.OrderLog;
import com.enation.app.shop.core.model.ReturnsOrder;
import com.enation.framework.database.Page;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IReturnsOrderManager
{
  public abstract void add(ReturnsOrder paramReturnsOrder, int paramInt1, int paramInt2, int[] paramArrayOfInt);
  
  public abstract String getSnByOrderSn(String paramString);
  
  public abstract ReturnsOrder get(Integer paramInteger);
  
  public abstract ReturnsOrder getByOrderSn(String paramString);
  
  public abstract List<ReturnsOrder> listMemberOrder();
  
  public abstract Page listAll(int paramInt1, int paramInt2);
  
  public abstract void updateState(Integer paramInteger, int paramInt);
  
  public abstract void updateItemChange(String paramString, int paramInt1, int paramInt2);
  
  public abstract void updateOrderItemsState(Integer paramInteger, int paramInt);
  
  public abstract void updateItemsState(Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract Double queryItemPrice(Integer paramInteger1, Integer paramInteger2);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void refuse(Integer paramInteger, String paramString, int paramInt);
  
  public abstract Page pageReturnOrder(int paramInt1, int paramInt2);
  
  public abstract void updateItemStatusByOrderidAndStatus(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int queryOrderidByReturnorderid(int paramInt);
  
  public abstract void updatePriceByItemid(int paramInt, double paramDouble);
  
  public abstract Page listAll(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void addLog(OrderLog paramOrderLog);
  
  public abstract Integer getOrderidByReturnid(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\orderreturns\service\IReturnsOrderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */