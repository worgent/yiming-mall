 package com.enation.app.shop.core.tag;

 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;






 @Component
 @Scope("prototype")
 public class MarketPointTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer userpoint = (Integer)ThreadContextHolder.getSessionContext().getAttribute("use_point_num");

     if (userpoint == null) userpoint = Integer.valueOf(0);
     return userpoint;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\MarketPointTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */