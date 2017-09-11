package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.OrderItem;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IOrderItemSaveEvent
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void onItemSave(Order paramOrder, OrderItem paramOrderItem);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\order\IOrderItemSaveEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */