 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreBonusManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;










 @Component
 public class StoreBonusListTag
   extends BaseFreeMarkerTag
 {
   private IStoreBonusManager storeBonusManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer store_id = (Integer)params.get("store_id");
     if (store_id == null) {
       StoreMember storeMember = this.storeMemberManager.getStoreMember();
       store_id = storeMember.getStore_id();
     }
     List list = this.storeBonusManager.getBonusList(store_id);
     return list;
   }

   public IStoreBonusManager getStoreBonusManager() {
     return this.storeBonusManager;
   }

   public void setStoreBonusManager(IStoreBonusManager storeBonusManager) { this.storeBonusManager = storeBonusManager; }

   public IStoreMemberManager getStoreMemberManager()
   {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreBonusListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */