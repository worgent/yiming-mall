package com.enation.app.b2b2c.core.service.store;

import com.enation.app.b2b2c.core.model.store.Store;
import com.enation.framework.database.Page;

import java.util.List;
import java.util.Map;

public abstract interface IStoreManager
{
  public abstract void apply(Store paramStore);
  
  public abstract void audit_pass(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5);

  public abstract Page store_list(Map paramMap, Integer paramInteger, int paramInt1, int paramInt2);
  //获得推荐店铺
  public abstract List store_recommend_list();

  public abstract void disStore(Integer paramInteger);
  
  public abstract void useStore(Integer paramInteger);
  
  public abstract Store getStore(Integer paramInteger);
  
  public abstract boolean checkStoreName(String paramString);
  
  public abstract void editStore(Store paramStore);
  
  public abstract void editStore(Map paramMap);
  
  public abstract boolean checkStore();
  
  public abstract void save(Store paramStore);
  
  public abstract Integer checkIdNumber(String paramString);
  
  public abstract void editStoreOnekey(String paramString1, String paramString2);
  
  public abstract void addcollectNum(Integer paramInteger);
  
  public abstract void reduceCollectNum(Integer paramInteger);
  
  public abstract void saveStoreLicense(Integer paramInteger1, String paramString1, String paramString2, Integer paramInteger2, Integer paramInteger3);
  
  public abstract Page auth_list(Map paramMap, Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract void auth_pass(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\store\IStoreManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */