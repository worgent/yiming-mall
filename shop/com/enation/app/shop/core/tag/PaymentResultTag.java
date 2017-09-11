 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.model.PaymentResult;
 import com.enation.app.shop.core.plugin.payment.IPaymentEvent;
 import com.enation.framework.context.spring.SpringContextHolder;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.RequestUtil;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;
















 @Component
 @Scope("prototype")
 public class PaymentResultTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map p)
     throws TemplateModelException
   {
     PaymentResult paymentResult = new PaymentResult();
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       String url = RequestUtil.getRequestUrl(request);
       String pluginid = null;
       String ordertype = null;
       String[] params = getPluginid(url);

       ordertype = params[0];
       pluginid = params[1];
       if (null == pluginid) {
         paymentResult.setResult(0);
         paymentResult.setError("参数不正确");
       } else {
         IPaymentEvent paymentPlugin = (IPaymentEvent)SpringContextHolder.getBean(pluginid);
         String ordersn = paymentPlugin.onReturn(ordertype);
         paymentResult.setResult(1);
         paymentResult.setOrdersn(ordersn);
       }
     }
     catch (Exception e)
     {
       this.logger.error("支付失败", e);
       paymentResult.setResult(0);
       paymentResult.setError(e.getMessage());
     }



     return paymentResult;
   }

   private String[] getPluginid(String url) {
     String pluginid = null;
     String ordertype = null;
     String[] params = new String[2];
     String pattern = ".*/(\\w+)_(\\w+)_(payment-result).html(.*)";
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
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\PaymentResultTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */