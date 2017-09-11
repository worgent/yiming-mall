 package com.enation.app.shop.component.payment.plugin.bill;

 import com.enation.app.shop.component.payment.plugin.bill.encrypt.MD5Util;
 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.model.PayEnable;
 import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
 import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.io.UnsupportedEncodingException;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.springframework.stereotype.Component;








 @Component
 public class BillPlugin
   extends AbstractPaymentPlugin
   implements IPaymentEvent
 {
   private IPaymentManager paymentManager;

   public String getId()
   {
     return "billPlugin";
   }


   public String getName()
   {
     return "快钱人民币支付";
   }









   public String appendParam(String returnStr, String paramId, String paramValue)
   {
     if (!returnStr.equals(""))
     {
       if (!paramValue.equals(""))
       {
         returnStr = returnStr + "&" + paramId + "=" + paramValue;
       }


     }
     else if (!paramValue.equals(""))
     {
       returnStr = paramId + "=" + paramValue;
     }

     return returnStr;
   }


   public String onPay(PayCfg payCfg, PayEnable order)
   {
     Map<String, String> params = this.paymentManager.getConfigParams(getId());
     String partner = (String)params.get("partner");



     String key = (String)params.get("key");

     String show_url = getShowUrl(order);
     String notify_url = getCallBackUrl(payCfg, order);
     String return_url = getReturnUrl(payCfg, order);



     String merchantAcctId = partner;






     String inputCharset = "1";





     String bgUrl = return_url;




     String version = "v2.0";





     String language = "1";




     String signType = "1";



     String payerName = "payerName";




     String payerContactType = "1";



     String payerContact = "";



     String orderId = order.getSn();




     double oa = order.getNeedPayMoney().doubleValue() * 100.0D;
     String orderAmount = String.valueOf((int)oa);




     String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());



     String productName = "订单:" + order.getSn();



     String productNum = "1";



     String productId = "";


     String productDesc = "";



     String ext1 = "";



     String ext2 = "";




     String payType = "00";





     String redoFlag = "0";



     String pid = "";




     String signMsgVal = "";
     signMsgVal = appendParam(signMsgVal, "inputCharset", inputCharset);
     signMsgVal = appendParam(signMsgVal, "bgUrl", bgUrl);
     signMsgVal = appendParam(signMsgVal, "version", version);
     signMsgVal = appendParam(signMsgVal, "language", language);
     signMsgVal = appendParam(signMsgVal, "signType", signType);
     signMsgVal = appendParam(signMsgVal, "merchantAcctId", merchantAcctId);
     signMsgVal = appendParam(signMsgVal, "payerName", payerName);
     signMsgVal = appendParam(signMsgVal, "payerContactType", payerContactType);
     signMsgVal = appendParam(signMsgVal, "payerContact", payerContact);
     signMsgVal = appendParam(signMsgVal, "orderId", orderId);
     signMsgVal = appendParam(signMsgVal, "orderAmount", orderAmount);
     signMsgVal = appendParam(signMsgVal, "orderTime", orderTime);
     signMsgVal = appendParam(signMsgVal, "productName", productName);
     signMsgVal = appendParam(signMsgVal, "productNum", productNum);
     signMsgVal = appendParam(signMsgVal, "productId", productId);
     signMsgVal = appendParam(signMsgVal, "productDesc", productDesc);
     signMsgVal = appendParam(signMsgVal, "ext1", ext1);
     signMsgVal = appendParam(signMsgVal, "ext2", ext2);
     signMsgVal = appendParam(signMsgVal, "payType", payType);
     signMsgVal = appendParam(signMsgVal, "redoFlag", redoFlag);
     signMsgVal = appendParam(signMsgVal, "pid", pid);
     signMsgVal = appendParam(signMsgVal, "key", key);
     try
     {
       String signMsg = MD5Util.md5Hex(signMsgVal.getBytes("utf-8")).toUpperCase();
       String strHtml = "";
       strHtml = strHtml + "<form name=\"kqPay\" id=\"kqPay\" action=\"https://www.99bill.com/gateway/recvMerchantInfoAction.htm\" method=\"post\">";
       strHtml = strHtml + "<input type=\"hidden\" name=\"inputCharset\" value=\"" + inputCharset + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"bgUrl\" value=\"" + bgUrl + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"version\" value=\"" + version + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"language\" value=\"" + language + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"signType\" value=\"" + signType + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"signMsg\" value=\"" + signMsg + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"merchantAcctId\" value=\"" + merchantAcctId + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"payerName\" value=\"" + payerName + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"payerContactType\" value=\"" + payerContactType + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"payerContact\" value=\"" + payerContact + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"orderId\" value=\"" + orderId + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"orderAmount\" value=\"" + orderAmount + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"orderTime\" value=\"" + orderTime + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"productName\" value=\"" + productName + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"productNum\" value=\"" + productNum + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"productId\" value=\"" + productId + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"productDesc\" value=\"" + productDesc + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"ext1\" value=\"" + ext1 + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"ext2\" value=\"" + ext2 + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"payType\" value=\"" + payType + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"redoFlag\" value=\"" + redoFlag + "\"/>";
       strHtml = strHtml + "<input type=\"hidden\" name=\"pid\" value=\"" + pid + "\"/>";

       strHtml = strHtml + "</form>";
       return strHtml + "<script type=\"text/javascript\">document.forms['kqPay'].submit();</script>";

     }
     catch (UnsupportedEncodingException e)
     {
       e.printStackTrace(); }
     return "验证串失败";
   }



   public String onCallBack(String ordertype)
   {
     return null;
   }

   public String onReturn(String ordertype)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     String merchantAcctId = request.getParameter("merchantAcctId").trim();

     Map<String, String> params = this.paymentManager.getConfigParams(getId());


     String key = (String)params.get("key");




     String version = request.getParameter("version").trim();





     String language = request.getParameter("language").trim();




     String signType = request.getParameter("signType").trim();




     String payType = request.getParameter("payType").trim();



     String bankId = request.getParameter("bankId").trim();


     String orderId = request.getParameter("orderId").trim();
     this.logger.debug("快钱 return-----------orderId----------" + orderId);



     String orderTime = request.getParameter("orderTime").trim();




     String orderAmount = request.getParameter("orderAmount").trim();



     String dealId = request.getParameter("dealId").trim();



     String bankDealId = request.getParameter("bankDealId").trim();




     String dealTime = request.getParameter("dealTime").trim();




     String payAmount = request.getParameter("payAmount").trim();




     String fee = request.getParameter("fee").trim();


     String ext1 = request.getParameter("ext1").trim();


     String ext2 = request.getParameter("ext2").trim();



     String payResult = request.getParameter("payResult").trim();
     this.logger.debug("快钱 return---------payResult------------" + payResult);


     String errCode = request.getParameter("errCode").trim();


     String signMsg = request.getParameter("signMsg").trim();
     this.logger.debug("快钱 return----------signMsg-----------" + signMsg);



     String merchantSignMsgVal = "";
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "merchantAcctId", merchantAcctId);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "version", version);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "language", language);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "signType", signType);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "payType", payType);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankId", bankId);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderId", orderId);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderTime", orderTime);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "orderAmount", orderAmount);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealId", dealId);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "bankDealId", bankDealId);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "dealTime", dealTime);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "payAmount", payAmount);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "fee", fee);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext1", ext1);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "ext2", ext2);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "payResult", payResult);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "errCode", errCode);
     merchantSignMsgVal = appendParam(merchantSignMsgVal, "key", key);

     String merchantSignMsg = "";
     try {
       merchantSignMsg = MD5Util.md5Hex(merchantSignMsgVal.getBytes("utf-8")).toUpperCase();
     }
     catch (UnsupportedEncodingException e) {
       this.logger.error("快钱支付验证串失败");
       e.printStackTrace();
     }



     int rtnOk = 0;
     String rtnUrl = "";



     if (signMsg.toUpperCase().equals(merchantSignMsg.toUpperCase()))
     {

       switch (Integer.parseInt(payResult))
       {







       case 10:
         rtnOk = 1;
         rtnUrl = "member_orderdetail_" + orderId + ".html";
         paySuccess(orderId, dealId, ordertype);
         return orderId;
       }


       rtnOk = 1;
       rtnUrl = "member_orderdetail_" + orderId + ".html";
       paySuccess(orderId, dealId, ordertype);
       return orderId;
     }




     rtnOk = 1;
     rtnUrl = "";
     this.logger.debug("onReturn in............. fail");
     throw new RuntimeException("验证失败");
   }




   public IPaymentManager getPaymentManager()
   {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager) {
     this.paymentManager = paymentManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\payment\plugin\bill\BillPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */