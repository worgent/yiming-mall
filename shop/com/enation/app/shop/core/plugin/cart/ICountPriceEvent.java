package com.enation.app.shop.core.plugin.cart;

import com.enation.app.shop.core.model.support.OrderPrice;

public abstract interface ICountPriceEvent
{
  public abstract OrderPrice countPrice(OrderPrice paramOrderPrice);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\cart\ICountPriceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */