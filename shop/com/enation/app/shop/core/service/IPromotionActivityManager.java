package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.PromotionActivity;
import com.enation.framework.database.Page;

public abstract interface IPromotionActivityManager
{
  public abstract void add(PromotionActivity paramPromotionActivity);
  
  public abstract PromotionActivity get(Integer paramInteger);
  
  public abstract Page list(int paramInt1, int paramInt2);
  
  public abstract void edit(PromotionActivity paramPromotionActivity);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IPromotionActivityManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */