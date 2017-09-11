package com.enation.app.shop.core.plugin.member;

import com.enation.app.base.core.model.Member;

public abstract interface IMemberTabShowEvent
{
  public abstract String getTabName(Member paramMember);
  
  public abstract int getOrder();
  
  public abstract boolean canBeExecute(Member paramMember);
  
  public abstract String onShowMemberDetailHtml(Member paramMember);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\member\IMemberTabShowEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */