 package com.enation.app.b2b2c.core.tag.groupbuy;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;







 @Component
 @Scope("prototype")
 public class MyGroupBuyListTag
   extends BaseFreeMarkerTag
 {
   private IGroupBuyManager groupBuyManager;

   protected Object exec(Map arg0)
     throws TemplateModelException
   {
     StoreMember storeMember = (StoreMember)ThreadContextHolder.getSessionContext().getAttribute("curr_member");
     if (storeMember == null) {
       return null;
     }
     int page = getPage();
     int pageSize = getPageSize();

     HttpServletRequest request = getRequest();

     Map params = new HashMap();
     params.put("gb_name", request.getParameter("gb_name"));
     params.put("gb_status", request.getParameter("gb_status"));
     Page webpage = this.groupBuyManager.listByStoreId(page, pageSize, storeMember.getStore_id().intValue(), params);
     return webpage;
   }

   public IGroupBuyManager getGroupBuyManager() {
     return this.groupBuyManager;
   }

   public void setGroupBuyManager(IGroupBuyManager groupBuyManager) { this.groupBuyManager = groupBuyManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\groupbuy\MyGroupBuyListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */