 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreTemplateManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;






 @Component
 public class StoreTemplateTag
   extends BaseFreeMarkerTag
 {
   private IStoreMemberManager storeMemberManager;
   private IStoreTemplateManager storeTemplateManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     StoreMember member = this.storeMemberManager.getStoreMember();
     List list = this.storeTemplateManager.getTemplateList(member.getStore_id());
     return list;
   }

   public IStoreTemplateManager getStoreTemplateManager() {
     return this.storeTemplateManager;
   }

   public void setStoreTemplateManager(IStoreTemplateManager storeTemplateManager) {
     this.storeTemplateManager = storeTemplateManager;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreTemplateTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */