package com.enation.app.shop.core.service;

import com.enation.app.base.core.model.MemberLv;
import com.enation.framework.database.Page;
import java.util.List;

public abstract interface IMemberLvManager
{
  public abstract Integer getDefaultLv();
  
  public abstract void add(MemberLv paramMemberLv);
  
  public abstract void edit(MemberLv paramMemberLv);
  
  public abstract Page list(String paramString, int paramInt1, int paramInt2);
  
  public abstract MemberLv get(Integer paramInteger);
  
  public abstract MemberLv getByPoint(int paramInt);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  public abstract List<MemberLv> list();
  
  public abstract List<MemberLv> list(String paramString);
  
  public abstract MemberLv getNextLv(int paramInt);
  
  public abstract List getCatDiscountByLv(int paramInt);
  
  public abstract List getHaveCatDiscountByLv(int paramInt);
  
  public abstract void saveCatDiscountByLv(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt);
  
  public abstract List getHaveLvDiscountByCat(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IMemberLvManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */