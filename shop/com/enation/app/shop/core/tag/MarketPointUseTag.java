 package com.enation.app.shop.core.tag;

 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;








 @Component
 @Scope("prototype")
 public class MarketPointUseTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     Integer userpoint = Integer.valueOf(0);
     String use_point = request.getParameter("use_point");

     if ("yes".equals(use_point)) {
       String use_point_num = request.getParameter("point_num");
       userpoint = StringUtil.toInt(use_point_num, Integer.valueOf(0));
       ThreadContextHolder.getSessionContext().setAttribute("use_point_num", userpoint);
     }
     else if (("clean".equals(use_point)) || ("no".equals(use_point))) {
       ThreadContextHolder.getSessionContext().removeAttribute("use_point_num");
       userpoint = Integer.valueOf(0);
     }

     userpoint = (Integer)ThreadContextHolder.getSessionContext().getAttribute("use_point_num");

     if (userpoint == null) { userpoint = Integer.valueOf(0);
     }
     return userpoint;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\MarketPointUseTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */