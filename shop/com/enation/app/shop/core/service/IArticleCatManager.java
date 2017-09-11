package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.ArticleCat;
import java.util.List;

public abstract interface IArticleCatManager
{
  public abstract ArticleCat getById(int paramInt);
  
  public abstract void saveAdd(ArticleCat paramArticleCat);
  
  public abstract void update(ArticleCat paramArticleCat);
  
  public abstract int delete(int paramInt);
  
  public abstract void saveSort(int[] paramArrayOfInt1, int[] paramArrayOfInt2);
  
  public abstract List listChildById(Integer paramInteger);
  
  public abstract List listHelp(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IArticleCatManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */