 package com.enation.app.b2b2c.core.model.order;

 import com.enation.app.shop.core.model.Order;
 import com.enation.framework.database.NotDbField;




 public class StoreOrder
   extends Order
 {
   private Integer store_id;
   private Integer parent_id;
   private String[] storeids;

   public Integer getStore_id()
   {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) { this.store_id = store_id; }

   public Integer getParent_id() {
     return this.parent_id;
   }

   public void setParent_id(Integer parent_id) { this.parent_id = parent_id; }

   @NotDbField
   public String getOrderType() {
     return "b";
   }

   @NotDbField
   public String[] getStoreids() { return this.storeids; }

   public void setStoreids(String[] storeids) {
     this.storeids = storeids;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\order\StoreOrder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */