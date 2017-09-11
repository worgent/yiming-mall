package com.enation.app.shop.core.service;

import com.enation.framework.database.Page;
import java.util.List;
import java.util.Map;

public abstract interface IGoodsSearchManager
{
  public abstract Page search(int paramInt1, int paramInt2, Map<String, String> paramMap);
  
  public abstract List[] getPropListByCat(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IGoodsSearchManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */