 package com.enation.eop.sdk.context;

 import com.enation.app.base.core.model.MultiSite;
 import com.enation.app.base.core.service.IMultiSiteManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.eop.resource.ISiteManager;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.framework.component.IComponentManager;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import javax.servlet.http.HttpSession;

 public class SaasEopContextIniter
 {
   public static boolean installing = false;

   public static void init(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
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
     EopSite site = null;
     if ((servletPath.startsWith("/install")) && (!servletPath.startsWith("/install.html"))) {
       site = new EopSite();
       site.setUserid(Integer.valueOf(1));
       site.setId(Integer.valueOf(1));
       site.setThemeid(Integer.valueOf(1));
       EopContext context = new EopContext();
       context.setCurrentSite(site);
       EopContext.setContext(context);

     }
     else
     {
       String domain = httpRequest.getServerName();
       ISiteManager siteManager = (ISiteManager)SpringContextHolder.getBean("siteManager");
       site = siteManager.get(domain);
       EopContext context = new EopContext();
       context.setCurrentSite(site);
       EopContext.setContext(context);
       if (site.getMulti_site().intValue() == 1) {
         IMultiSiteManager multiSiteManager = (IMultiSiteManager)SpringContextHolder.getBean("multiSiteManager");
         MultiSite multiSite = multiSiteManager.get(domain);
         context.setCurrentChildSite(multiSite);
       }
     }





     fmp.putData("ctx", httpRequest.getContextPath());
     fmp.putData("ext", EopSetting.EXTENSION);
     fmp.putData("staticserver", EopSetting.IMG_SERVER_DOMAIN);
     fmp.putData("site", site);

     if (EopSetting.INSTALL_LOCK.toUpperCase().equals("YES")) {
       IComponentManager componentManager = (IComponentManager)SpringContextHolder.getBean("componentManager");
       componentManager.saasStartComponents();
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\context\SaasEopContextIniter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */