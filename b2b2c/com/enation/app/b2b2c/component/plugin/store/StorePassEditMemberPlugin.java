 package com.enation.app.b2b2c.component.plugin.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.model.store.Store;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;


 @Component
 public class StorePassEditMemberPlugin
   extends AutoRegisterPlugin
   implements IAfterStorePassEvent
 {
   private IStoreMemberManager storeMemberManager;

   public void AfterStorePassEvent(Store store)
   {
     StoreMember member = this.storeMemberManager.getMember(Integer.valueOf(store.getMember_id()));
     member.setStore_id(Integer.valueOf(store.getStore_id()));
     member.setIs_store(Integer.valueOf(1));
     this.storeMemberManager.edit(member);
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\store\StorePassEditMemberPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */