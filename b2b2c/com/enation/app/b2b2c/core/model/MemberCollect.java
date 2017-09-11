 package com.enation.app.b2b2c.core.model;

 import java.io.Serializable;








 public class MemberCollect
   implements Serializable
 {
   private static final long serialVersionUID = -3611072385708540242L;
   private Integer id;
   private Integer member_id;
   private Integer store_id;
   private Long create_time;

   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) { this.id = id; }

   public Integer getMember_id() {
     return this.member_id;
   }

   public void setMember_id(Integer member_id) { this.member_id = member_id; }

   public Integer getStore_id() {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) { this.store_id = store_id; }

   public Long getCreate_time() {
     return this.create_time;
   }

   public void setCreate_time(Long create_time) { this.create_time = create_time; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\MemberCollect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */