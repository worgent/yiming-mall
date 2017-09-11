 package com.enation.app.b2b2c.core.model.cart;

 import com.enation.app.shop.core.model.support.CartItem;

 public class StoreCartItem
   extends CartItem
 {
   private Integer store_id;
   private String store_name;

   public Integer getStore_id()
   {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) { this.store_id = store_id; }

   public String getStore_name() {
     return this.store_name;
   }

   public void setStore_name(String store_name) { this.store_name = store_name; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\cart\StoreCartItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */