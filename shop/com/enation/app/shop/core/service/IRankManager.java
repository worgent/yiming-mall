package com.enation.app.shop.core.service;

import java.util.List;
import java.util.Map;

public abstract interface IRankManager
{
  public abstract List rank_goods(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract List rank_member(int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  public abstract List rank_buy(int paramInt1, int paramInt2, String paramString);
  
  public abstract Map rank_all();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IRankManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */