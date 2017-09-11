 package com.enation.app.shop.component.payment.plugin.alipay.direct;

 import com.alipay.config.AlipayConfig;
 import com.alipay.util.AlipayNotify;
 import com.alipay.util.AlipaySubmit;
 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.model.PayEnable;
 import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
 import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.StringUtil;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.Set;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.springframework.stereotype.Component;





 @Component
 public class AlipayDirectPlugin
   extends AbstractPaymentPlugin
   implements IPaymentEvent
 {
   public String onCallBack(String ordertype)
   {
     Map<String, String> cfgparams = this.paymentManager.getConfigParams(getId());
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String key = (String)cfgparams.get("key");
     String partner = (String)cfgparams.get("partner");
     String encoding = (String)cfgparams.get("callback_encoding");


     Map<String, String> params = new HashMap();
     Map requestParams = request.getParameterMap();
     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
       String name = (String)iter.next();
       String[] values = (String[])requestParams.get(name);
       String valueStr = "";
       for (int i = 0; i < values.length; i++) {
         valueStr = valueStr + values[i] + ",";
       }


       if (!StringUtil.isEmpty(encoding)) {
         valueStr = StringUtil.to(valueStr, encoding);
       }
       params.put(name, valueStr);
     }





     String trade_no = request.getParameter("trade_no");
     String order_no = request.getParameter("out_trade_no");
     String total_fee = request.getParameter("total_fee");

     String subject = request.getParameter("subject");
     if (!StringUtil.isEmpty(encoding)) {
       subject = StringUtil.to(subject, encoding);
     }
     String body = "";
     if (request.getParameter("body") != null) {
       body = request.getParameter("body");
       if (!StringUtil.isEmpty(encoding)) {
         body = StringUtil.to(body, encoding);
       }
     }

     String buyer_email = request.getParameter("buyer_email");
     String trade_status = request.getParameter("trade_status");





     if (AlipayNotify.callbackverify(params, key, partner))
     {

       paySuccess(order_no, trade_no, ordertype);


       if ((trade_status.equals("TRADE_FINISHED")) || (trade_status.equals("TRADE_SUCCESS")))
       {


         this.logger.info("异步校验订单[" + order_no + "]成功");
         return "success";
       }
       this.logger.info("异步校验订单[" + order_no + "]成功");
       return "success";
     }





     this.logger.info("异步校验订单[" + order_no + "]失败");
     return "fail";
   }




   public String onPay(PayCfg payCfg, PayEnable order)
   {
     Map<String, String> params = this.paymentManager.getConfigParams(getId());
     String seller_email = (String)params.get("seller_email");
     String partner = (String)params.get("partner");
     String key = (String)params.get("key");

     String show_url = getShowUrl(order);
     String notify_url = getCallBackUrl(payCfg, order);
     String return_url = getReturnUrl(payCfg, order);

     this.logger.info("notify_url is [" + notify_url + "]");






     String out_trade_no = order.getSn();

     String subject = "订单:" + order.getSn();

     String body = "网店订单";

     String total_fee = String.valueOf(order.getNeedPayMoney());





     String paymethod = "";

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();


     String defaultbank = request.getParameter("bank");
     defaultbank = defaultbank == null ? "" : defaultbank;


     Pattern pattern = Pattern.compile("[0-9]*");
     if (pattern.matcher(defaultbank).matches()) {
       defaultbank = "";
     }




     String anti_phishing_key = "";

     String exter_invoke_ip = "";












     String extra_common_param = "";

     String buyer_email = "";





     String royalty_type = "";

     String royalty_parameters = "";











     Map<String, String> sParaTemp = new HashMap();
     sParaTemp.put("payment_type", "1");
     sParaTemp.put("out_trade_no", out_trade_no);
     sParaTemp.put("subject", subject);
     sParaTemp.put("body", body);
     sParaTemp.put("total_fee", total_fee);
     sParaTemp.put("show_url", show_url);
     sParaTemp.put("paymethod", paymethod);
     sParaTemp.put("defaultbank", defaultbank);
     sParaTemp.put("anti_phishing_key", anti_phishing_key);
     sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
     sParaTemp.put("extra_common_param", extra_common_param);
     sParaTemp.put("buyer_email", buyer_email);
     sParaTemp.put("royalty_type", royalty_type);
     sParaTemp.put("royalty_parameters", royalty_parameters);



     sParaTemp.put("service", "create_direct_pay_by_user");
     sParaTemp.put("partner", partner);
     sParaTemp.put("return_url", return_url);
     sParaTemp.put("notify_url", notify_url);
     sParaTemp.put("seller_email", seller_email);
     sParaTemp.put("_input_charset", AlipayConfig.input_charset);



     return AlipaySubmit.buildForm(sParaTemp, "https://mapi.alipay.com/gateway.do?", "get", key);
   }






   public String onReturn(String ordertype)
   {
     Map<String, String> cfgparams = this.paymentManager.getConfigParams(getId());
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String key = (String)cfgparams.get("key");
     String partner = (String)cfgparams.get("partner");
     String encoding = (String)cfgparams.get("return_encoding");


     Map<String, String> params = new HashMap();
     Map requestParams = request.getParameterMap();
     for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
       String name = (String)iter.next();
       String[] values = (String[])requestParams.get(name);
       String valueStr = "";
       for (int i = 0; i < values.length; i++) {
         valueStr = valueStr + values[i] + ",";
       }


       if (!StringUtil.isEmpty(encoding)) {
         valueStr = StringUtil.to(valueStr, encoding);
       }
       params.put(name, valueStr);
     }




     String trade_no = request.getParameter("trade_no");
     String order_no = request.getParameter("out_trade_no");
     String total_fee = request.getParameter("total_fee");
     String subject = request.getParameter("subject");

     if (!StringUtil.isEmpty(encoding)) {
       subject = StringUtil.to(subject, encoding);
     }

     String body = "";
     if (request.getParameter("body") != null) {
       body = request.getParameter("body");
       if (!StringUtil.isEmpty(encoding)) {
         body = StringUtil.to(body, encoding);
       }
     }

     String buyer_email = request.getParameter("buyer_email");
     String trade_status = request.getParameter("trade_status");




     boolean verify_result = AlipayNotify.returnverify(params, key, partner);

     if (verify_result)
     {
       paySuccess(order_no, trade_no, ordertype);
       return order_no;
     }

     throw new RuntimeException("验证失败");
   }







   public String getId()
   {
     return "alipayDirectPlugin";
   }


   public String getName()
   {
     return "支付宝即时到帐接口";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\payment\plugin\alipay\direct\AlipayDirectPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */