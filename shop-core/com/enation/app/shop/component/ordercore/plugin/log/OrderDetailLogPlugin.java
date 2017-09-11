 package com.enation.app.shop.component.ordercore.plugin.log;

 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.plugin.order.IOrderTabShowEvent;
 import com.enation.app.shop.core.plugin.order.IShowOrderDetailHtmlEvent;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IAjaxExecuteEnable;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;















 @Component
 public class OrderDetailLogPlugin
   extends AutoRegisterPlugin
   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent, IAjaxExecuteEnable
 {
   private IOrderManager orderManager;

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


     List logList = this.orderManager.listLogs(order.getOrder_id());
     freeMarkerPaser.putData("logList", logList);


     freeMarkerPaser.setPageName("log_list");
     return freeMarkerPaser.proessPageContent();
   }









   public String getTabName(Order order)
   {
     return "订单日志";
   }


   public int getOrder()
   {
     return 3;
   }

   public IOrderManager getOrderManager()
   {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\ordercore\plugin\log\OrderDetailLogPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */