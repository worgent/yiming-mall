 package com.enation.app.b2b2c.component.plugin.member;

 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberRegisterEvent;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.stereotype.Component;


 @Component
 public class B2b2cMemberRegisterPlugin
   extends AutoRegisterPlugin
   implements IMemberRegisterEvent
 {
   private IDaoSupport daoSupport;
   private IStoreMemberManager storeMemberManager;

   public void onRegister(Member member)
   {
     Map map = new HashMap();
     map.put("is_store", Integer.valueOf(-1));
     this.daoSupport.update("es_member", map, "member_id=" + member.getMember_id());

     ThreadContextHolder.getSessionContext().setAttribute("curr_store_member", this.storeMemberManager.getMember(member.getMember_id()));
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) { this.daoSupport = daoSupport; }

   public IStoreMemberManager getStoreMemberManager()
   {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\member\B2b2cMemberRegisterPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */