 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IMemberOrderManager;
 import com.enation.app.shop.core.service.IMemberPointManger;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IPromotionManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
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
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;













 @Component
 @Scope("prototype")
 public class MemberOrderListTag
   extends BaseFreeMarkerTag
 {
   private IMemberOrderManager memberOrderManager;
   private IOrderManager orderManager;
   private IPromotionManager promotionManager;
   private IOrderFlowManager orderFlowManager;
   private IMemberPointManger memberPointManger;

   public Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
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

     Page ordersPage = this.memberOrderManager.pageOrders(Integer.valueOf(page).intValue(), pageSize, status, keyword);
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

   public IMemberOrderManager getMemberOrderManager() {
     return this.memberOrderManager;
   }

   public void setMemberOrderManager(IMemberOrderManager memberOrderManager) { this.memberOrderManager = memberOrderManager; }

   public IOrderManager getOrderManager()
   {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IPromotionManager getPromotionManager() {
     return this.promotionManager;
   }

   public void setPromotionManager(IPromotionManager promotionManager) {
     this.promotionManager = promotionManager;
   }

   public IOrderFlowManager getOrderFlowManager() {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
     this.orderFlowManager = orderFlowManager;
   }

   public IMemberPointManger getMemberPointManger() {
     return this.memberPointManger;
   }

   public void setMemberPointManger(IMemberPointManger memberPointManger) {
     this.memberPointManger = memberPointManger;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\MemberOrderListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */