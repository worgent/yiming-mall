package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.Cat;
import com.enation.framework.database.Page;
import java.util.Map;

public abstract interface IGoodsSearchManager2
{
  public abstract Page search(int paramInt1, int paramInt2, String paramString);
  
  public abstract Map<String, Object> getSelector(Cat paramCat, String paramString);
  
  public abstract void putParams(Map<String, Object> paramMap, String paramString);
  
  public abstract Cat getCat(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IGoodsSearchManager2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */