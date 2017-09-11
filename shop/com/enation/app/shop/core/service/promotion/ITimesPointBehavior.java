package com.enation.app.shop.core.service.promotion;

import com.enation.app.shop.core.model.Promotion;

public abstract interface ITimesPointBehavior
  extends IPromotionBehavior
{
  public abstract Integer countPoint(Promotion paramPromotion, Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\promotion\ITimesPointBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */