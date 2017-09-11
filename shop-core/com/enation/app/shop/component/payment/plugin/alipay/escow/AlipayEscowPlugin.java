 package com.enation.app.shop.component.payment.plugin.alipay.escow;

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
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;


 @Component
 public class AlipayEscowPlugin
   extends AbstractPaymentPlugin
   implements IPaymentEvent
 {
   private static String paygateway = "https://www.alipay.com/cooperate/gateway.do?";

   public String onCallBack(String ordertype)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     Map<String, String> configparams = getConfigParams();
     String partner = (String)configparams.get("partner");
     String key = (String)configparams.get("key");
     String encoding = (String)configparams.get("callback_encoding");


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
     String total_fee = request.getParameter("price");
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
     String receive_name = "";
     if (request.getParameter("receive_name") != null) {
       receive_name = request.getParameter("receive_name");
       if (!StringUtil.isEmpty(encoding)) {
         receive_name = StringUtil.to(receive_name, encoding);
       }
     }
     String receive_address = "";
     if (request.getParameter("receive_address") != null) {
       receive_address = request.getParameter("receive_address");
       if (!StringUtil.isEmpty(encoding)) {
         receive_address = StringUtil.to(receive_address, encoding);
       }
     }

     String receive_zip = "";
     if (request.getParameter("receive_zip") != null) {
       receive_zip = request.getParameter("receive_zip");
       if (!StringUtil.isEmpty(encoding)) {
         receive_zip = StringUtil.to(receive_zip, encoding);
       }
     }


     String receive_phone = "";
     if (request.getParameter("receive_phone") != null) {
       receive_phone = request.getParameter("receive_phone");
       if (!StringUtil.isEmpty(encoding)) {
         receive_phone = StringUtil.to(receive_phone, encoding);
       }
     }


     String receive_mobile = "";
     if (request.getParameter("receive_mobile") != null) {
       receive_mobile = request.getParameter("receive_mobile");
       if (!StringUtil.isEmpty(encoding)) {
         receive_mobile = StringUtil.to(receive_mobile, encoding);
       }
     }


     String trade_status = request.getParameter("trade_status");



     if (AlipayNotify.callbackverify(params, key, partner))
     {
       paySuccess(order_no, trade_no, ordertype);







       if (trade_status.equals("WAIT_BUYER_PAY"))
       {





         return "success"; }
       if (trade_status.equals("WAIT_SELLER_SEND_GOODS"))
       {




         return "success"; }
       if (trade_status.equals("WAIT_BUYER_CONFIRM_GOODS"))
       {




         return "success"; }
       if (trade_status.equals("TRADE_FINISHED"))
       {




         return "success";
       }

       return "success";
     }





     return "fail";
   }





   public String onPay(PayCfg payCfg, PayEnable order)
   {
     Map<String, String> data = new HashMap();
     Map<String, String> params = getConfigParams();

     String out_trade_no = order.getSn();
     String partner = (String)params.get("partner");
     String key = (String)params.get("key");
     String seller_email = (String)params.get("seller_email");



     String show_url = getShowUrl(order);
     String notify_url = getCallBackUrl(payCfg, order);
     String return_url = getReturnUrl(payCfg, order);





     String subject = "订单:" + order.getSn();

     String body = "网店订单";



     String price = String.valueOf(order.getNeedPayMoney());


     String logistics_fee = "0.00";

     String logistics_type = "EXPRESS";

     String logistics_payment = "SELLER_PAY";


     String quantity = "1";






     String receive_name = "";
     String receive_address = "";
     String receive_zip = "";
     String receive_phone = "";
     String receive_mobile = "";






     Map<String, String> sParaTemp = new HashMap();
     sParaTemp.put("payment_type", "1");
     sParaTemp.put("show_url", show_url);
     sParaTemp.put("out_trade_no", out_trade_no);
     sParaTemp.put("subject", subject);
     sParaTemp.put("body", body);
     sParaTemp.put("price", price);
     sParaTemp.put("logistics_fee", logistics_fee);
     sParaTemp.put("logistics_type", logistics_type);
     sParaTemp.put("logistics_payment", logistics_payment);
     sParaTemp.put("quantity", quantity);
     sParaTemp.put("receive_name", receive_name);
     sParaTemp.put("receive_address", receive_address);
     sParaTemp.put("receive_zip", receive_zip);
     sParaTemp.put("receive_phone", receive_phone);
     sParaTemp.put("receive_mobile", receive_mobile);

     sParaTemp.put("service", "create_partner_trade_by_buyer");
     sParaTemp.put("partner", partner);
     sParaTemp.put("return_url", return_url);
     sParaTemp.put("notify_url", notify_url);
     sParaTemp.put("seller_email", seller_email);
     sParaTemp.put("_input_charset", AlipayConfig.input_charset);

     return AlipaySubmit.buildForm(sParaTemp, "https://mapi.alipay.com/gateway.do?", "get", key);
   }





   public void register() {}





   public String getAuthor()
   {
     return "kingapex";
   }

   public String getId()
   {
     return "alipayEscowPlugin";
   }

   public String getName()
   {
     return "支付宝担保交易接口";
   }

   public String getType()
   {
     return "payment";
   }

   public String getVersion()
   {
     return "1.0";
   }



   public void perform(Object... params) {}



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

       if (StringUtil.isEmpty(encoding)) {
         valueStr = StringUtil.to(valueStr, encoding);
       }
       params.put(name, valueStr);
     }




     String trade_no = request.getParameter("trade_no");
     String order_no = request.getParameter("out_trade_no");
     String total_fee = request.getParameter("price");

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
     String receive_name = "";
     if (request.getParameter("receive_name") != null) {
       receive_name = request.getParameter("receive_name");
       if (!StringUtil.isEmpty(encoding)) {
         receive_name = StringUtil.to(receive_name, encoding);
       }
     }
     String receive_address = "";
     if (request.getParameter("receive_address") != null) {
       receive_address = request.getParameter("receive_address");
       if (!StringUtil.isEmpty(encoding)) {
         receive_address = StringUtil.to(receive_address, encoding);
       }
     }

     String receive_zip = "";
     if (request.getParameter("receive_zip") != null) {
       receive_zip = request.getParameter("receive_zip");
       if (!StringUtil.isEmpty(encoding)) {
         receive_zip = StringUtil.to(receive_zip, encoding);
       }
     }


     String receive_phone = "";
     if (request.getParameter("receive_phone") != null) {
       receive_phone = request.getParameter("receive_phone");
       if (!StringUtil.isEmpty(encoding)) {
         receive_phone = StringUtil.to(receive_phone, encoding);
       }
     }


     String receive_mobile = "";
     if (request.getParameter("receive_mobile") != null) {
       receive_mobile = request.getParameter("receive_mobile");
       if (!StringUtil.isEmpty(encoding)) {
         receive_mobile = StringUtil.to(receive_mobile, encoding);
       }
     }


     String trade_status = request.getParameter("trade_status");




     boolean verify_result = AlipayNotify.returnverify(params, key, partner);

     if (verify_result)
     {




       if (trade_status.equals("WAIT_SELLER_SEND_GOODS")) {}




       paySuccess(order_no, trade_no, ordertype);
       return order_no;
     }


     paySuccess(order_no, trade_no, ordertype);
     return order_no;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\payment\plugin\alipay\escow\AlipayEscowPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */