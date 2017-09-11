 package com.enation.app.shop.component.payment.plugin.tenpay.direct;

 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.model.PayEnable;
 import com.enation.app.shop.core.plugin.payment.AbstractPaymentPlugin;
 import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.tenpay.RequestHandler;
 import com.tenpay.ResponseHandler;
 import com.tenpay.client.ClientResponseHandler;
 import com.tenpay.client.TenpayHttpClient;
 import com.tenpay.util.TenpayUtil;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.stereotype.Component;








 @Component
 public class TenpayDirectPlugin
   extends AbstractPaymentPlugin
   implements IPaymentEvent
 {
   public String onCallBack(String ordertype)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     HttpServletResponse response = ThreadContextHolder.getHttpResponse();

     Map<String, String> params = getConfigParams();

     String key = (String)params.get("tenpaykey");
     String partner = (String)params.get("tenpaybid");
     String sn = null;






     ResponseHandler resHandler = new ResponseHandler(request, response);
     resHandler.setKey(key);

     System.out.println("后台回调返回参数:" + resHandler.getAllParameters());

     try
     {
       if (resHandler.isTenpaySign())
       {

         String notify_id = resHandler.getParameter("notify_id");


         RequestHandler queryReq = new RequestHandler(null, null);

         TenpayHttpClient httpClient = new TenpayHttpClient();

         ClientResponseHandler queryRes = new ClientResponseHandler();


         queryReq.init();
         queryReq.setKey(key);
         queryReq.setGateUrl("https://gw.tenpay.com/gateway/simpleverifynotifyid.xml");
         queryReq.setParameter("partner", partner);
         queryReq.setParameter("notify_id", notify_id);


         httpClient.setTimeOut(5);


         httpClient.setReqContent(queryReq.getRequestURL());
         System.out.println("验证ID请求字符串:" + queryReq.getRequestURL());


         if (httpClient.call())
         {
           queryRes.setContent(httpClient.getResContent());
           System.out.println("验证ID返回字符串:" + httpClient.getResContent());

           queryRes.setKey(key);


           String retcode = queryRes.getParameter("retcode");


           String out_trade_no = resHandler.getParameter("out_trade_no");

           String transaction_id = resHandler.getParameter("transaction_id");


           String total_fee = resHandler.getParameter("total_fee");

           String discount = resHandler.getParameter("discount");

           String trade_state = resHandler.getParameter("trade_state");

           String trade_mode = resHandler.getParameter("trade_mode");


           if ((queryRes.isTenpaySign()) && ("0".equals(retcode))) {
             System.out.println("id验证成功");

             if ("1".equals(trade_mode)) {
               if ("0".equals(trade_state))
               {



                 sn = out_trade_no;
                 paySuccess(sn, transaction_id, ordertype);









                 System.out.println("即时到账支付成功");

                 resHandler.sendToCFT("success");

                 return "";
               }

               System.out.println("即时到账支付失败");
               resHandler.sendToCFT("fail");
             }
             else if ("2".equals(trade_mode))
             {







               int iStatus = TenpayUtil.toInt(trade_state);
               switch (iStatus)
               {
               case 0:
                 break;
               case 1:
                 break;
               case 2:
                 break;
               case 4:
                 break;
               case 5:
                 break;
               case 6:
                 break;
               case 7:
                 break;
               case 8:
                 break;
               case 9:
                 break;
               case 10:
                 break;
               }















               System.out.println("trade_state = " + trade_state);

               resHandler.sendToCFT("success");
               return "";
             }
           }
           else {
             System.out.println("查询验证签名失败或id验证失败,retcode:" + queryRes.getParameter("retcode"));
           }
         }
         else {
           System.out.println("后台调用通信失败");
           System.out.println(httpClient.getResponseCode());
           System.out.println(httpClient.getErrInfo());
         }
       }
       else
       {
         System.out.println("通知签名验证失败");
       }
     }
     catch (Exception e) {}


     return null;
   }

   public String onPay(PayCfg payCfg, PayEnable order)
   {
     Map<String, String> params = this.paymentManager.getConfigParams(getId());


     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     HttpServletResponse response = ThreadContextHolder.getHttpResponse();




     String desc = "商品：订单(" + order.getSn() + ")中的商品,备注：网店订单";


     String currTime = TenpayUtil.getCurrTime();


     String strReq = order.getSn();


     String partner = (String)params.get("tenpaybid");


     String key = (String)params.get("tenpaykey");


     String notify_url = getCallBackUrl(payCfg, order);

     String return_url = getReturnUrl(payCfg, order);


     RequestHandler reqHandler = new RequestHandler(request, response);
     reqHandler.init();

     reqHandler.setKey(key);

     reqHandler.setGateUrl("https://gw.tenpay.com/gateway/pay.htm");




     reqHandler.setParameter("partner", partner);
     reqHandler.setParameter("out_trade_no", strReq);
     reqHandler.setParameter("total_fee", "1");
     reqHandler.setParameter("return_url", return_url);
     reqHandler.setParameter("notify_url", notify_url);

     reqHandler.setParameter("body", desc);
     reqHandler.setParameter("bank_type", "DEFAULT");
     reqHandler.setParameter("spbill_create_ip", request.getRemoteAddr());
     reqHandler.setParameter("fee_type", "1");
     reqHandler.setParameter("subject", "测试");


     reqHandler.setParameter("sign_type", "MD5");
     reqHandler.setParameter("service_version", "1.0");
     reqHandler.setParameter("input_charset", "utf-8");
     reqHandler.setParameter("sign_key_index", "1");


     reqHandler.setParameter("trade_mode", "1");
     reqHandler.setParameter("attach", "");
     reqHandler.setParameter("product_fee", "");

     reqHandler.setParameter("transport_fee", "");


     reqHandler.setParameter("time_start", currTime);
     reqHandler.setParameter("time_expire", "");
     reqHandler.setParameter("buyer_id", "");
     reqHandler.setParameter("goods_tag", "");
     reqHandler.setParameter("transport_desc", "");
     reqHandler.setParameter("trans_type", "1");
     reqHandler.setParameter("agentid", "");
     reqHandler.setParameter("agent_type", "");
     reqHandler.setParameter("seller_id", "");


     String requestUrl = "";
     try {
       requestUrl = reqHandler.getRequestURL();
       reqHandler.doSend();
     } catch (IOException e) {
       e.printStackTrace();
     }


     String debuginfo = reqHandler.getDebugInfo();

     return requestUrl;
   }

   public String onReturn(String ordertype)
   {
     Map<String, String> params = this.paymentManager.getConfigParams(getId());

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     HttpServletResponse response = ThreadContextHolder.getHttpResponse();

     String sn = null;





     ResponseHandler resHandler = new ResponseHandler(request, response);
     resHandler.setKey((String)params.get("tenpaykey"));

     System.out.println("前台回调返回参数:" + resHandler.getAllParameters());

     if (resHandler.isTenpaySign())
     {

       String notify_id = resHandler.getParameter("notify_id");

       String out_trade_no = resHandler.getParameter("out_trade_no");

       String transaction_id = resHandler.getParameter("transaction_id");

       String total_fee = resHandler.getParameter("total_fee");

       String discount = resHandler.getParameter("discount");

       String trade_state = resHandler.getParameter("trade_state");

       String trade_mode = resHandler.getParameter("trade_mode");

       if ("1".equals(trade_mode)) {
         if ("0".equals(trade_state))
         {


           sn = out_trade_no;
           paySuccess(sn, transaction_id, ordertype);







           System.out.println("即时到帐付款成功");
         } else {
           System.out.println("即时到帐付款失败");
         }
       } else if ("2".equals(trade_mode)) {
         if ("0".equals(trade_state))
         {



           sn = out_trade_no;
           paySuccess(sn, transaction_id, ordertype);







           System.out.println("中介担保付款成功");
         } else {
           System.out.println("trade_state=" + trade_state);
         }
       }
     } else {
       System.out.println("认证签名失败");
     }


     String debuginfo = resHandler.getDebugInfo();
     return sn;
   }


   public String getId()
   {
     return "tenpayDirectPlugin";
   }


   public String getName()
   {
     return "财付通即时到账接口";
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\payment\plugin\tenpay\direct\TenpayDirectPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */