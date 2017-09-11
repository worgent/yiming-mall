 package com.enation.app.base.component.widget.nav;

 import com.enation.eop.sdk.widget.AbstractWidget;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;











 @Scope("prototype")
 public class SiteNavWidget
   extends AbstractWidget
 {
   protected void config(Map<String, String> params) {}

   protected void display(Map<String, String> params)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     List<Nav> navList = (List)request.getAttribute("site_nav_list");
     navList = navList == null ? new ArrayList() : navList;
     putData("navList", navList);
     request.removeAttribute("site_nav_list");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-base-core.jar!\com\enation\app\base\component\widget\nav\SiteNavWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */