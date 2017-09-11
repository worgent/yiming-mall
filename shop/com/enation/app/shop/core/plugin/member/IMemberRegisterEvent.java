package com.enation.app.shop.core.plugin.member;

import com.enation.app.base.core.model.Member;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IMemberRegisterEvent
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void onRegister(Member paramMember);
}


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\member\IMemberRegisterEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */