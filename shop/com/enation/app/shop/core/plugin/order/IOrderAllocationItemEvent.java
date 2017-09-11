package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.AllocationItem;
import com.enation.app.shop.core.model.OrderItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public abstract interface IOrderAllocationItemEvent
{
  public abstract String getAllocationStoreHtml(OrderItem paramOrderItem);
  
  public abstract String getAllocationViewHtml(OrderItem paramOrderItem);
  
  public abstract void onAllocation(AllocationItem paramAllocationItem);
  
  public abstract void filterAlloViewItem(Map paramMap, ResultSet paramResultSet)
    throws SQLException;
  
  public abstract boolean canBeExecute(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\order\IOrderAllocationItemEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */