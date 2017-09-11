 package com.enation.app.shop.core.model;

 import java.io.Serializable;











 public class PaymentLog
   implements Serializable
 {
   private Integer payment_id;
   private int order_id;
   private String order_sn;
   private Integer member_id;
   private String pay_user;
   private Double money;
   private long pay_date;
   private String pay_method;
   private String remark;
   private int type;
   private Integer status;
   private Long create_time;
   private String sn;
   private Double market_point;
   private Double credit;
   private String admin_user;
   private Double paymoney;

   public Integer getPayment_id()
   {
     return this.payment_id;
   }

   public void setPayment_id(Integer payment_id) {
     this.payment_id = payment_id;
   }

   public String getOrder_sn() {
     return this.order_sn;
   }

   public void setOrder_sn(String order_sn) {
     this.order_sn = order_sn;
   }

   public Integer getMember_id() {
     return this.member_id;
   }

   public void setMember_id(Integer member_id) {
     this.member_id = member_id;
   }

   public String getPay_user() {
     return this.pay_user;
   }

   public void setPay_user(String pay_user) {
     this.pay_user = pay_user;
   }

   public Double getMoney() {
     return this.money;
   }

   public void setMoney(Double money) {
     this.money = money;
   }

   public String getPay_method() {
     return this.pay_method;
   }

   public void setPay_method(String pay_method) {
     this.pay_method = pay_method;
   }

   public String getRemark() {
     return this.remark;
   }

   public void setRemark(String remark) {
     this.remark = remark;
   }

   public Integer getStatus()
   {
     return this.status;
   }

   public void setStatus(Integer status) {
     this.status = status;
   }

   public Long getCreate_time() {
     return this.create_time;
   }

   public void setCreate_time(Long create_time) {
     this.create_time = create_time;
   }

   public String getSn() {
     return this.sn;
   }

   public void setSn(String sn) {
     this.sn = sn;
   }

   public long getPay_date() {
     return this.pay_date;
   }

   public void setPay_date(long pay_date) {
     this.pay_date = pay_date;
   }

   public int getOrder_id() {
     return this.order_id;
   }

   public void setOrder_id(int order_id) {
     this.order_id = order_id;
   }

   public int getType() {
     return this.type;
   }

   public void setType(int type) {
     this.type = type;
   }

   public Double getMarket_point() {
     return this.market_point;
   }

   public void setMarket_point(Double market_point) {
     this.market_point = market_point;
   }

   public Double getCredit() {
     return this.credit;
   }

   public void setCredit(Double credit) {
     this.credit = credit;
   }

   public String getAdmin_user() {
     return this.admin_user;
   }

   public void setAdmin_user(String admin_user) {
     this.admin_user = admin_user;
   }

   public Double getPaymoney() {
     return this.paymoney;
   }

   public void setPaymoney(Double paymoney) {
     this.paymoney = paymoney;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\PaymentLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */