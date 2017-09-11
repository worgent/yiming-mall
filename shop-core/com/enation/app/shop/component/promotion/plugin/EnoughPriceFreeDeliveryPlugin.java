 package com.enation.app.shop.component.promotion.plugin;

 import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;















 @Component
 public class EnoughPriceFreeDeliveryPlugin
   extends AutoRegisterPlugin
   implements IPromotionPlugin
 {
   public void register() {}

   public String[] getConditions()
   {
     return new String[] { "order", "memberLv" };
   }


   public String getMethods()
   {
     return "freeFreight";
   }


   public String getAuthor()
   {
     return "kingapex";
   }


   public String getId()
   {
     return "enoughPriceFreeDeliveryPlugin";
   }


   public String getName()
   {
     return "免运费———购物车中商品总金额大于指定金额,免运费。";
   }


   public String getType()
   {
     return "order";
   }


   public String getVersion()
   {
     return "1.0";
   }

   public void perform(Object... params) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\promotion\plugin\EnoughPriceFreeDeliveryPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */