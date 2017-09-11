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
 public class OrderDetailShipLogPlugin
   extends AutoRegisterPlugin
   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent, IAjaxExecuteEnable
 {
   private IOrderReportManager orderReportManager;

   public boolean canBeExecute(Order order)
   {
     return true;
   }



   public String execute()
   {
     return null;
   }


   public String onShowOrderDetailHtml(Order order)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     int orderId = StringUtil.toInt(request.getParameter("orderid"), true);

     List shipLogList = this.orderReportManager.listDelivery(Integer.valueOf(orderId), Integer.valueOf(1));
     List reshipLogList = this.orderReportManager.listDelivery(Integer.valueOf(orderId), Integer.valueOf(2));
     List chshipLogList = this.orderReportManager.listDelivery(Integer.valueOf(orderId), Integer.valueOf(3));

     freeMarkerPaser.putData("shipLogList", shipLogList);
     freeMarkerPaser.putData("reshipLogList", reshipLogList);
     freeMarkerPaser.putData("chshipLogList", chshipLogList);

     freeMarkerPaser.setPageName("shiplog_list");
     return freeMarkerPaser.proessPageContent();
   }








   public String getTabName(Order order)
   {
     return "发退货记录";
   }


   public int getOrder()
   {
     return 7;
   }

   public IOrderReportManager getOrderReportManager()
   {
     return this.orderReportManager;
   }

   public void setOrderReportManager(IOrderReportManager orderReportManager) {
     this.orderReportManager = orderReportManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\ordercore\plugin\log\OrderDetailShipLogPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */