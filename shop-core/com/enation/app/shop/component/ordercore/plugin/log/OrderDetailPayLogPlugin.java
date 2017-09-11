 package com.enation.app.shop.component.ordercore.plugin.log;

 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.plugin.order.IOrderTabShowEvent;
 import com.enation.app.shop.core.plugin.order.IShowOrderDetailHtmlEvent;
 import com.enation.app.shop.core.service.IOrderReportManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IAjaxExecuteEnable;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;










 @Component
 public class OrderDetailPayLogPlugin
   extends AutoRegisterPlugin
   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent, IAjaxExecuteEnable
 {
   private IOrderReportManager orderReportManager;

   public boolean canBeExecute(Order order)
   {
     return true;
   }



   public String getTabName(Order order)
   {
     return "收退款记录";
   }

   public String onShowOrderDetailHtml(Order order)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     int orderId = StringUtil.toInt(request.getParameter("orderid"), true);

     List payLogList = this.orderReportManager.listPayLogs(Integer.valueOf(orderId));
     List refundList = this.orderReportManager.listRefundLogs(Integer.valueOf(orderId));

     freeMarkerPaser.putData("payLogList", payLogList);
     freeMarkerPaser.putData("refundList", refundList);

     freeMarkerPaser.setPageName("paylog_list");
     return freeMarkerPaser.proessPageContent();
   }











   public String execute()
   {
     return null;
   }




   public int getOrder()
   {
     return 5;
   }

   public IOrderReportManager getOrderReportManager()
   {
     return this.orderReportManager;
   }

   public void setOrderReportManager(IOrderReportManager orderReportManager) {
     this.orderReportManager = orderReportManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\ordercore\plugin\log\OrderDetailPayLogPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */