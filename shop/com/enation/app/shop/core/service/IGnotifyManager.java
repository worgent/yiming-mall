package com.enation.app.shop.core.service;

import com.enation.framework.database.Page;

public abstract interface IGnotifyManager
{
  public abstract Page pageGnotify(int paramInt1, int paramInt2);
  
  public abstract void deleteGnotify(int paramInt);
  
  public abstract void addGnotify(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IGnotifyManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */