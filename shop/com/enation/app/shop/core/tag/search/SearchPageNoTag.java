 package com.enation.app.shop.core.tag.search;

 import com.enation.app.shop.core.utils.UrlUtils;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;








 @Component
 @Scope("prototype")
 public class SearchPageNoTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String uri = request.getServletPath();
     String page_str = UrlUtils.getParamStringValue(uri, "page");
     int page = 1;
     if ((page_str != null) && (!page_str.equals(""))) {
       page = Integer.valueOf(page_str).intValue();
     }
     return Integer.valueOf(page);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\search\SearchPageNoTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */