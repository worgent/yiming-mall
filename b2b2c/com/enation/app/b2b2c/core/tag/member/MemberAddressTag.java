 package com.enation.app.b2b2c.core.tag.member;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreMemberAddressManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.core.service.IMemberAddressManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;













 @Component
 @Scope("prototype")
 public class MemberAddressTag
   extends BaseFreeMarkerTag
 {
   private IMemberAddressManager memberAddressManager;
   private IStoreMemberAddressManager storeMemberAddressManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     StoreMember member = this.storeMemberManager.getStoreMember();
     if (member == null) {
       throw new TemplateModelException("未登陆不能使用此标签[ConsigneeListTag]");
     }
     return this.memberAddressManager.listAddress();
   }

   public IMemberAddressManager getMemberAddressManager() {
     return this.memberAddressManager;
   }

   public void setMemberAddressManager(IMemberAddressManager memberAddressManager) { this.memberAddressManager = memberAddressManager; }

   public IStoreMemberAddressManager getStoreMemberAddressManager()
   {
     return this.storeMemberAddressManager;
   }

   public void setStoreMemberAddressManager(IStoreMemberAddressManager storeMemberAddressManager)
   {
     this.storeMemberAddressManager = storeMemberAddressManager;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\member\MemberAddressTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */