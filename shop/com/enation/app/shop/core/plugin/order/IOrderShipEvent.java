package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.AllocationItem;
import com.enation.app.shop.core.model.Delivery;
import com.enation.app.shop.core.model.DeliveryItem;
import com.enation.app.shop.core.model.Order;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IOrderShipEvent
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void itemShip(Order paramOrder, DeliveryItem paramDeliveryItem, AllocationItem paramAllocationItem);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void ship(Delivery paramDelivery, List<DeliveryItem> paramList);
  
  public abstract boolean canBeExecute(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\order\IOrderShipEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */