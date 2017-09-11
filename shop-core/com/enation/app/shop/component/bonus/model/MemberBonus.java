 package com.enation.app.shop.component.bonus.model;

 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;






 public class MemberBonus
 {
   private int bonus_id;
   private int bonus_type_id;
   private String bonus_sn;
   private Integer member_id;
   private Long used_time;
   private Long create_time;
   private Integer order_id;
   private String order_sn;
   private String member_name;
   private String type_name;
   private int bonus_type;
   private double bonus_money;
   private double min_goods_amount;
   private int use_start_date;
   private int use_end_date;

   @PrimaryKeyField
   public int getBonus_id()
   {
     return this.bonus_id;
   }

   public void setBonus_id(int bonus_id) { this.bonus_id = bonus_id; }

   public int getBonus_type_id() {
     return this.bonus_type_id;
   }

   public void setBonus_type_id(int bonus_type_id) { this.bonus_type_id = bonus_type_id; }

   public String getBonus_sn() {
     return this.bonus_sn;
   }

   public void setBonus_sn(String bonus_sn) { this.bonus_sn = bonus_sn; }

   public Integer getMember_id() {
     return this.member_id;
   }

   public void setMember_id(Integer member_id) { this.member_id = member_id; }

   public Integer getOrder_id()
   {
     return this.order_id;
   }

   public void setOrder_id(Integer order_id) { this.order_id = order_id; }

   public String getOrder_sn()
   {
     return this.order_sn;
   }

   public void setOrder_sn(String order_sn) { this.order_sn = order_sn; }

   public String getMember_name() {
     return this.member_name;
   }

   public void setMember_name(String member_name) { this.member_name = member_name; }

   public String getType_name() {
     return this.type_name;
   }

   public void setType_name(String type_name) { this.type_name = type_name; }

   public int getBonus_type() {
     return this.bonus_type;
   }

   public void setBonus_type(int bonus_type) { this.bonus_type = bonus_type; }

   @NotDbField
   public double getBonus_money()
   {
     return this.bonus_money;
   }

   public void setBonus_money(double bonus_money) { this.bonus_money = bonus_money; }

   @NotDbField
   public double getMin_goods_amount()
   {
     return this.min_goods_amount;
   }

   public void setMin_goods_amount(double min_goods_amount) { this.min_goods_amount = min_goods_amount; }

   @NotDbField
   public int getUse_start_date() {
     return this.use_start_date;
   }

   public void setUse_start_date(int use_start_date) { this.use_start_date = use_start_date; }

   @NotDbField
   public int getUse_end_date() {
     return this.use_end_date;
   }

   public void setUse_end_date(int use_end_date) { this.use_end_date = use_end_date; }

   public Long getUsed_time() {
     return this.used_time;
   }

   public void setUsed_time(Long used_time) { this.used_time = used_time; }

   public Long getCreate_time() {
     return this.create_time;
   }

   public void setCreate_time(Long create_time) { this.create_time = create_time; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\model\MemberBonus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */