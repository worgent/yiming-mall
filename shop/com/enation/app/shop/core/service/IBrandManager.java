package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Brand;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IBrandManager
{
  public abstract boolean checkUsed(Integer[] paramArrayOfInteger);
  
  public abstract boolean checkname(String paramString, Integer paramInteger);
  
  public abstract void add(Brand paramBrand);
  
  public abstract void update(Brand paramBrand);
  
  public abstract Page list(String paramString, int paramInt1, int paramInt2);
  
  public abstract Page listTrash(String paramString, int paramInt1, int paramInt2);
  
  public abstract void revert(Integer[] paramArrayOfInteger);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  public abstract void clean(Integer[] paramArrayOfInteger);
  
  public abstract List<Brand> list();
  
  public abstract List<Brand> list(int paramInt);
  
  public abstract List<Brand> listByTypeId(Integer paramInteger);
  
  public abstract List<Brand> listByCatId(Integer paramInteger);
  
  public abstract List groupByCat();
  
  public abstract Brand get(Integer paramInteger);
  
  public abstract Page getGoods(Integer paramInteger, int paramInt1, int paramInt2);
  
  public abstract List<Map> queryAllTypeNameAndId();
  
  public abstract Page search(int paramInt1, int paramInt2, String paramString, Integer paramInteger);
  
  public abstract Page searchBrand(Map paramMap, int paramInt1, int paramInt2, String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IBrandManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */