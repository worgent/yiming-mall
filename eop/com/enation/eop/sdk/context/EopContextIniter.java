 package com.enation.eop.sdk.context;

 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.eop.resource.ISiteManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;







 public class EopContextIniter
 {
   public static void init(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
   {
     FreeMarkerPaser.set(new FreeMarkerPaser());
     FreeMarkerPaser fmp = FreeMarkerPaser.getInstance();



     HttpSession session = httpRequest.getSession();
     ThreadContextHolder.getSessionContext().setSession(session);
     EopContext.setHttpRequest(httpRequest);
     ThreadContextHolder.setHttpRequest(httpRequest);
     ThreadContextHolder.setHttpResponse(httpResponse);
     httpRequest.setAttribute("staticserver", EopSetting.IMG_SERVER_DOMAIN);
     httpRequest.setAttribute("ext", EopSetting.EXTENSION);
     String servletPath = httpRequest.getServletPath();

     if (servletPath.startsWith("/statics")) {
       return;
     }
     if (servletPath.startsWith("/install")) {
       EopSite site = new EopSite();
       site.setUserid(Integer.valueOf(1));
       site.setId(Integer.valueOf(1));
       site.setThemeid(Integer.valueOf(1));
       EopContext context = new EopContext();
       context.setCurrentSite(site);
       EopContext.setContext(context);
     } else {
       EopContext context = new EopContext();
       EopSite site = new EopSite();
       site.setUserid(Integer.valueOf(1));
       site.setId(Integer.valueOf(1));
       site.setThemeid(Integer.valueOf(1));
       context.setCurrentSite(site);
       EopContext.setContext(context);

       ISiteManager siteManager = (ISiteManager)SpringContextHolder.getBean("siteManager");
       site = siteManager.get("localhost");

       if (isMobile()) {
         site.setMobilesite(Integer.valueOf(1));
       } else {
         site.setMobilesite(Integer.valueOf(0));
       }

       context.setCurrentSite(site);
       EopContext.setContext(context);
       fmp.putData("site", site);
     }




     fmp.putData("ctx", httpRequest.getContextPath());
     fmp.putData("ext", EopSetting.EXTENSION);
     fmp.putData("staticserver", EopSetting.IMG_SERVER_DOMAIN);
   }


   private static boolean isMobile()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String domain = request.getServerName().toLowerCase();

     if (domain.startsWith("wap.")) {
       return true;
     }

     String userAgent = request.getHeader("user-agent").toLowerCase();

     if ((userAgent.contains("android")) || (userAgent.contains("iphone")))
     {
       return true;
     }
     return false;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\context\EopContextIniter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */