package com.enation.app.b2b2c.core.service.goods;

import com.enation.app.b2b2c.core.model.store.StoreCat;
import java.util.List;
import java.util.Map;

public abstract interface IStoreGoodsCatManager
{
  public abstract List storeCatList(Integer paramInteger);
  
  public abstract void addStoreCat(StoreCat paramStoreCat);
  
  public abstract void editStoreCat(StoreCat paramStoreCat);
  
  public abstract void deleteStoreCat(Integer paramInteger);
  
  public abstract List getStoreCatList(Integer paramInteger1, Integer paramInteger2);
  
  public abstract StoreCat getStoreCat(Map paramMap);
  
  public abstract Integer is_children(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\goods\IStoreGoodsCatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */