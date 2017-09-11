 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.MemberAddress;
 import com.enation.app.shop.core.service.IMemberAddressManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.stereotype.Component;









 @Component
 public class DefaultConsigneeTag
   extends BaseFreeMarkerTag
 {
   private IMemberAddressManager memberAddressManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Member member = UserServiceFactory.getUserService().getCurrentMember();
     if (member == null) {
       throw new TemplateModelException("未登录，不能使用此标签");
     }
     Integer memberid = member.getMember_id();
     MemberAddress address = this.memberAddressManager.getMemberDefault(memberid);
     if (address == null) {
       address = new MemberAddress();
     }

     return address;
   }

   public IMemberAddressManager getMemberAddressManager() {
     return this.memberAddressManager;
   }

   public void setMemberAddressManager(IMemberAddressManager memberAddressManager) {
     this.memberAddressManager = memberAddressManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\DefaultConsigneeTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */