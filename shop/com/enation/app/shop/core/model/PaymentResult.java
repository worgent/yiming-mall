 package com.enation.app.shop.core.model;


 public class PaymentResult
 {
   private int result;

   private String ordersn;

   private String error;

   public int getResult()
   {
     return this.result;
   }

   public void setResult(int result) { this.result = result; }

   public String getOrdersn() {
     return this.ordersn;
   }

   public void setOrdersn(String ordersn) { this.ordersn = ordersn; }

   public String getError() {
     return this.error;
   }

   public void setError(String error) { this.error = error; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\PaymentResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */