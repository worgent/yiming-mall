 package com.enation.app.b2b2c.core.tag.order;

 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.PaymentDetail;
 import com.enation.app.shop.core.model.PaymentLog;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderReportManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.CurrencyUtil;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;




 @Component
 public class PaymentInfoTag
   extends BaseFreeMarkerTag
 {
   private IOrderReportManager orderReportManager;
   private IOrderManager orderManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     String sn = (String)params.get("ordersn");
     Order order = this.orderManager.get(sn);
     Integer payment_id = this.orderReportManager.getPaymentLogId(order.getOrder_id());
     PaymentLog payment = this.orderReportManager.getPayment(payment_id);
     List<PaymentDetail> paymentList = this.orderReportManager.listPayMentDetail(payment_id);
     Double showMoney = Double.valueOf(CurrencyUtil.sub(payment.getMoney().doubleValue(), payment.getPaymoney().doubleValue()));
     Map result = new HashMap();
     result.put("paymentList", paymentList);
     result.put("payment", payment);
     result.put("order", order);
     result.put("showMoney", showMoney);
     result.put("payment_id", payment_id);
     return result;
   }

   public IOrderReportManager getOrderReportManager() { return this.orderReportManager; }

   public void setOrderReportManager(IOrderReportManager orderReportManager) {
     this.orderReportManager = orderReportManager;
   }

   public IOrderManager getOrderManager() { return this.orderManager; }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\order\PaymentInfoTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */