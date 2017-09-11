package com.enation.app.shop.core.plugin.member;

import com.enation.app.base.core.model.Member;

public abstract interface IMemberLoginEvent
{
  public abstract void onLogin(Member paramMember, Long paramLong);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\member\IMemberLoginEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */