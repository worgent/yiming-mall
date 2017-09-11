 package com.enation.eop.processor.core;

 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.opensymphony.xwork2.ActionInvocation;
 import com.opensymphony.xwork2.interceptor.Interceptor;






 public class EopPermissionInterceptor
   implements Interceptor
 {
   public void destroy() {}

   public void init() {}

   public String intercept(ActionInvocation inv)
     throws Exception
   {
     Integer userid = EopContext.getContext().getCurrentSite().getUserid();
     if (userid.intValue() != 1) {
       return "error";
     }
     String result = inv.invoke();
     return result;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\core\EopPermissionInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */