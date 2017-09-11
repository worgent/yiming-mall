 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.eop.processor.core.UrlNotFoundException;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.stereotype.Component;







 @Component
 public class MyGoodsListTag
   extends BaseFreeMarkerTag
 {
   private IStoreGoodsManager storeGoodsManager;
   private IStoreMemberManager storeMemberManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     StoreMember member = this.storeMemberManager.getStoreMember();
     if (member == null) {
       HttpServletResponse response = ThreadContextHolder.getHttpResponse();
       try {
         response.sendRedirect("login.html");
       } catch (IOException e) {
         throw new UrlNotFoundException();
       }
     }
     Map result = new HashMap();
     int pageSize = 10;
     int disable = request.getParameter("disable") == null ? 0 : Integer.parseInt(request.getParameter("disable"));
     String page = request.getParameter("page") == null ? "1" : request.getParameter("page");
     String store_cat = request.getParameter("store_cat");
     String goodsName = request.getParameter("goodsName");
     String market_enable = request.getParameter("market_enable") == null ? "-1" : request.getParameter("market_enable");

     result.put("store_id", member.getStore_id());
     result.put("disable", Integer.valueOf(disable));
     result.put("store_cat", store_cat);
     result.put("goodsName", goodsName);
     result.put("market_enable", Integer.valueOf(Integer.parseInt(market_enable)));
     Page storegoods = this.storeGoodsManager.storeGoodsList(Integer.valueOf(Integer.parseInt(page)), Integer.valueOf(pageSize), result);


     Long totalCount = Long.valueOf(storegoods.getTotalCount());

     result.put("page", page);
     result.put("pageSize", Integer.valueOf(pageSize));
     result.put("totalCount", totalCount);
     result.put("storegoods", storegoods);
     return result;
   }

   public IStoreGoodsManager getStoreGoodsManager() { return this.storeGoodsManager; }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
     this.storeGoodsManager = storeGoodsManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\MyGoodsListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */