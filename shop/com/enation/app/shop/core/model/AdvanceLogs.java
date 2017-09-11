 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;




 public class AdvanceLogs
   implements Serializable
 {
   private int log_id;
   private int member_id;
   private Double money;
   private String message;
   private Long mtime;
   private String payment_id;
   private String order_id;
   private String paymethod;
   private String memo;
   private Double import_money;
   private Double explode_money;
   private Double member_advance;
   private Double shop_advance;
   private String disabled;

   @PrimaryKeyField
   public int getLog_id()
   {
     return this.log_id;
   }

   public void setLog_id(int logId) {
     this.log_id = logId;
   }

   public int getMember_id() {
     return this.member_id;
   }

   public void setMember_id(int memberId) {
     this.member_id = memberId;
   }

   public Double getMoney() {
     return this.money;
   }

   public void setMoney(Double money) {
     this.money = money;
   }

   public String getMessage() {
     return this.message;
   }

   public void setMessage(String message) {
     this.message = message;
   }

   public Long getMtime() {
     return this.mtime;
   }

   public void setMtime(Long mtime) {
     this.mtime = mtime;
   }

   public String getPayment_id() {
     return this.payment_id;
   }

   public void setPayment_id(String payment_id) {
     this.payment_id = payment_id;
   }

   public String getOrder_id() {
     return this.order_id;
   }

   public void setOrder_id(String order_id) {
     this.order_id = order_id;
   }

   public String getPaymethod() {
     return this.paymethod;
   }

   public void setPaymethod(String paymethod) {
     this.paymethod = paymethod;
   }

   public String getMemo() {
     return this.memo;
   }

   public void setMemo(String memo) {
     this.memo = memo;
   }

   public Double getImport_money() {
     return this.import_money;
   }

   public void setImport_money(Double importMoney) {
     this.import_money = importMoney;
   }

   public Double getExplode_money() {
     return this.explode_money;
   }

   public void setExplode_money(Double explodeMoney) {
     this.explode_money = explodeMoney;
   }

   public Double getMember_advance() {
     return this.member_advance;
   }

   public void setMember_advance(Double memberAdvance) {
     this.member_advance = memberAdvance;
   }

   public Double getShop_advance() {
     return this.shop_advance;
   }

   public void setShop_advance(Double shopAdvance) {
     this.shop_advance = shopAdvance;
   }

   public String getDisabled() {
     return this.disabled;
   }

   public void setDisabled(String disabled) {
     this.disabled = disabled;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\AdvanceLogs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */