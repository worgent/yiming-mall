 package com.enation.app.shop.core.model;







 public enum PaymentLogType
 {
   receivable("收款单", 1),  payable("退款款单", 2);

   private String name;
   private int value;

   private PaymentLogType(String _name, int _value) {
     this.name = _name;
     this.value = _value;
   }

   public String getName() {
     return this.name;
   }

   public int getValue() { return this.value; }

   public static PaymentLogType valueOf(int status)
   {
     PaymentLogType[] list = values();
     for (PaymentLogType log : list) {
       if (log.getValue() == status) {
         return log;
       }
     }
     return null;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\PaymentLogType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */