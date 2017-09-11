package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.FreeOffer;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IFreeOfferManager
{
  public abstract FreeOffer get(int paramInt);
  
  public abstract void saveAdd(FreeOffer paramFreeOffer);
  
  public abstract void update(FreeOffer paramFreeOffer);
  
  public abstract void delete(String paramString);
  
  public abstract void revert(String paramString);
  
  public abstract void clean(String paramString);
  
  public abstract Page list(int paramInt1, int paramInt2);
  
  public abstract Page list(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract Page pageTrash(String paramString1, String paramString2, int paramInt1, int paramInt2);
  
  public abstract List getOrderGift(int paramInt);
  
  public abstract List list(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IFreeOfferManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */