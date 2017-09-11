package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.MemberOrderItem;
import com.enation.framework.database.Page;

public abstract interface IMemberOrderItemManager
{
  public abstract void add(MemberOrderItem paramMemberOrderItem);
  
  public abstract int count(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract int count(int paramInt1, int paramInt2);
  
  public abstract MemberOrderItem get(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void update(MemberOrderItem paramMemberOrderItem);
  
  public abstract Page getGoodsList(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IMemberOrderItemManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */