 package com.enation.app.base.component.widget.header;

 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.StringUtil;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;








 @Component("header")
 @Scope("prototype")
 public class HeaderWidget
   extends AbstractWidget
 {
   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     setPageName("header");
     EopSite site = EopContext.getContext().getCurrentSite();

     String ctx = ThreadContextHolder.getHttpRequest().getContextPath();
     putData("ctx", ctx);

     if (getData("pagetitle") == null) {
       putData("pagetitle", StringUtil.isEmpty(site.getTitle()) ? site.getSitename() : site.getTitle());
     }
     if (getData("keywords") == null) {
       putData("keywords", site.getKeywords());
     }
     if (getData("description") == null) {
       putData("description", site.getDescript());
     }
     putData("ico", site.getIcofile());
     putData("logo", site.getLogofile());
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\header\HeaderWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */