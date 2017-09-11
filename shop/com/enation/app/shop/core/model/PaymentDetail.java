 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;

 public class PaymentDetail {
   private Integer paymentDetail_id;
   private Integer payment_id;
   private Double pay_money;
   private long pay_date;
   private String admin_user;

   @PrimaryKeyField
   public Integer getPaymentDetail_id() {
     return this.paymentDetail_id;
   }

   public void setPaymentDetail_id(Integer paymentDetail_id) { this.paymentDetail_id = paymentDetail_id; }

   public Integer getPayment_id() {
     return this.payment_id;
   }

   public void setPayment_id(Integer payment_id) { this.payment_id = payment_id; }

   public Double getPay_money() {
     return this.pay_money;
   }

   public void setPay_money(Double pay_money) { this.pay_money = pay_money; }

   public long getPay_date() {
     return this.pay_date;
   }

   public void setPay_date(long pay_date) { this.pay_date = pay_date; }

   public String getAdmin_user() {
     return this.admin_user;
   }

   public void setAdmin_user(String admin_user) { this.admin_user = admin_user; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\PaymentDetail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */