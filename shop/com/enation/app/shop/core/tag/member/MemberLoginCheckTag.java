 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.RequestUtil;
 import freemarker.template.TemplateModelException;
 import java.io.IOException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;









 @Component
 @Scope("prototype")
 public class MemberLoginCheckTag
   extends BaseFreeMarkerTag
 {
   private IMemberManager memberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     String loginUrl = (String)params.get("login_url");

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String curr_url = RequestUtil.getRequestUrl(request);

     String ctx = getRequest().getContextPath();
     if ("/".equals(ctx)) {
       ctx = "";
     }
     loginUrl = ctx + "/login.html?forward=" + curr_url;

     Member member = UserServiceFactory.getUserService().getCurrentMember();
     if (member == null) {
       HttpServletResponse response = ThreadContextHolder.getHttpResponse();
       try {
         response.sendRedirect(loginUrl);
         return null;
       } catch (IOException e) {
         e.printStackTrace();
         return null;
       }
     }
     return member;
   }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) { this.memberManager = memberManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\MemberLoginCheckTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */