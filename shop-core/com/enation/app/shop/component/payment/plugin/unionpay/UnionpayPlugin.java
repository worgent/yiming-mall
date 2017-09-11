 package com.enation.app.shop.component.payment.plugin.unionpay;

 import chinapay.PrivateKey;
 import chinapay.SecureLink;
 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.model.PayEnable;
 import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
 import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.text.DecimalFormat;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;




 @Component("unPay")
 public class UnionpayPlugin
   extends AbstractPaymentPlugin
   implements IPaymentEvent
 {
   private String generateAutoSubmitForm(String actionUrl, Map<String, String> paramMap)
   {
     StringBuilder html = new StringBuilder();
     html.append("<script language=\"javascript\">window.onload=function(){document.pay_form.submit();}</script>\n");
     html.append("<form id=\"pay_form\" name=\"pay_form\" action=\"").append(actionUrl).append("\" method=\"post\">\n");

     for (String key : paramMap.keySet()) {
       html.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\"" + (String)paramMap.get(key) + "\">\n");
     }
     html.append("</form>\n");
     return html.toString();
   }

   protected String payBack(String ordertype) {
     Map<String, String> params = this.paymentManager.getConfigParams(getId());
     String merPath = (String)params.get("merPath");

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String merId = request.getParameter("merid");
     String orderno = request.getParameter("orderno");
     String transdate = request.getParameter("transdate");
     String amount = request.getParameter("amount");
     String currencycode = request.getParameter("currencycode");
     String transtype = request.getParameter("transtype");
     String status = request.getParameter("status");
     String checkvalue = request.getParameter("checkvalue");
     String Priv1 = request.getParameter("Priv1");

     PrivateKey key = new PrivateKey();

     boolean flag = key.buildKey(merId, 0, merPath);
     if (!flag)
     {
       return "<div>系统设置错误</div>";
     }
     SecureLink t = new SecureLink(key);
     flag = t.verifyTransResponse(merId, orderno, amount, currencycode, transdate, transtype, status, checkvalue);

     if (!flag) {}




     String MsgBody = merId + orderno + amount + currencycode + transdate + transtype + Priv1;
     flag = t.verifyAuthToken(MsgBody, checkvalue);
     String ordersn = orderno.substring(2);
     if (!flag)
     {

       paySuccess(ordersn, "", ordertype);
     }

     return ordersn;
   }

   public String onPay(PayCfg payCfg, PayEnable order)
   {
     Map<String, String> params = this.paymentManager.getConfigParams(getId());
     String merId = (String)params.get("merId");
     String merPath = (String)params.get("merPath");
     String payUrl = (String)params.get("payUrl");

     DecimalFormat df_amount = new DecimalFormat("000000000000");

     String ordId = order.getSn();
     if (ordId.length() > 16) {
       ordId = ordId.substring(0, 16);
     } else if (ordId.length() < 16) {
       String zero = "";
       for (int i = 0; i < 16 - ordId.length(); i++) {
         zero = zero + "0";
       }
       ordId = zero + ordId;
     }

     String amount = df_amount.format((int)(order.getNeedPayMoney().doubleValue() * 100.0D));
     String CuryId = "156";
     String TransDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
     String TransType = "0001";
     String Priv1 = "memo";

     PrivateKey key = new PrivateKey();

     boolean flag = key.buildKey(merId, 0, merPath);
     if (!flag)
     {
       return "<div>系统设置错误</div>";
     }
     SecureLink t = new SecureLink(key);
     String MsgBody = merId + ordId + amount + CuryId + TransDate + TransType + Priv1;

     String ChkValue = t.Sign(MsgBody);

     Map<String, String> param = new HashMap();
     param.put("MerId", merId);
     param.put("OrdId", ordId);
     param.put("TransAmt", amount);
     param.put("CuryId", CuryId);
     param.put("TransDate", TransDate);
     param.put("TransType", TransType);
     param.put("Version", "20070129");
     param.put("BgRetUrl", getCallBackUrl(payCfg, order));
     param.put("PageRetUrl", getReturnUrl(payCfg, order));
     param.put("Priv1", Priv1);
     param.put("ChkValue", ChkValue);
     String html = "<div style='margin:50px auto;width:500px'>正在跳转到银联在线支付平台，请稍等...</div>";
     html = html + generateAutoSubmitForm(payUrl, param);

     return html;
   }

   public String onCallBack(String ordertype)
   {
     return payBack(ordertype);
   }

   public String onReturn(String ordertype)
   {
     return payBack(ordertype);
   }

   public String getId()
   {
     return "unPay";
   }

   public String getName()
   {
     return "银联在线支付";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\payment\plugin\\unionpay\UnionpayPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */