package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Order;

public abstract interface IOrderTabShowEvent
{
  public abstract String getTabName(Order paramOrder);
  
  public abstract int getOrder();
  
  public abstract boolean canBeExecute(Order paramOrder);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\order\IOrderTabShowEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */