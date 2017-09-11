 package com.enation.app.shop.core.tag.order;

 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderReportManager;
 import com.enation.app.shop.core.service.IPromotionManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;























 @Component
 @Scope("prototype")
 public class SearchOrderTag
   extends BaseFreeMarkerTag
 {
   private IOrderManager orderManager;
   private IOrderReportManager orderReportManager;
   private IPromotionManager promotionManager;

   public Object exec(Map args)
     throws TemplateModelException
   {
     Map data = new HashMap();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String action = request.getParameter("action");
     if ((action != null) && (!action.equals("")))
     {
       if (action.equals("search")) {
         String ship_name = request.getParameter("ship_name");
         if ((ship_name != null) && (!ship_name.equals(""))) {
           String encoding = EopSetting.ENCODING;
           if (!StringUtil.isEmpty(encoding)) {
             ship_name = StringUtil.toUTF8(ship_name);
           }
         }
         String ship_tel = request.getParameter("ship_tel");
         if (StringUtil.isEmpty(ship_name)) {
           throw new TemplateModelException("请输入收货人姓名!");
         }
         if (StringUtil.isEmpty(ship_tel)) {
           throw new TemplateModelException("请输入手机或固定号码!");
         }
         String page = request.getParameter("page");
         page = (page == null) || (page.equals("")) ? "1" : page;
         int pageSize = 10;
         Page ordersPage = this.orderManager.searchForGuest(Integer.parseInt(page), pageSize, ship_name, ship_tel);
         data.put("ordersPage", ordersPage);
         data.put("totalCount", Long.valueOf(ordersPage.getTotalCount()));
         data.put("ship_name", ship_name);
         data.put("ship_tel", ship_tel);
         data.put("pageSize", Integer.valueOf(pageSize));
         data.put("page", page);
         data.put("status", OrderStatus.getOrderStatusMap());
       }
     }
     return data;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IOrderReportManager getOrderReportManager() {
     return this.orderReportManager;
   }

   public void setOrderReportManager(IOrderReportManager orderReportManager) {
     this.orderReportManager = orderReportManager;
   }

   public IPromotionManager getPromotionManager() {
     return this.promotionManager;
   }

   public void setPromotionManager(IPromotionManager promotionManager) {
     this.promotionManager = promotionManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\order\SearchOrderTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */