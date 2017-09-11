 package com.enation.app.b2b2c.core.service;

 import com.enation.app.b2b2c.core.model.order.StoreOrder;
 import com.enation.app.b2b2c.core.service.order.IStoreOrderManager;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.PaymentDetail;
 import com.enation.app.shop.core.plugin.payment.IPaySuccessProcessor;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderReportManager;
 import com.enation.framework.database.IDaoSupport;
 import java.util.Date;
 import java.util.List;
 import org.springframework.stereotype.Component;









 @Component
 public class B2b2cOrderPaySuccessProcessor
   implements IPaySuccessProcessor
 {
   private IOrderFlowManager orderFlowManager;
   private IOrderManager orderManager;
   private IOrderReportManager orderReportManager;
   private IDaoSupport daoSupport;
   private IStoreOrderManager storeOrderManager;

   public void paySuccess(String ordersn, String tradeno, String ordertype)
   {
     StoreOrder order = this.storeOrderManager.get(ordersn);

     if (order.getPay_status().intValue() == 2) {
       return;
     }
     payConfirmOrder(order);
     if (order.getParent_id() == null)
     {
       List<StoreOrder> cOrderList = this.storeOrderManager.storeOrderList(order.getOrder_id());
       for (StoreOrder storeOrder : cOrderList) {
         payConfirmOrder(storeOrder);
       }
     }
   }



   private void payConfirmOrder(Order order)
   {
     this.orderFlowManager.payConfirm(order.getOrder_id().intValue());
     Double needPayMoney = order.getNeed_pay_money();
     int paymentid = this.orderReportManager.getPaymentLogId(order.getOrder_id()).intValue();

     PaymentDetail paymentdetail = new PaymentDetail();

     paymentdetail.setAdmin_user("系统");
     paymentdetail.setPay_date(new Date().getTime());
     paymentdetail.setPay_money(needPayMoney);
     paymentdetail.setPayment_id(Integer.valueOf(paymentid));
     this.orderReportManager.addPayMentDetail(paymentdetail);

     this.daoSupport.execute("update es_payment_logs set paymoney=paymoney+? where payment_id=?", new Object[] { needPayMoney, Integer.valueOf(paymentid) });


     this.daoSupport.execute("update es_order set paymoney=paymoney+? where order_id=?", new Object[] { needPayMoney, order.getOrder_id() });
   }

   public IOrderFlowManager getOrderFlowManager() { return this.orderFlowManager; }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
     this.orderFlowManager = orderFlowManager;
   }

   public IOrderManager getOrderManager() { return this.orderManager; }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IOrderReportManager getOrderReportManager() { return this.orderReportManager; }

   public void setOrderReportManager(IOrderReportManager orderReportManager) {
     this.orderReportManager = orderReportManager;
   }

   public IDaoSupport getDaoSupport() { return this.daoSupport; }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IStoreOrderManager getStoreOrderManager() { return this.storeOrderManager; }

   public void setStoreOrderManager(IStoreOrderManager storeOrderManager) {
     this.storeOrderManager = storeOrderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\B2b2cOrderPaySuccessProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */