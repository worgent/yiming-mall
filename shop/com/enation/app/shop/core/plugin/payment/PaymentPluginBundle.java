 package com.enation.app.shop.core.plugin.payment;

 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import java.util.List;


 public class PaymentPluginBundle
   extends AutoRegisterPluginsBundle
 {
   public String getName()
   {
     return "支付插件桩";
   }

   public List getPluginList() {
     return getPlugins();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\payment\PaymentPluginBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */