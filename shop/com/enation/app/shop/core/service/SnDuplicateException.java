 package com.enation.app.shop.core.service;





 public class SnDuplicateException
   extends RuntimeException
 {
   public SnDuplicateException(String sn)
   {
     super("货号[" + sn + "]重复");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\SnDuplicateException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */