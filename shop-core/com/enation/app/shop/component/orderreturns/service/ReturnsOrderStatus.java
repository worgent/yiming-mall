 package com.enation.app.shop.component.orderreturns.service;





 public abstract class ReturnsOrderStatus
 {
   public static final int APPLY_SUB = 0;




   public static final int APPLY_REFUSE = 1;




   public static final int APPLY_PASSED = 2;




   public static final int GOODS_REC = 3;




   public static final int APPLY_END = 4;





   public static String getOrderStatusText(int status)
   {
     String text = "";
     switch (status) {
     case 0:
       text = "申请已提交";
       break;
     case 1:
       text = "已拒绝 ";
       break;
     case 2:
       text = "已通过审核";
       break;
     case 3:
       text = "已收货(换货已发货)";
       break;
     case 4:
       text = "完成";
     }


     return text;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\orderreturns\service\ReturnsOrderStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */