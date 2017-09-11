 package com.enation.app.b2b2c.component.plugin.member;

 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberLoginEvent;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;

 @Component
 public class B2b2cMemberLoginPlugin
   extends AutoRegisterPlugin
   implements IMemberLoginEvent
 {
   private IStoreMemberManager storeMemberManager;

   public void onLogin(Member member, Long upLogintime)
   {
     ThreadContextHolder.getSessionContext().setAttribute("curr_store_member", this.storeMemberManager.getMember(member.getMember_id()));
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\member\B2b2cMemberLoginPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */