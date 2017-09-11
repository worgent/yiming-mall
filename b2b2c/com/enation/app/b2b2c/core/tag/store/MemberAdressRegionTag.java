 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreMemberAddressManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.stereotype.Component;










 @Component
 public class MemberAdressRegionTag
   extends BaseFreeMarkerTag
 {
   private IStoreMemberManager storeMemberManager;
   private IStoreMemberAddressManager storeMemberAddressManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     StoreMember storeMember = this.storeMemberManager.getStoreMember();
     Integer regionid = this.storeMemberAddressManager.getRegionid(storeMember.getMember_id());
     return regionid;
   }

   public IStoreMemberAddressManager getStoreMemberAddressManager() {
     return this.storeMemberAddressManager;
   }

   public void setStoreMemberAddressManager(IStoreMemberAddressManager storeMemberAddressManager) {
     this.storeMemberAddressManager = storeMemberAddressManager;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\MemberAdressRegionTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */