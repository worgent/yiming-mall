package com.enation.app.base.core.service;

import com.enation.app.base.core.model.SmsMessage;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface ISmsManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int sendSmsNow(SmsMessage paramSmsMessage);
  
  public abstract void reSend(int paramInt);
  
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void delete(int paramInt);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\ISmsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */