package com.enation.app.base.core.service;

import com.enation.app.base.core.model.Smtp;
import java.util.List;

public abstract interface ISmtpManager
{
  public abstract void add(Smtp paramSmtp);
  
  public abstract void edit(Smtp paramSmtp);
  
  public abstract void delete(Integer[] paramArrayOfInteger);
  
  public abstract List<Smtp> list();
  
  public abstract void sendOneMail(Smtp paramSmtp);
  
  public abstract Smtp get(int paramInt);
  
  public abstract Smtp getCurrentSmtp();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\ISmtpManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */