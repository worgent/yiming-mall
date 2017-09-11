 package com.enation.app.shop.core.model;





 public enum SellBackStatus
 {
   create("新建退货单", 0),  apply("申请退货", 1),  in_storage("退货入库", 2),  close_payable("退货结算", 3),  cancel("取消退货", 4),  partial_storage("部分入库", 5);

   private String name;
   private int value;

   private SellBackStatus(String _name, int _value)
   {
     this.name = _name;
     this.value = _value;
   }

   public String getName() {
     return this.name;
   }

   public int getValue() { return this.value; }

   public static SellBackStatus valueOf(int status)
   {
     SellBackStatus[] statusList = values();
     for (SellBackStatus sellBackStatus : statusList) {
       if (sellBackStatus.getValue() == status) {
         return sellBackStatus;
       }
     }
     return null;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\SellBackStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */