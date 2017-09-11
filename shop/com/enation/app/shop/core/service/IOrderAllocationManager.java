package com.enation.app.shop.core.service;

import java.util.List;

public abstract interface IOrderAllocationManager
{
  public abstract List listAllocation(int paramInt);
  
  public abstract void clean(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IOrderAllocationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */