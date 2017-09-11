 package com.enation.app.shop.component.ordercore.plugin.allocation;

 import com.enation.app.shop.core.model.Depot;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.plugin.order.IOrderTabShowEvent;
 import com.enation.app.shop.core.plugin.order.IShowOrderDetailHtmlEvent;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.app.shop.core.service.IOrderAllocationManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IAjaxExecuteEnable;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;











 @Component
 public class OrderDetailAllocationPlugin
   extends AutoRegisterPlugin
   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent, IAjaxExecuteEnable
 {
   private IOrderAllocationManager orderAllocationManager;
   private IOrderManager orderManager;
   private IDepotManager depotManager;

   public boolean canBeExecute(Order order)
   {
     if (order.getShip_status().intValue() != 0) {
       return true;
     }
     return false;
   }


   public String execute()
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();


     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     int orderId = StringUtil.toInt(request.getParameter("orderid"), true);

     long all_time = this.orderManager.get(Integer.valueOf(orderId)).getAllocation_time().longValue();
     String all_depotname = this.depotManager.get(this.orderManager.get(Integer.valueOf(orderId)).getDepotid().intValue()).getName();
     List allocationList = this.orderAllocationManager.listAllocation(orderId);


     freeMarkerPaser.putData("all_time", Long.valueOf(all_time));
     freeMarkerPaser.putData("all_depotname", all_depotname);
     freeMarkerPaser.putData("allocationList", allocationList);

     freeMarkerPaser.setPageName("allocation_list");
     freeMarkerPaser.setClz(getClass());
     return freeMarkerPaser.proessPageContent();
   }


   public String onShowOrderDetailHtml(Order order)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     int orderId = order.getOrder_id().intValue();
     freeMarkerPaser.putData("orderid", Integer.valueOf(orderId));
     freeMarkerPaser.setPageName("allocation");

     return freeMarkerPaser.proessPageContent();
   }



   public String getTabName(Order order)
   {
     return "配货信息";
   }



   public int getOrder()
   {
     return 9;
   }

   public IOrderAllocationManager getOrderAllocationManager() {
     return this.orderAllocationManager;
   }

   public void setOrderAllocationManager(IOrderAllocationManager orderAllocationManager)
   {
     this.orderAllocationManager = orderAllocationManager;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IDepotManager getDepotManager() {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) {
     this.depotManager = depotManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\ordercore\plugin\allocation\OrderDetailAllocationPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */