package com.enation.app.shop.core.plugin.cart;

import com.enation.app.shop.core.model.support.CartItem;
import java.util.List;

public abstract interface ICartItemFilter
{
  public abstract void filter(List<CartItem> paramList, String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\cart\ICartItemFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */