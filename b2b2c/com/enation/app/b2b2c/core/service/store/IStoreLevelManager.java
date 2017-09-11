package com.enation.app.b2b2c.core.service.store;

import com.enation.app.b2b2c.core.model.store.StoreLevel;
import java.util.List;

public abstract interface IStoreLevelManager
{
  public abstract List storeLevelList();
  
  public abstract void addStoreLevel(String paramString);
  
  public abstract void editStoreLevel(String paramString, Integer paramInteger);
  
  public abstract void delStoreLevel(Integer paramInteger);
  
  public abstract StoreLevel getStoreLevel(Integer paramInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\store\IStoreLevelManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */