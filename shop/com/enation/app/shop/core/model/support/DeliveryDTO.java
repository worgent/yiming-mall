 package com.enation.app.shop.core.model.support;

 import com.enation.app.shop.core.model.Delivery;

 public class DeliveryDTO
 {
   private Delivery delivery;
   private String reason;
   private String[] sn;
   private String[] name;
   private String[] num;
   private Integer[] item_id;

   public String[] getName() {
     return this.name;
   }

   public void setName(String[] name) { this.name = name; }

   public String[] getNum()
   {
     return this.num;
   }

   public void setNum(String[] num) { this.num = num; }

   public String[] getSn() {
     return this.sn;
   }

   public void setSn(String[] sn) { this.sn = sn; }

   public Delivery getDelivery()
   {
     return this.delivery;
   }

   public void setDelivery(Delivery delivery) { this.delivery = delivery; }

   public Integer[] getItem_id() {
     return this.item_id;
   }

   public void setItem_id(Integer[] item_id) { this.item_id = item_id; }

   public String getReason() {
     return this.reason;
   }

   public void setReason(String reason) { this.reason = reason; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\support\DeliveryDTO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */