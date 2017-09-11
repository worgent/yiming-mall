 package com.enation.app.b2b2c.core.tag.storesite;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.INavigationManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;





 @Component
 public class StoreNavigationTag
   extends BaseFreeMarkerTag
 {
   private INavigationManager navigationManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer storeid = (Integer)params.get("store_id");
     if (storeid == null) {
       StoreMember storeMember = this.storeMemberManager.getStoreMember();
       storeid = storeMember.getStore_id();
     }
     List list = this.navigationManager.getNavicationList(storeid);
     return list;
   }

   public INavigationManager getNavigationManager() { return this.navigationManager; }

   public void setNavigationManager(INavigationManager navigationManager) {
     this.navigationManager = navigationManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\storesite\StoreNavigationTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */