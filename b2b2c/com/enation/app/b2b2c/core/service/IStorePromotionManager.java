package com.enation.app.b2b2c.core.service;

import com.enation.app.b2b2c.core.model.StoreBonus;

public abstract interface IStorePromotionManager
{
  public abstract void add_FullSubtract(StoreBonus paramStoreBonus);
  
  public abstract void receive_bonus(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\IStorePromotionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */