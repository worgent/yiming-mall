 package com.enation.app.shop.core.tag.order;

 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;









 @Component
 @Scope("prototype")
 public class OrderDetailLogTag
   extends BaseFreeMarkerTag
 {
   private IOrderManager orderManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     int orderId = StringUtil.toInt(request.getParameter("orderid"), true);

     List logList = this.orderManager.listLogs(Integer.valueOf(orderId));
     return logList;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\order\OrderDetailLogTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */