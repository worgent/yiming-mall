 package com.enation.app.base.core.tag;

 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;






 @Component
 @Scope("prototype")
 public class HeaderTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     Map siteHeader = new HashMap();
     EopSite site = EopContext.getContext().getCurrentSite();
     String ctx = ThreadContextHolder.getHttpRequest().getContextPath();
     siteHeader.put("title", StringUtil.isEmpty(site.getTitle()) ? site.getSitename() : site.getTitle());
     siteHeader.put("siteName", StringUtil.isEmpty(site.getSitename()) ?  "b2b2c平台" : site.getSitename());
     siteHeader.put("keywords", site.getKeywords());
     siteHeader.put("description", site.getDescript());
     siteHeader.put("ctx", ctx);
     return siteHeader;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\tag\HeaderTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */