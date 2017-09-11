package com.enation.app.b2b2c.core.service.member;

import com.enation.app.b2b2c.core.model.member.StoreMember;

public abstract interface IStoreMemberManager
{
  public static final String CURRENT_STORE_MEMBER_KEY = "curr_store_member";
  
  public abstract void edit(StoreMember paramStoreMember);
  
  public abstract StoreMember getMember(Integer paramInteger);
  
  public abstract StoreMember getMember(String paramString);


  public abstract StoreMember getMemberByStoreId(int storeId);

  public abstract StoreMember getStoreMember();
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\member\IStoreMemberManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */