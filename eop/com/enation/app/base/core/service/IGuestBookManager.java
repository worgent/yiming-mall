package com.enation.app.base.core.service;

import com.enation.app.base.core.model.GuestBook;
import com.enation.framework.database.Page;

public abstract interface IGuestBookManager
{
  public abstract void add(GuestBook paramGuestBook);
  
  public abstract void reply(GuestBook paramGuestBook);
  
  public abstract Page list(String paramString, int paramInt1, int paramInt2);
  
  public abstract GuestBook get(int paramInt);
  
  public abstract void edit(int paramInt, String paramString);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\IGuestBookManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */