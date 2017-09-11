package com.enation.app.shop.core.plugin.cart;

import com.enation.app.shop.core.model.Cart;

public abstract interface ICartAddEvent
{
  public abstract void add(Cart paramCart);
  
  public abstract void afterAdd(Cart paramCart);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\cart\ICartAddEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */