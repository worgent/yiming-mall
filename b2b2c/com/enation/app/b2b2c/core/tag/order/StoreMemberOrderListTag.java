 package com.enation.app.b2b2c.core.tag.order;

 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.order.IStoreOrderManager;
 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberPointManger;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IPromotionManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;







 @Component
 public class StoreMemberOrderListTag
   extends BaseFreeMarkerTag
 {
   private IStoreOrderManager storeOrderManager;
   private IStoreMemberManager storeMemberManager;
   private IMemberPointManger memberPointManger;
   private IOrderManager orderManager;
   private IOrderFlowManager orderFlowManager;
   private IPromotionManager promotionManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     Member member = this.storeMemberManager.getStoreMember();
     if (member == null) {
       throw new TemplateModelException("未登陆不能使用此标签[MemberOrderListTag]");
     }
     Map result = new HashMap();
     String page = request.getParameter("page");
     page = (page == null) || (page.equals("")) ? "1" : page;
     int pageSize = 10;
     String status = request.getParameter("status");
     String keyword = request.getParameter("keyword");

     if ((!StringUtil.isEmpty(keyword)) && (!StringUtil.isEmpty(EopSetting.ENCODING))) {
       keyword = StringUtil.to(keyword, EopSetting.ENCODING);
     }

     Page ordersPage = this.storeOrderManager.pageOrders(Integer.valueOf(page).intValue(), pageSize, status, keyword);
     Long totalCount = Long.valueOf(ordersPage.getTotalCount());

     List ordersList = (List)ordersPage.getResult();
     ordersList = ordersList == null ? new ArrayList() : ordersList;

     result.put("totalCount", totalCount);
     result.put("pageSize", Integer.valueOf(pageSize));
     result.put("page", page);
     result.put("ordersList", ordersList);


     Map<String, Object> orderstatusMap = OrderStatus.getOrderStatusMap();
     for (String orderStatus : orderstatusMap.keySet()) {
       result.put(orderStatus, orderstatusMap.get(orderStatus));
     }

     if (status != null) {
       result.put("status", Integer.valueOf(status));
     }

     return result;
   }

   public IStoreOrderManager getStoreOrderManager() { return this.storeOrderManager; }

   public void setStoreOrderManager(IStoreOrderManager storeOrderManager)
   {
     this.storeOrderManager = storeOrderManager;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) { this.orderManager = orderManager; }

   public IPromotionManager getPromotionManager() {
     return this.promotionManager;
   }

   public void setPromotionManager(IPromotionManager promotionManager) { this.promotionManager = promotionManager; }

   public IOrderFlowManager getOrderFlowManager() {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) { this.orderFlowManager = orderFlowManager; }

   public IMemberPointManger getMemberPointManger() {
     return this.memberPointManger;
   }

   public void setMemberPointManger(IMemberPointManger memberPointManger) { this.memberPointManger = memberPointManger; }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) { this.storeMemberManager = storeMemberManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\order\StoreMemberOrderListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */