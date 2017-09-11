 package com.enation.app.b2b2c.component.plugin.store;

 import com.enation.app.b2b2c.core.model.store.Store;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;





 @Component
 public class StoreAfterApplyPlugin
   extends AutoRegisterPlugin
   implements IAfterStoreApplyEvent
 {
   private IDaoSupport daoSupport;
   private IStoreMemberManager storeMemberManager;

   public void IAfterStoreApplyEvent(Store store)
   {
     String sql = "update es_member set is_store=2 where member_id=?";
     this.daoSupport.execute(sql, new Object[] { Integer.valueOf(store.getMember_id()) });
     ThreadContextHolder.getSessionContext().setAttribute("curr_store_member", this.storeMemberManager.getMember(Integer.valueOf(store.getMember_id())));
   }

   public IDaoSupport getDaoSupport() { return this.daoSupport; }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\store\StoreAfterApplyPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */