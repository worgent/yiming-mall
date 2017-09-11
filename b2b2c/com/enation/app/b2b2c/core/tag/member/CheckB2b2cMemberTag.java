 package com.enation.app.b2b2c.core.tag.member;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.io.IOException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.stereotype.Component;







 @Component
 public class CheckB2b2cMemberTag
   extends BaseFreeMarkerTag
 {
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     String ctx = getRequest().getContextPath();
     if ("/".equals(ctx)) {
       ctx = "";
     }

     if (this.storeMemberManager.getStoreMember() == null) {
       HttpServletResponse response = ThreadContextHolder.getHttpResponse();
       try {
         response.sendRedirect(ctx + "/store/login.html");
         return null;
       } catch (IOException e) {
         e.printStackTrace();
         return null;
       }
     }

     StoreMember member = this.storeMemberManager.getMember(this.storeMemberManager.getStoreMember().getMember_id());

     if (member == null) {
       HttpServletResponse response = ThreadContextHolder.getHttpResponse();
       try {
         response.sendRedirect(ctx + "/store/login.html");
         return null;
       } catch (IOException e) {
         e.printStackTrace();
         return null;
       }
     }
     return member;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) { this.storeMemberManager = storeMemberManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\member\CheckB2b2cMemberTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */