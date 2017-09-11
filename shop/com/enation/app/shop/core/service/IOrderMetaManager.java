package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.OrderMeta;
import java.util.List;

public abstract interface IOrderMetaManager
{
  public abstract void add(OrderMeta paramOrderMeta);
  
  public abstract List<OrderMeta> list(int paramInt);
  
  public abstract OrderMeta get(int paramInt, String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IOrderMetaManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */