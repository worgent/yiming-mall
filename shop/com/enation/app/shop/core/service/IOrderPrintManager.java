package com.enation.app.shop.core.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IOrderPrintManager
{
  public abstract String getShipScript(Integer[] paramArrayOfInteger);
  
  public abstract String getExpressScript(Integer[] paramArrayOfInteger);
  
  public abstract void saveShopNos(Integer[] paramArrayOfInteger, String[] paramArrayOfString);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract String ship(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IOrderPrintManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */