 package com.enation.app.b2b2c.core.tag.groupbuy;

 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;








 @Component
 @Scope("prototype")
 public class GroupBuyListTag
   extends BaseFreeMarkerTag
 {
   private IGroupBuyManager groupBuyManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String catid_str = request.getParameter("catid");
     String minprice_str = request.getParameter("minprice");
     String maxprice_str = request.getParameter("maxprice");
     String sort_key_str = request.getParameter("sort_key");
     String sort_type_str = request.getParameter("sort_type");

     Integer catid = Integer.valueOf(catid_str);
     Integer sort_key = Integer.valueOf(sort_key_str);
     Integer sort_type = Integer.valueOf(sort_type_str);
     Double minprice = Double.valueOf(minprice_str);
     Double maxprice = Double.valueOf(maxprice_str);


     return null;
   }

   public IGroupBuyManager getGroupBuyManager() {
     return this.groupBuyManager;
   }

   public void setGroupBuyManager(IGroupBuyManager groupBuyManager) { this.groupBuyManager = groupBuyManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\groupbuy\GroupBuyListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */