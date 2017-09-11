 package com.enation.app.shop.core.plugin.payment;

 import com.enation.app.shop.core.model.PayCfg;
 import com.enation.app.shop.core.model.PayEnable;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.text.NumberFormat;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;








 public abstract class AbstractPaymentPlugin
   extends AutoRegisterPlugin
 {
   protected IPaymentManager paymentManager;
   private IMemberManager memberManager;
   protected final Logger logger = Logger.getLogger(getClass());

   private String callbackUrl;

   private String showUrl;


   protected String getCallBackUrl(PayCfg payCfg, PayEnable order)
   {
     if (this.callbackUrl != null)
       return this.callbackUrl;
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String serverName = request.getServerName();
     int port = request.getLocalPort();
     String portstr = "";
     if (port != 80) {
       portstr = ":" + port;
     }
     String contextPath = request.getContextPath();
     return "http://" + serverName + portstr + contextPath + "/api/shop/" + order.getOrderType() + "_" + payCfg.getType() + "_payment-callback.do";
   }

   protected String getReturnUrl(PayCfg payCfg, PayEnable order)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String serverName = request.getServerName();
     int port = request.getLocalPort();
     String portstr = "";
     if (port != 80) {
       portstr = ":" + port;
     }
     String contextPath = request.getContextPath();
     return "http://" + serverName + portstr + contextPath + "/" + order.getOrderType() + "_" + payCfg.getType() + "_payment-result.html";
   }





   protected String formatPrice(Double price)
   {
     NumberFormat nFormat = NumberFormat.getNumberInstance();
     nFormat.setMaximumFractionDigits(0);
     nFormat.setGroupingUsed(false);
     return nFormat.format(price);
   }




   protected String getShowUrl(PayEnable order)
   {
     if (this.showUrl != null) { return this.showUrl;
     }
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String serverName = request.getServerName();
     int port = request.getLocalPort();
     String portstr = "";
     if (port != 80) {
       portstr = ":" + port;
     }


     String contextPath = request.getContextPath();

     if ("s".equals(order.getOrderType())) {
       return "http://" + serverName + portstr + contextPath + "/orderdetail_" + order.getSn() + ".html";
     }
     return "http://" + serverName + portstr + contextPath + "/" + order.getOrderType() + "_" + order.getSn() + ".html";
   }





   public void setCallBackUrl(String url)
   {
     this.callbackUrl = url;
   }




   public void setShowUrl(String url)
   {
     this.showUrl = url;
   }




   protected Map<String, String> getConfigParams()
   {
     return this.paymentManager.getConfigParams(getId());
   }






   protected void paySuccess(String ordersn, String tradeno, String ordertype)
   {
     PaySuccessProcessorFactory.getProcessor(ordertype).paySuccess(ordersn, tradeno, ordertype);
   }





   public abstract String getId();





   public abstract String getName();




   public IPaymentManager getPaymentManager()
   {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager) {
     this.paymentManager = paymentManager;
   }


   public IMemberManager getMemberManager()
   {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\payment\AbstractPaymentPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */