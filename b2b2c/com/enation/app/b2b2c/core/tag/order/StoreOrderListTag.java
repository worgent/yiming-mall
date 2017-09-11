 package com.enation.app.b2b2c.core.tag.order;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.order.IStoreOrderManager;
 import com.enation.eop.processor.core.UrlNotFoundException;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.stereotype.Component;





 @Component
 public class StoreOrderListTag
   extends BaseFreeMarkerTag
 {
   private IStoreOrderManager storeOrderManager;
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

     int pageSize = 10;
     String page = request.getParameter("page") == null ? "1" : request.getParameter("page");
     String order_state = request.getParameter("order_state");
     String keyword = request.getParameter("keyword");
     String buyerName = request.getParameter("buyerName");
     String startTime = request.getParameter("startTime");
     String endTime = request.getParameter("endTime");

     if ((!StringUtil.isEmpty(keyword)) && (!StringUtil.isEmpty(EopSetting.ENCODING))) {
       keyword = StringUtil.to(keyword, EopSetting.ENCODING);
     }
     if ((!StringUtil.isEmpty(buyerName)) && (!StringUtil.isEmpty(EopSetting.ENCODING))) {
       buyerName = StringUtil.to(buyerName, EopSetting.ENCODING);
     }

     Map result = new HashMap();
     result.put("keyword", keyword);
     result.put("order_state", order_state);
     result.put("buyerName", buyerName);
     result.put("startTime", startTime);
     result.put("endTime", endTime);

     Page orderList = this.storeOrderManager.storeOrderList(Integer.valueOf(Integer.parseInt(page)), Integer.valueOf(pageSize), member.getStore_id(), result);

     Long totalCount = Long.valueOf(orderList.getTotalCount());

     result.put("page", page);
     result.put("pageSize", Integer.valueOf(pageSize));
     result.put("totalCount", totalCount);
     result.put("storeOrder", orderList);
     return result;
   }

   public IStoreOrderManager getStoreOrderManager() { return this.storeOrderManager; }

   public void setStoreOrderManager(IStoreOrderManager storeOrderManager) {
     this.storeOrderManager = storeOrderManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\order\StoreOrderListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */