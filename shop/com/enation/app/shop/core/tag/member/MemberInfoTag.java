 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;


















 @Component
 @Scope("prototype")
 public class MemberInfoTag
   extends BaseFreeMarkerTag
 {
   private IMemberManager memberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     String mustlogin = (String)params.get("mustlogin");

     Member member = UserServiceFactory.getUserService().getCurrentMember();

     if (member == null) {
       throw new TemplateModelException("未登陆不能使用此标签[MemberInfoTag]");
     }

     int memberid = member.getMember_id().intValue();
     member = this.memberManager.get(Integer.valueOf(memberid));

     return member;
   }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) { this.memberManager = memberManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\MemberInfoTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */