 package com.enation.eop.sdk.user;

 import com.enation.eop.sdk.user.impl.UserServiceImpl;






 public final class UserServiceFactory
 {
   public static int isTest = 0;
   private static IUserService userService;

   public static void set(IUserService _userService) { userService = _userService; }

   public static IUserService getUserService()
   {
     if (userService != null) {
       return userService;
     }
     return new UserServiceImpl();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\user\UserServiceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */