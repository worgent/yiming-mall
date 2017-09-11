 package com.enation.eop.resource.impl;

 import com.enation.eop.processor.core.EopException;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;







 public final class UserUtil
 {
   private static IUserService userService;

   public static void validUser(Integer userid)
   {
     userService = UserServiceFactory.getUserService();

     if (!userid.equals(userService.getCurrentUserId())) {
       throw new EopException("非法操作");
     }
   }

   public IUserService getUserService() {
     return userService;
   }

   public void setUserService(IUserService userService) {
     userService = userService;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\impl\UserUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */