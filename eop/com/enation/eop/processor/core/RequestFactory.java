 package com.enation.eop.processor.core;







 public abstract class RequestFactory
 {
   public static Request getRequest(int model)
   {
     Request request = null;

     if (model == 1) {
       request = new RemoteRequest();
     }
     if (model == 0) {
       request = new LocalRequest();
     }
     return request;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\core\RequestFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */