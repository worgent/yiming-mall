 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreDlyCenterManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;






 @Component
 public class StoreDlyCenterListTag
   extends BaseFreeMarkerTag
 {
   private IStoreMemberManager storeMemberManager;
   private IStoreDlyCenterManager storeDlyCenterManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     StoreMember member = this.storeMemberManager.getStoreMember();
     List list = this.storeDlyCenterManager.getDlyCenterList(member.getStore_id());
     return list;
   }


   public IStoreDlyCenterManager getStoreDlyCenterManager()
   {
     return this.storeDlyCenterManager;
   }

   public void setStoreDlyCenterManager(IStoreDlyCenterManager storeDlyCenterManager) {
     this.storeDlyCenterManager = storeDlyCenterManager;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreDlyCenterListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */