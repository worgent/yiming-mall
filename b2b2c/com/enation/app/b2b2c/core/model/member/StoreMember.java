 package com.enation.app.b2b2c.core.model.member;

 import com.enation.app.base.core.model.Member;


 public class StoreMember
   extends Member
 {
   private Integer is_store;
   private Integer store_id;

   public Integer getIs_store()
   {
     return this.is_store;
   }

   public void setIs_store(Integer is_store) { this.is_store = is_store; }

   public Integer getStore_id() {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) { this.store_id = store_id; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\member\StoreMember.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */