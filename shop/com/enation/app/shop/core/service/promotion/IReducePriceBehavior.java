package com.enation.app.shop.core.service.promotion;

import com.enation.app.shop.core.model.Promotion;

public abstract interface IReducePriceBehavior
  extends IPromotionBehavior
{
  public abstract Double reducedPrice(Promotion paramPromotion, Double paramDouble);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\promotion\IReducePriceBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */