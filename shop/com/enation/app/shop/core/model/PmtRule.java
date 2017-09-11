 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;












 public class PmtRule
   implements Serializable
 {
   private Integer rule_id;
   private Integer pmt_id;
   private Long begin_time;
   private Long end_time;
   private Double discount;
   private String brief;
   private Integer type;
   private Long update_time;
   private Double order_price;

   public Long getBegin_time()
   {
     return this.begin_time;
   }

   public void setBegin_time(Long begin_time) {
     this.begin_time = begin_time;
   }

   public String getBrief() {
     return this.brief;
   }

   public void setBrief(String brief) {
     this.brief = brief;
   }

   public Long getEnd_time() {
     return this.end_time;
   }

   public void setEnd_time(Long end_time) {
     this.end_time = end_time;
   }

   public Double getOrder_price() {
     return this.order_price;
   }

   public void setOrder_price(Double order_price) {
     this.order_price = order_price;
   }

   @PrimaryKeyField
   public Integer getPmt_id() {
     return this.pmt_id;
   }

   public void setPmt_id(Integer pmt_id) {
     this.pmt_id = pmt_id;
   }

   public Integer getRule_id() {
     return this.rule_id;
   }

   public void setRule_id(Integer rule_id) {
     this.rule_id = rule_id;
   }

   public Double getDiscount()
   {
     return this.discount;
   }

   public void setDiscount(Double discount) {
     this.discount = discount;
   }

   public Integer getType() {
     return this.type;
   }

   public void setType(Integer type) {
     this.type = type;
   }

   public Long getUpdate_time() {
     return this.update_time;
   }

   public void setUpdate_time(Long update_time) {
     this.update_time = update_time;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\PmtRule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */