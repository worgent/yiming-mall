 package com.enation.eop.sdk.user.impl;

 import com.enation.app.base.core.model.Member;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserContext;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import org.apache.log4j.Logger;

 public final class UserServiceImpl
   implements IUserService
 {
   private UserContext userContext;
   protected static final Logger logger = Logger.getLogger(UserServiceImpl.class);




   public UserServiceImpl()
   {
     WebSessionContext<UserContext> webSessionContext = ThreadContextHolder.getSessionContext();

     this.userContext = ((UserContext)webSessionContext.getAttribute("usercontext"));
   }





   public Integer getCurrentSiteId()
   {
     if (isUserLoggedIn()) {
       return this.userContext.getSiteid();
     }
     throw new IllegalStateException("The current user is not logged in.");
   }

   public Integer getCurrentUserId()
   {
     if (isUserLoggedIn()) {
       return this.userContext.getUserid();
     }
     throw new IllegalStateException("The current user is not logged in.");
   }

   public boolean isUserLoggedIn()
   {
     if (this.userContext == null) {
       return false;
     }
     return true;
   }

   public Integer getCurrentManagerId()
   {
     if (isUserLoggedIn()) {
       return this.userContext.getManagerid();
     }
     throw new IllegalStateException("The current user is not logged in.");
   }

   public Member getCurrentMember()
   {
     Member member = (Member)ThreadContextHolder.getSessionContext().getAttribute("curr_member");





















     return member;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\user\impl\UserServiceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */