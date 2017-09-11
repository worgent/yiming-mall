package com.enation.app.shop.component.bonus.service;

import com.enation.app.shop.component.bonus.model.MemberBonus;
import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IBonusManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int sendForMemberLv(int paramInt1, int paramInt2, int paramInt3);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int sendForMember(int paramInt, Integer[] paramArrayOfInteger);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int sendForGoods(int paramInt, Integer[] paramArrayOfInteger);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int sendForOffLine(int paramInt1, int paramInt2);
  
  public abstract Page list(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Page pageList(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void delete(int paramInt);
  
  public abstract List<Map> getGoodsList(int paramInt);
  
  public abstract String exportToExcel(int paramInt);
  
  public abstract List<Map> getMemberBonusList(int paramInt, Double paramDouble);
  
  public abstract MemberBonus getBonus(int paramInt);
  
  public abstract MemberBonus getBonus(String paramString);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void use(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void returned(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\service\IBonusManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */