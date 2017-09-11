 package com.enation.app.shop.core.tag.order;

 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;
















 @Component
 @Scope("prototype")
 public class OrderDetailTag
   extends BaseFreeMarkerTag
 {
   private IOrderManager orderManager;

   public Object exec(Map args)
     throws TemplateModelException
   {
     Integer orderid = (Integer)args.get("orderid");
     String ordersn = (String)args.get("ordersn");

     if ((orderid == null) && (StringUtil.isEmpty(ordersn))) {
       throw new TemplateModelException("必须传递orderid参数或ordersn参数");
     }

     Order order = null;

     if (orderid != null) {
       order = this.orderManager.get(orderid);
     } else if (!StringUtil.isEmpty(ordersn)) {
       order = this.orderManager.get(ordersn);
     }



     return order;
   }

   public IOrderManager getOrderManager()
   {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) { this.orderManager = orderManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\order\OrderDetailTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */