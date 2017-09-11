package com.enation.app.shop.core.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IProductStoreManager
{
  public abstract int getEnableStroe(int paramInt1, int paramInt2);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void decreaseEnable(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void increaseEnable(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void decreaseStroe(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void increaseStroe(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IProductStoreManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */