 package com.enation.app.shop.component.payment.plugin.abcpay;

 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.model.PayEnable;
 import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
 import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.hitrust.trustpay.client.TrxResponse;
 import com.hitrust.trustpay.client.b2c.Order;
 import com.hitrust.trustpay.client.b2c.PaymentRequest;
 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;

 @org.springframework.stereotype.Component
 public class AbcpayPlugin extends AbstractPaymentPlugin implements IPaymentEvent
 {
   public String onPay(PayCfg payCfg, PayEnable order)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     String callBackUrl = getCallBackUrl(payCfg, order);

     Date today = Calendar.getInstance().getTime();
     SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
     SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");

     Order tOrder = new Order();
     tOrder.setOrderNo(order.getSn());
     tOrder.setExpiredDate(30);
     tOrder.setOrderDesc("");
     tOrder.setOrderDate(date.format(today));
     tOrder.setOrderTime(time.format(today));
     tOrder.setOrderAmount(order.getNeedPayMoney().doubleValue());
     tOrder.setOrderURL(getShowUrl(order));
     tOrder.setBuyIP(request.getRemoteAddr());






     PaymentRequest tPaymentRequest = new PaymentRequest();
     tPaymentRequest.setOrder(tOrder);
     tPaymentRequest.setProductType("2");


     tPaymentRequest.setPaymentType("1");


     tPaymentRequest.setNotifyType("0");


     tPaymentRequest.setResultNotifyURL(callBackUrl);
     tPaymentRequest.setMerchantRemarks("Javashop abcpayPlugIn");
     tPaymentRequest.setPaymentLinkType("1");


     TrxResponse tTrxResponse = tPaymentRequest.extendPostRequest(1);
     if (tTrxResponse.isSuccess())
     {
       return "<h2>正在转向中国农业银行网上支付页面，请稍后...</h2><script>location.href='" + tTrxResponse.getValue("PaymentURL") + "';</script>";
     }

     return "ReturnCode = [" + tTrxResponse.getReturnCode() + "]<br>" + "ErrorMessage = [" + tTrxResponse.getErrorMessage() + "]<br>";
   }


   public String onCallBack(String ordertype)
   {
     this.logger.debug("Abc callbacked");

     return "callbacked";
   }


   public String onReturn(String ordertype)
   {
     return null;
   }

   public String getId()
   {
     return "abcpayPlugin";
   }

   public String getName()
   {
     return "中国农业银行网上支付";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\payment\plugin\abcpay\AbcpayPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */