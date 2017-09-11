package com.enation.app.shop.component.bonus.service;

import com.enation.app.shop.component.bonus.model.BonusType;
import com.enation.framework.database.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IBonusTypeManager
{
  public abstract void add(BonusType paramBonusType);
  
  public abstract void update(BonusType paramBonusType);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  public abstract Page list(int paramInt1, int paramInt2);
  
  public abstract BonusType get(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\service\IBonusTypeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */