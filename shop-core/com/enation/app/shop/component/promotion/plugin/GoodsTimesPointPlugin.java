 package com.enation.app.shop.component.promotion.plugin;

 import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
 import com.enation.framework.plugin.AutoRegisterPlugin;
import org.springframework.stereotype.Component;














 @Component
 public class GoodsTimesPointPlugin
   extends AutoRegisterPlugin
   implements IPromotionPlugin
 {
   public void register() {}

   public String[] getConditions()
   {
     return new String[] { "goods", "memberLv" };
   }

   public String getMethods()
   {
     return "timesPoint";
   }

   public String getAuthor()
   {
     return "kingapex";
   }

   public String getId()
   {
     return "goodsTimesPointPlugin";
   }

   public String getName()
   {
     return "积分翻倍——顾客购买指定的商品，可获得翻倍积分或者x倍积分";
   }

   public String getType()
   {
     return "goods";
   }

   public String getVersion()
   {
     return "1.0";
   }

   public void perform(Object... params) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\promotion\plugin\GoodsTimesPointPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */