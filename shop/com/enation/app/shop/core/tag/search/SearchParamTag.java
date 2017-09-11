 package com.enation.app.shop.core.tag.search;

 import com.enation.app.shop.core.utils.UrlUtils;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;









 @Component
 @Scope("prototype")
 public class SearchParamTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     String name = (String)params.get("name");

     if (StringUtil.isEmpty(name)) {
       throw new TemplateModelException("必须传递name参数");
     }

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String uri = request.getServletPath();
     String value = UrlUtils.getParamStringValue(uri, name);

     return value;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\search\SearchParamTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */