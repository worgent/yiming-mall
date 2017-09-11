 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.io.IOException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.stereotype.Component;








 @Component
 public class CheckUrlTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer show_c = (Integer)params.get("show_c");
     Integer store_id = (Integer)params.get("store_id");

     String ctx = getRequest().getContextPath();
     if ("/".equals(ctx)) {
       ctx = "";
     }

     HttpServletResponse response = ThreadContextHolder.getHttpResponse();
     if ((show_c == null) || (show_c.intValue() == 0) || (store_id == null) || (store_id.intValue() == 0)) {
       try {
         response.sendRedirect(ctx + "/404.html");
         return null;
       } catch (IOException e) {
         e.printStackTrace();
       }
     }
     return null;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\CheckUrlTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */