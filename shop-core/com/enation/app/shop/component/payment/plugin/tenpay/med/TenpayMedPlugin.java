 package com.enation.app.shop.component.payment.plugin.tenpay.med;

 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.model.PayEnable;
 import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
 import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
 import org.springframework.stereotype.Component;













































































































 @Component
 public class TenpayMedPlugin
   extends AbstractPaymentPlugin
   implements IPaymentEvent
 {
   public String onCallBack(String ordertype)
   {
     return null;
   }





























































   public String onPay(PayCfg payCfg, PayEnable order)
   {
     return null;
   }

   public String getId()
   {
     return "tenpayMedPlugin";
   }

   public String getName()
   {
     return "财付通（中介担保）";
   }



   public String onReturn(String ordertype)
   {
     return null;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\payment\plugin\tenpay\med\TenpayMedPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */