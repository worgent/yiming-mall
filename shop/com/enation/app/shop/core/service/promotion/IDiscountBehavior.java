package com.enation.app.shop.core.service.promotion;

import com.enation.app.shop.core.model.Promotion;

public abstract interface IDiscountBehavior
  extends IPromotionBehavior
{
  public abstract Double discount(Promotion paramPromotion, Double paramDouble);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\promotion\IDiscountBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */