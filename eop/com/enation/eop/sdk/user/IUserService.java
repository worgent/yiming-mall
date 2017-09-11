package com.enation.eop.sdk.user;

import com.enation.app.base.core.model.Member;

public abstract interface IUserService
{
  public static final String CURRENT_MEMBER_KEY = "curr_member";
  
  public abstract boolean isUserLoggedIn();
  
  public abstract Integer getCurrentUserId();
  
  public abstract Integer getCurrentSiteId();
  
  public abstract Integer getCurrentManagerId();
  
  public abstract Member getCurrentMember();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\user\IUserService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */