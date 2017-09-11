 package com.enation.eop.processor.core;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.opensymphony.xwork2.ActionInvocation;
 import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;




 public class BackendRightInterceptor
   extends MethodFilterInterceptor
 {
   protected String doIntercept(ActionInvocation inv)
     throws Exception
   {
     IAdminUserManager adminUserManager = (IAdminUserManager)SpringContextHolder.getBean("adminUserManager");
     AdminUser user = adminUserManager.getCurrentUser();
     if (user == null) {
       return "not_login";
     }

     String result = inv.invoke();
     return result;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\core\BackendRightInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */