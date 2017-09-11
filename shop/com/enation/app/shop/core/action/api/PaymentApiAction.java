 package com.enation.app.shop.core.action.api;

 import com.enation.app.shop.core.model.Order;
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














 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("payment")
 public class PaymentApiAction
   extends WWAction
 {
   private IPaymentManager paymentManager;
   private IOrderManager orderManager;

   public String execute()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();


     Integer orderId = StringUtil.toInt(request.getParameter("orderid"), null);
     if (orderId == null) {
       this.json = "必须传递orderid参数";
       return "json_message";
     }


     Integer paymentId = StringUtil.toInt(request.getParameter("paymentid"), null);
     Order order = this.orderManager.get(orderId);

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



   public IPaymentManager getPaymentManager()
   {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager)
   {
     this.paymentManager = paymentManager;
   }

   public IOrderManager getOrderManager()
   {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager)
   {
     this.orderManager = orderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\PaymentApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */