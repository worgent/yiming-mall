 package com.enation.app.shop.component.bonus.model;

 import com.enation.framework.database.PrimaryKeyField;





 public class BonusType
 {
   private int type_id;
   private String type_name;
   private Double type_money;
   private int send_type;
   private int send_start_date;
   private int send_end_date;
   private int use_start_date;
   private int use_end_date;
   private Double min_goods_amount;
   private String recognition;
   private int create_num;
   private int use_num;

   @PrimaryKeyField
   public int getType_id()
   {
     return this.type_id;
   }

   public void setType_id(int type_id) { this.type_id = type_id; }

   public String getType_name() {
     return this.type_name;
   }

   public void setType_name(String type_name) { this.type_name = type_name; }

   public Double getType_money() {
     return this.type_money;
   }

   public void setType_money(Double type_money) { this.type_money = type_money; }

   public int getSend_type() {
     return this.send_type;
   }

   public void setSend_type(int send_type) { this.send_type = send_type; }

   public int getSend_start_date() {
     return this.send_start_date;
   }

   public void setSend_start_date(int send_start_date) { this.send_start_date = send_start_date; }

   public int getSend_end_date() {
     return this.send_end_date;
   }

   public void setSend_end_date(int send_end_date) { this.send_end_date = send_end_date; }

   public int getUse_start_date() {
     return this.use_start_date;
   }

   public void setUse_start_date(int use_start_date) { this.use_start_date = use_start_date; }

   public int getUse_end_date() {
     return this.use_end_date;
   }

   public void setUse_end_date(int use_end_date) { this.use_end_date = use_end_date; }

   public Double getMin_goods_amount() {
     return this.min_goods_amount;
   }

   public void setMin_goods_amount(Double min_goods_amount) { this.min_goods_amount = min_goods_amount; }

   public String getRecognition() {
     return this.recognition;
   }

   public void setRecognition(String recognition) { this.recognition = recognition; }

   public int getCreate_num() {
     return this.create_num;
   }

   public void setCreate_num(int create_num) { this.create_num = create_num; }

   public int getUse_num() {
     return this.use_num;
   }

   public void setUse_num(int use_num) { this.use_num = use_num; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\model\BonusType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */