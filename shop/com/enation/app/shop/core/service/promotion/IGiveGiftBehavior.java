package com.enation.app.shop.core.service.promotion;

import com.enation.app.shop.core.model.Promotion;
import java.util.List;

public abstract interface IGiveGiftBehavior
  extends IPromotionBehavior
{
  public abstract void giveGift(Promotion paramPromotion, Integer paramInteger);
  
  public abstract List getGiftList(Promotion paramPromotion);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\promotion\IGiveGiftBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */