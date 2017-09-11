package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.FreeOfferCategory;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IFreeOfferCategoryManager
{
  public abstract FreeOfferCategory get(int paramInt);
  
  public abstract void saveAdd(FreeOfferCategory paramFreeOfferCategory);
  
  public abstract void update(FreeOfferCategory paramFreeOfferCategory);
  
  public abstract void delete(String paramString);
  
  public abstract void revert(String paramString);
  
  public abstract void clean(String paramString);
  
  public abstract List getFreeOfferCategoryList();
  
  public abstract Page searchFreeOfferCategory(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Page pageTrash(String paramString1, String paramString2, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IFreeOfferCategoryManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */