package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Cat;
import java.util.List;

public abstract interface IGoodsCatManager
{
  public abstract boolean checkname(String paramString, Integer paramInteger);
  
  public abstract Cat getById(int paramInt);
  
  public abstract void saveAdd(Cat paramCat);
  
  public abstract void update(Cat paramCat);
  
  public abstract int delete(int paramInt);
  
  public abstract List<Cat> listAllChildren(Integer paramInteger);
  
  public abstract List<Cat> listChildren(Integer paramInteger);
  
  public abstract void saveSort(int[] paramArrayOfInt1, int[] paramArrayOfInt2);
  
  public abstract List getNavpath(int paramInt);
  
  public abstract List<Cat> getParents(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IGoodsCatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */