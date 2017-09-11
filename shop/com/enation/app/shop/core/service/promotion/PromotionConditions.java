 package com.enation.app.shop.core.service.promotion;


 public class PromotionConditions
 {
   public static final String ORDER = "order";

   public static final String MEMBERLV = "memberLv";

   public static final String GOODS = "goods";

   private boolean hasOrder = false;
   private boolean hasMemberLv = false;
   private boolean hasGoods = false;

   public PromotionConditions(String[] conditions) {
     if (conditions != null) {
       for (String cond : conditions) {
         if ("order".equals(cond)) {
           this.hasOrder = true;
         }

         if ("memberLv".equals(cond)) {
           this.hasMemberLv = true;
         }

         if ("goods".equals(cond)) {
           this.hasGoods = true;
         }
       }
     }
   }

   public boolean getHasOrder() {
     return this.hasOrder;
   }

   public boolean getHasMemberLv()
   {
     return this.hasMemberLv;
   }

   public void setHasMemberLv(boolean hasMemberLv)
   {
     this.hasMemberLv = hasMemberLv;
   }

   public void setHasOrder(boolean hasOrder)
   {
     this.hasOrder = hasOrder;
   }

   public boolean getHasGoods()
   {
     return this.hasGoods;
   }

   public void setHasGoods(boolean hasGoods)
   {
     this.hasGoods = hasGoods;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\promotion\PromotionConditions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */