 package com.enation.app.shop.component.payment.plugin.cod;

 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.model.PayEnable;
 import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
 import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
 import org.springframework.stereotype.Component;







 @Component("cod")
 public class CodPlugin
   extends AbstractPaymentPlugin
   implements IPaymentEvent
 {
   public String onCallBack(String ordertype)
   {
     return "";
   }


   public String onPay(PayCfg payCfg, PayEnable order)
   {
     return "";
   }




   public String getId()
   {
     return "cod";
   }

   public String getName()
   {
     return "货到付款";
   }


   public String onReturn(String ordertype)
   {
     return "";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\payment\plugin\cod\CodPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */