package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.OrderItem;
import java.util.List;

public abstract interface IOrderItemFilter
{
  public abstract void filter(Integer paramInteger, List<OrderItem> paramList);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\order\IOrderItemFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */