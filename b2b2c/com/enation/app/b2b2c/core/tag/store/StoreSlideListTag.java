 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.store.IStoreSildeManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.stereotype.Component;





 @Component
 public class StoreSlideListTag
   extends BaseFreeMarkerTag
 {
   private IStoreSildeManager storeSildeManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer storeid = (Integer)params.get("storeid");
     if (storeid == null) {
       StoreMember member = this.storeMemberManager.getStoreMember();
       storeid = member.getStore_id();
     }
     return this.storeSildeManager.list(storeid);
   }

   public IStoreSildeManager getStoreSildeManager() { return this.storeSildeManager; }

   public void setStoreSildeManager(IStoreSildeManager storeSildeManager) {
     this.storeSildeManager = storeSildeManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreSlideListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */