package com.enation.app.shop.component.receipt.service;

import com.enation.app.shop.component.receipt.Receipt;

public abstract interface IReceiptManager
{
  public abstract void add(Receipt paramReceipt);
  
  public abstract Receipt getByOrderid(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\receipt\service\IReceiptManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */