 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.service.store.IStoreManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;





 @Component
 public class StoreSearchTag
   extends BaseFreeMarkerTag
 {
   private IStoreManager storeManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     Map map = new HashMap();
     map.put("name", request.getParameter("namekeyword"));
     map.put("store_credit", request.getParameter("store_credit"));
     map.put("searchType", request.getParameter("searchType"));
     String pageNo = request.getParameter("page");
     int pageSize = 10;
     pageNo = (pageNo == null) || (pageNo.equals("")) ? "1" : pageNo;
     Page page = this.storeManager.store_list(map, Integer.valueOf(1), Integer.parseInt(pageNo), 10);
     map.put("list", page);
     map.put("totalCount", Long.valueOf(page.getTotalCount()));
     map.put("pageSize", Integer.valueOf(pageSize));
     map.put("page", pageNo);
     return map;
   }

   public IStoreManager getStoreManager() { return this.storeManager; }

   public void setStoreManager(IStoreManager storeManager) {
     this.storeManager = storeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreSearchTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */