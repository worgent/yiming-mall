 package com.enation.app.shop.component.ordercore.plugin.base;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.model.Delivery;
 import com.enation.app.shop.core.model.Depot;
 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.plugin.order.IOrderTabShowEvent;
 import com.enation.app.shop.core.plugin.order.IShowOrderDetailHtmlEvent;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderReportManager;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.List;
 import org.springframework.stereotype.Component;







 @Component
 public class OrderDetailBasePlugin
   extends AutoRegisterPlugin
   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent
 {
   private IOrderManager orderManager;
   private IMemberManager memberManager;
   private IOrderReportManager orderReportManager;
   private IDepotManager depotManager;
   private IPaymentManager paymentManager;
   private IDlyTypeManager dlyTypeManager;

   public boolean canBeExecute(Order order)
   {
     return true;
   }


   public String getTabName(Order order)
   {
     return "基本信息";
   }


   public int getOrder()
   {
     return 0;
   }


   public String onShowOrderDetailHtml(Order order)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();

     List itemList = this.orderManager.listGoodsItems(order.getOrder_id());
     freeMarkerPaser.setClz(getClass());
     if (order.getMember_id() != null) {
       Member member = this.memberManager.get(order.getMember_id());
       freeMarkerPaser.putData("member", member);
     }

     List<Delivery> deliveryList = this.orderReportManager.getDeliveryList(order.getOrder_id().intValue());
     List<Depot> depotList = this.depotManager.list();
     List<DlyType> dlyTypeList = this.dlyTypeManager.list();
     List<PayCfg> payCfgList = this.paymentManager.list();

     freeMarkerPaser.putData("deliveryList", deliveryList);
     freeMarkerPaser.putData("itemList", itemList);
     freeMarkerPaser.putData(OrderStatus.getOrderStatusMap());
     freeMarkerPaser.putData("depotList", depotList);
     freeMarkerPaser.putData("payCfgList", payCfgList);
     freeMarkerPaser.putData("dlyTypeList", dlyTypeList);

     Integer depotid = order.getDepotid();

     Depot depot = this.depotManager.get(depotid.intValue());
     freeMarkerPaser.putData("depot", depot);

     freeMarkerPaser.setPageName("base");
     return freeMarkerPaser.proessPageContent();
   }

   public IOrderManager getOrderManager()
   {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public IOrderReportManager getOrderReportManager() {
     return this.orderReportManager;
   }

   public void setOrderReportManager(IOrderReportManager orderReportManager) {
     this.orderReportManager = orderReportManager;
   }

   public IDepotManager getDepotManager() {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) {
     this.depotManager = depotManager;
   }

   public IPaymentManager getPaymentManager() {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager) {
     this.paymentManager = paymentManager;
   }

   public IDlyTypeManager getDlyTypeManager() {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\ordercore\plugin\base\OrderDetailBasePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */