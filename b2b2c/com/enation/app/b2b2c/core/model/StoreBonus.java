 package com.enation.app.b2b2c.core.model;

 import com.enation.app.shop.component.bonus.model.BonusType;



 public class StoreBonus
   extends BonusType
 {
   private Integer store_id;
   private String img_bonus;

   public String getImg_bonus()
   {
     return this.img_bonus;
   }

   public void setImg_bonus(String img_bonus) {
     this.img_bonus = img_bonus;
   }

   public Integer getStore_id() {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) {
     this.store_id = store_id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\StoreBonus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */