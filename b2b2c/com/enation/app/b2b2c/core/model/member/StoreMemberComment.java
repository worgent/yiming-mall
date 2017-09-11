 package com.enation.app.b2b2c.core.model.member;

 import com.enation.app.shop.core.model.MemberComment;
 import org.springframework.stereotype.Component;



 @Component
 public class StoreMemberComment
   extends MemberComment
 {
   private int store_desccredit;
   private int store_servicecredit;
   private int store_deliverycredit;
   private int store_id;

   public int getStore_desccredit()
   {
     return this.store_desccredit;
   }

   public void setStore_desccredit(int store_desccredit) { this.store_desccredit = store_desccredit; }

   public int getStore_servicecredit() {
     return this.store_servicecredit;
   }

   public void setStore_servicecredit(int store_servicecredit) { this.store_servicecredit = store_servicecredit; }

   public int getStore_deliverycredit() {
     return this.store_deliverycredit;
   }

   public void setStore_deliverycredit(int store_deliverycredit) { this.store_deliverycredit = store_deliverycredit; }

   public int getStore_id() {
     return this.store_id;
   }

   public void setStore_id(int store_id) { this.store_id = store_id; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\member\StoreMemberComment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */