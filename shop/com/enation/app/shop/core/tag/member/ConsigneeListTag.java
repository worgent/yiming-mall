 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberAddressManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import freemarker.template.TemplateMethodModel;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;















 @Component
 @Scope("prototype")
 public class ConsigneeListTag
   implements TemplateMethodModel
 {
   private IMemberAddressManager memberAddressManager;

   public Object exec(List args)
     throws TemplateModelException
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     if (member == null) {
       throw new TemplateModelException("未登陆不能使用此标签[ConsigneeListTag]");
     }

     return this.memberAddressManager.listAddress();
   }

   public IMemberAddressManager getMemberAddressManager()
   {
     return this.memberAddressManager;
   }

   public void setMemberAddressManager(IMemberAddressManager memberAddressManager) { this.memberAddressManager = memberAddressManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\ConsigneeListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */