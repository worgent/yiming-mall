 package com.enation.app.shop.component.promotion.plugin;

 import com.enation.app.shop.core.plugin.promotion.IPromotionPlugin;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;

 @Component
 public class GoodsDiscountPlugin
   extends AutoRegisterPlugin
   implements IPromotionPlugin
 {
   public String[] getConditions()
   {
     return new String[] { "goods", "memberLv" };
   }

   public String getMethods()
   {
     return "discount";
   }

   public String getAuthor()
   {
     return "kingapex";
   }

   public String getId()
   {
     return "goodsDiscountPlugin";
   }

   public String getName()
   {
     return "打折————商品直接打折，如全场女鞋8折。可以对商品任意折扣，适合低价清货促销";
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

   public void register() {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\promotion\plugin\GoodsDiscountPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */