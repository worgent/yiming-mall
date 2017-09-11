package com.enation.app.b2b2c.core.service.goods;

import com.enation.app.b2b2c.core.model.goods.StoreTag;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IStoreGoodsTagManager
{
  public abstract List list(Integer paramInteger);
  
  public abstract void add(StoreTag paramStoreTag);
  
  public abstract void deleteRel(Integer paramInteger, Integer[] paramArrayOfInteger);
  
  public abstract void addRels(Integer paramInteger, Integer[] paramArrayOfInteger);
  
  public abstract void saveSort(Integer paramInteger, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
  
  public abstract Page getGoodsList(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Page getGoodsList(Map paramMap, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\goods\IStoreGoodsTagManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */