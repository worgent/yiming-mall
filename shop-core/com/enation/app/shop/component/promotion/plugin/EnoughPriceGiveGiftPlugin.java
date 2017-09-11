 package com.enation.app.shop.component.promotion.plugin;

 import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
 import com.enation.framework.plugin.AutoRegisterPlugin;
import org.springframework.stereotype.Component;










 @Component
 public class EnoughPriceGiveGiftPlugin
   extends AutoRegisterPlugin
   implements IPromotionPlugin
 {
   public String[] getConditions()
   {
     return new String[] { "order", "memberLv" };
   }

   public String getMethods()
   {
     return "giveGift";
   }

   public String getAuthor()
   {
     return "kingapex";
   }

   public String getId()
   {
     return "enoughPriceGiveGiftPlugin";
   }

   public String getName()
   {
     return "满就送———购物车中商品总金额大于指定金额，赠送某个赠品";
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

   public void register() {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\promotion\plugin\EnoughPriceGiveGiftPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */