 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;





 public class OrderMeta
 {
   private Integer metaid;
   private int orderid;
   private String meta_key;
   private String meta_value;

   @PrimaryKeyField
   public Integer getMetaid()
   {
     return this.metaid;
   }

   public void setMetaid(Integer metaid) {
     this.metaid = metaid;
   }

   public int getOrderid() {
     return this.orderid;
   }

   public void setOrderid(int orderid) {
     this.orderid = orderid;
   }

   public String getMeta_key() {
     return this.meta_key;
   }

   public void setMeta_key(String meta_key) {
     this.meta_key = meta_key;
   }

   public String getMeta_value() {
     return this.meta_value;
   }

   public void setMeta_value(String meta_value) {
     this.meta_value = meta_value;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\OrderMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */