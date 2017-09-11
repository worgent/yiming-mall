package com.enation.app.shop.core.plugin.payment;

import com.enation.app.shop.core.model.PayCfg;
import com.enation.app.shop.core.model.PayEnable;

public abstract interface IPaymentEvent
{
  public abstract String onPay(PayCfg paramPayCfg, PayEnable paramPayEnable);
  
  public abstract String onCallBack(String paramString);
  
  public abstract String onReturn(String paramString);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\payment\IPaymentEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */