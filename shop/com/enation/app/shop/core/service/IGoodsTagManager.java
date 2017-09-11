package com.enation.app.shop.core.service;

import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IGoodsTagManager
{
  public abstract Page getGoodsList(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List getGoodsList(int paramInt);
  
  public abstract Page getGoodsList(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void addTag(int paramInt1, int paramInt2);
  
  public abstract void removeTag(int paramInt1, int paramInt2);
  
  public abstract void updateOrderNum(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Integer[] paramArrayOfInteger3);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IGoodsTagManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */