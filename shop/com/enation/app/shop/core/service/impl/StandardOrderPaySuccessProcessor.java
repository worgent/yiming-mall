 package com.enation.app.shop.core.service.impl;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.PaymentDetail;
 import com.enation.app.shop.core.plugin.payment.IPaySuccessProcessor;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderReportManager;
 import com.enation.framework.database.IDaoSupport;
 import java.util.Date;












 public class StandardOrderPaySuccessProcessor
   implements IPaySuccessProcessor
 {
   private IOrderFlowManager orderFlowManager;
   private IOrderManager orderManager;
   private IAdminUserManager adminUserManager;
   private IOrderReportManager orderReportManager;
   private IDaoSupport daoSupport;

   public void paySuccess(String ordersn, String tradeno, String ordertype)
   {
     Order order = this.orderManager.get(ordersn);

     if (order.getPay_status().intValue() == 2) {
       return;
     }

     this.orderFlowManager.payConfirm(order.getOrder_id().intValue());


     try
     {
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
     catch (Exception e) {
       e.printStackTrace();
     }
   }




   public IOrderFlowManager getOrderFlowManager()
   {
     return this.orderFlowManager;
   }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) { this.orderFlowManager = orderFlowManager; }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) { this.orderManager = orderManager; }


   public IAdminUserManager getAdminUserManager()
   {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager)
   {
     this.adminUserManager = adminUserManager;
   }

   public IOrderReportManager getOrderReportManager()
   {
     return this.orderReportManager;
   }

   public void setOrderReportManager(IOrderReportManager orderReportManager)
   {
     this.orderReportManager = orderReportManager;
   }

   public IDaoSupport getDaoSupport()
   {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport)
   {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\StandardOrderPaySuccessProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */