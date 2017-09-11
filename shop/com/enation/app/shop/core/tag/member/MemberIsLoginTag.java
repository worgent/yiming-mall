 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;









 @Component
 @Scope("prototype")
 public class MemberIsLoginTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     Member member = UserServiceFactory.getUserService().getCurrentMember();
     boolean isLogin = false;
     if (member != null) {
       isLogin = true;
     }
     return Boolean.valueOf(isLogin);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\MemberIsLoginTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */