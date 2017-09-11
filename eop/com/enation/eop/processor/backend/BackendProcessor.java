 package com.enation.eop.processor.backend;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.eop.processor.Processor;
 import com.enation.eop.processor.core.Response;
 import com.enation.eop.processor.core.StringResponse;
 import com.enation.eop.resource.IAdminThemeManager;
 import com.enation.eop.resource.model.AdminTheme;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.util.RequestUtil;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;






 public class BackendProcessor
   implements Processor
 {
   public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest)
   {
     IAdminUserManager adminUserManager = (IAdminUserManager)SpringContextHolder.getBean("adminUserManager");
     AdminUser adminUser = adminUserManager.getCurrentUser();
     Response response = new StringResponse();

     String ctx = httpRequest.getContextPath();

     if ("/".equals(ctx)) {
       ctx = "";
     }

     String uri = httpRequest.getServletPath();
     String redirectUrl = "";
     if (uri.startsWith("/admin")) {
       if (adminUser == null) {
         redirectUrl = ctx + "/admin/backendUi!login.do";
       } else {
         redirectUrl = ctx + "/admin/backendUi!main.do";
       }
       response.setContent(redirectUrl);
       response.setStatusCode(-1);
     }
     else {
       if (uri.startsWith("/core/admin/adminUser!login.do")) {
         response.setStatusCode(-2);
         return response;
       }

       if (adminUser == null) {
         String referer = RequestUtil.getRequestUrl(httpRequest);
         response.setContent(ctx + "/admin/backendUi!login.do?timeout=yes&referer=" + referer);
         response.setStatusCode(-1);
       } else {
         EopSite site = EopContext.getContext().getCurrentSite();
         httpRequest.setAttribute("site", site);
         httpRequest.setAttribute("ctx", ctx);
         httpRequest.setAttribute("theme", getAdminTheme(site.getAdminthemeid().intValue()));
         response.setStatusCode(-2);
       }
     }



     return response;
   }

   private String getAdminTheme(int themeid)
   {
     IAdminThemeManager adminThemeManager = (IAdminThemeManager)SpringContextHolder.getBean("adminThemeManager");

     AdminTheme theTheme = adminThemeManager.get(Integer.valueOf(themeid));
     String theme = "default";
     if (theTheme != null) {
       theme = theTheme.getPath();
     }
     return theme;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\backend\BackendProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */