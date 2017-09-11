package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Allocation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IOrderAllocationEvent
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void onAllocation(Allocation paramAllocation);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\order\IOrderAllocationEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */