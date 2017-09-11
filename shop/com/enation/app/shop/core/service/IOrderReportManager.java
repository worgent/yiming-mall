package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.DeliveryItem;
import com.enation.app.shop.core.model.PaymentDetail;
import com.enation.app.shop.core.model.PaymentLog;
import com.enation.app.shop.core.model.RefundLog;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IOrderReportManager
{
  public abstract Page listPayment(Map paramMap, int paramInt1, int paramInt2, String paramString);
  
  public abstract Page listRefund(int paramInt1, int paramInt2, String paramString);
  
  public abstract Page listShipping(int paramInt1, int paramInt2, String paramString);
  
  public abstract Page listReturned(int paramInt1, int paramInt2, String paramString);
  
  public abstract PaymentLog getPayment(Integer paramInteger);
  
  public abstract RefundLog getRefund(Integer paramInteger);
  
  public abstract Delivery getDelivery(Integer paramInteger);
  
  public abstract List<Delivery> getDeliveryList(int paramInt);
  
  public abstract List<DeliveryItem> listDeliveryItem(Integer paramInteger);
  
  public abstract List<PaymentLog> listPayLogs(Integer paramInteger);
  
  public abstract List<RefundLog> listRefundLogs(Integer paramInteger);
  
  public abstract List listDelivery(Integer paramInteger1, Integer paramInteger2);
  
  public abstract Page listAllocation(int paramInt1, int paramInt2);
  
  public abstract Page listAllocation(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract double getPayMoney(Integer paramInteger);
  
  public abstract void addPayMentDetail(PaymentDetail paramPaymentDetail);
  
  public abstract Integer getPaymentLogId(Integer paramInteger);
  
  public abstract List<PaymentDetail> listPayMentDetail(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IOrderReportManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */