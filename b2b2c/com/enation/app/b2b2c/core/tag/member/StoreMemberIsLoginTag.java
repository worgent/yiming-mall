 package com.enation.app.b2b2c.core.tag.member;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.RequestUtil;
 import freemarker.template.TemplateModelException;
 import java.io.IOException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.stereotype.Component;





 @Component
 public class StoreMemberIsLoginTag
   extends BaseFreeMarkerTag
 {
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     StoreMember member = this.storeMemberManager.getStoreMember();
     boolean isLogin = false;
     if (member != null) {
       isLogin = true;
     } else {
       if ("no".equals(params.get("redirect"))) {
         return Boolean.valueOf(isLogin);
       }

       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       HttpServletResponse response = ThreadContextHolder.getHttpResponse();
       String curr_url = RequestUtil.getRequestUrl(request);
       String loginUrl = request.getContextPath() + "/store/login.html?forward=" + curr_url;
       try {
         if ((!curr_url.equals(request.getContextPath() + "/")) && (!curr_url.equals(request.getContextPath() + "/store/index.html"))) {
           response.sendRedirect(loginUrl);
         }
       } catch (IOException e) {
         e.printStackTrace();
       }
     }
     return Boolean.valueOf(isLogin);
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\member\StoreMemberIsLoginTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */