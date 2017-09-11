package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.support.CartItem;
import java.util.List;

public abstract interface IBeforeOrderCreateEvent
{
  public abstract void onBeforeOrderCreate(Order paramOrder, List<CartItem> paramList, String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\order\IBeforeOrderCreateEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */