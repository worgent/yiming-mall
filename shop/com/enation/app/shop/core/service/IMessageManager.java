package com.enation.app.shop.core.service;

import com.enation.app.base.core.model.Message;
import com.enation.framework.database.Page;
@Deprecated
public abstract interface IMessageManager
{
  public abstract Page pageMessage(int paramInt1, int paramInt2, String paramString);
  
  public abstract void addMessage(Message paramMessage);
  
  public abstract void delinbox(String paramString);
  
  public abstract void deloutbox(String paramString);
  
  public abstract void setMessage_read(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\IMessageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */