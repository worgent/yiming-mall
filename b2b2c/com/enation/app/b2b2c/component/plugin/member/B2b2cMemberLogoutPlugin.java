 package com.enation.app.b2b2c.component.plugin.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberLogoutEvent;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;




 @Component
 public class B2b2cMemberLogoutPlugin
   extends AutoRegisterPlugin
   implements IMemberLogoutEvent
 {
   public void onLogout(Member member)
   {
     ThreadContextHolder.getSessionContext().removeAttribute("curr_store_member");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\member\B2b2cMemberLogoutPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */