 package com.enation.app.b2b2c.core.action.api.payment;

 import com.enation.app.b2b2c.core.model.order.StoreOrder;
 import com.enation.app.b2b2c.core.service.order.IStoreOrderManager;
 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.StringUtil;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;










 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("storePaymentApi")
 public class StorePaymentApiAction
   extends WWAction
 {
   private IPaymentManager paymentManager;
   private IOrderManager orderManager;
   private IStoreOrderManager storeOrderManager;

   public String execute()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();


     Integer orderId = StringUtil.toInt(request.getParameter("orderid"), null);
     if (orderId == null) {
       this.json = "必须传递orderid参数";
       return "json_message";
     }


     Integer paymentId = StringUtil.toInt(request.getParameter("paymentid"), null);
     StoreOrder order = this.storeOrderManager.get(orderId);

     if (order == null) {
       this.json = "该订单不存在";
       return "json_message";
     }


     if (paymentId == null) {
       paymentId = order.getPayment_id();
     }

     PayCfg payCfg = this.paymentManager.get(paymentId);

     IPaymentEvent paymentPlugin = (IPaymentEvent)SpringContextHolder.getBean(payCfg.getType());
     String payhtml = paymentPlugin.onPay(payCfg, order);


     if (order.getPayment_id().intValue() != paymentId.intValue()) {
       this.orderManager.updatePayMethod(orderId.intValue(), paymentId.intValue(), payCfg.getType(), payCfg.getName());
     }
     this.json = payhtml;
     return "json_message";
   }

   public IPaymentManager getPaymentManager() { return this.paymentManager; }

   public void setPaymentManager(IPaymentManager paymentManager) {
     this.paymentManager = paymentManager;
   }

   public IOrderManager getOrderManager() { return this.orderManager; }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IStoreOrderManager getStoreOrderManager() { return this.storeOrderManager; }

   public void setStoreOrderManager(IStoreOrderManager storeOrderManager) {
     this.storeOrderManager = storeOrderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\payment\StorePaymentApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */