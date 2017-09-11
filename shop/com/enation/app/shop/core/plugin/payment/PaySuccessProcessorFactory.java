 package com.enation.app.shop.core.plugin.payment;

 import com.enation.framework.context.spring.SpringContextHolder;







 public final class PaySuccessProcessorFactory
 {
   public static IPaySuccessProcessor getProcessor(String ordertype)
   {
     if ("s".equals(ordertype))
     {
       return (IPaySuccessProcessor)SpringContextHolder.getBean("standardOrderPaySuccessProcessor");
     }
     if ("credit".equals(ordertype))
     {
       return (IPaySuccessProcessor)SpringContextHolder.getBean("creditPaySuccessProcessor");
     }
     if ("b".equals(ordertype))
     {
       return (IPaySuccessProcessor)SpringContextHolder.getBean("b2b2cOrderPaySuccessProcessor");
     }
     return null;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\payment\PaySuccessProcessorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */