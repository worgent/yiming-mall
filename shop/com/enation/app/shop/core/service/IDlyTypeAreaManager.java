package com.enation.app.shop.core.service;

import java.util.Map;

public abstract interface IDlyTypeAreaManager
{
  public abstract Map listAllByRegion(String paramString);
  
  public abstract int queryByrdgion(String paramString);
  
  public abstract Map queryOtherRegions(Integer paramInteger, String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IDlyTypeAreaManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */