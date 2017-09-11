 package com.enation.eop.processor;

 import com.enation.eop.processor.backend.BackendProcessor;
 import com.enation.eop.processor.facade.FacadePageProcessor;
 import com.enation.eop.processor.facade.ResourceProcessor;
 import com.enation.eop.processor.facade.SiteMapProcessor;
 import com.enation.eop.processor.facade.WebResourceProcessor;
 import com.enation.eop.processor.facade.WidgetProcessor;
 import com.enation.eop.resource.IAppManager;
 import com.enation.eop.resource.model.EopApp;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.spring.SpringContextHolder;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;


















 public abstract class ProcessorFactory
 {
   public static Processor newProcessorInstance(String uri, HttpServletRequest httpRequest)
   {
     Processor processor = null;

     if (uri.startsWith("/statics"))
       return null;
     if ((uri.startsWith("/install")) && (!uri.startsWith("/install.html"))) {
       return null;
     }

     if (uri.toLowerCase().equals("/sitemap.xml")) {
       return new SiteMapProcessor();
     }

     if (uri.toLowerCase().equals("/robots.txt")) {
       return null;
     }

     IAppManager appManager = (IAppManager)SpringContextHolder.getBean("appManager");

     List<EopApp> appList = appManager.list();
     String path = httpRequest.getServletPath();
     for (EopApp app : appList) {
       if (app.getDeployment() != 1)
       {

         if (path.startsWith(app.getPath() + "/admin")) {
           return new BackendProcessor();
         }

         if (path.startsWith(app.getPath()))
           return null;
       }
     }
     if ((uri.endsWith(".action")) || (uri.endsWith(".do"))) return null;
     if (uri.startsWith("/api"))
       return null;
     if (uri.startsWith("/validcode"))
       return null;
     if (uri.startsWith("/commons"))
       return null;
     if (uri.startsWith("/editor/"))
       return null;
     if (uri.startsWith("/eop/"))
       return null;
     if (uri.startsWith("/test/"))
       return null;
     if (uri.endsWith("favicon.ico")) {
       return null;
     }
     if (uri.indexOf("/headerresource") >= 0) {
       return new ResourceProcessor();
     }

     if (uri.startsWith("/resource/")) {
       return new WebResourceProcessor();
     }

     if (isExinclude(uri)) {
       return null;
     }
     if (uri.startsWith("/admin/"))
       return new BackendProcessor();
     if (uri.startsWith("/widget"))
     {
       processor = new WidgetProcessor();
     }
     else {
       if ((uri.endsWith(".action")) || (uri.endsWith(".do")))
         return null;
       if (EopSetting.TEMPLATEENGINE.equals("on"))
         processor = new FacadePageProcessor();
     }
     return processor;
   }

   private static boolean isExinclude(String uri) {
     String[] exts = { "jpg", "gif", "js", "png", "css", "doc", "xls", "swf" };
     for (String ext : exts) {
       if (uri.toUpperCase().endsWith(ext.toUpperCase())) {
         return true;
       }
     }
     return false;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\ProcessorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */