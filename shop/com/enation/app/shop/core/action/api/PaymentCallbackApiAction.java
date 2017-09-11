 package com.enation.app.shop.core.action.api;

 import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.RequestUtil;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;

 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("*payment-callback")
 public class PaymentCallbackApiAction extends WWAction
 {
   public String execute()
   {
     try
     {
       HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
       String url = RequestUtil.getRequestUrl(httpRequest);
       String pluginid = null;
       String ordertype = null;
       String[] params = getPluginid(url);

       ordertype = params[0];
       pluginid = params[1];

       String error = "参数不正确";

       if (params == null) {
         this.json = error;
         return "json_message";
       }


       if (null == pluginid) {
         this.json = error;
         return "json_message";
       }

       if (null == ordertype) {
         this.json = error;
         return "json_message";
       }


       IPaymentEvent paymentPlugin = (IPaymentEvent)SpringContextHolder.getBean(pluginid);
       this.json = paymentPlugin.onCallBack(ordertype);

       this.logger.debug("支付回调结果" + this.json);
     } catch (Exception e) {
       this.logger.error("支付回调发生错误", e);
       this.json = "error";
     }
     return "json_message";
   }

   private String[] getPluginid(String url)
   {
     String pluginid = null;
     String ordertype = null;
     String[] params = new String[2];
     String pattern = ".*/(\\w+)_(\\w+)_(payment-callback).do(.*)";
     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(url);
     if (m.find()) {
       ordertype = m.replaceAll("$1");
       pluginid = m.replaceAll("$2");
       params[0] = ordertype;
       params[1] = pluginid;
       return params;
     }
     return null;
   }

   public static void main(String[] args)
   {
     String url = "/credit_alipay_payment-callback.do";
     String pattern = ".*/(\\w+)_(\\w+)_(payment-callback).do(.*)";
     Pattern p = Pattern.compile(pattern, 34);
     Matcher m = p.matcher(url);
     String pluginid; if (m.find()) {
       String ordertype = m.replaceAll("$1");
       pluginid = m.replaceAll("$2");
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\PaymentCallbackApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */