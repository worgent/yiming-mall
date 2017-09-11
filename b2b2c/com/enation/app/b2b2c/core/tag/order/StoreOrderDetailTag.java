 package com.enation.app.b2b2c.core.tag.order;

 import com.enation.app.b2b2c.core.model.order.StoreOrder;
 import com.enation.app.b2b2c.core.service.order.IStoreOrderManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.stereotype.Component;





 @Component
 public class StoreOrderDetailTag
   extends BaseFreeMarkerTag
 {
   private IStoreOrderManager storeOrderManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer orderid = (Integer)params.get("orderid");
     String ordersn = (String)params.get("ordersn");

     if ((orderid == null) && (StringUtil.isEmpty(ordersn))) {
       throw new TemplateModelException("必须传递orderid参数或ordersn参数");
     }

     StoreOrder order = null;

     if (orderid != null) {
       order = this.storeOrderManager.get(orderid);
     } else if (!StringUtil.isEmpty(ordersn)) {
       order = this.storeOrderManager.get(ordersn);
     }
     return order;
   }

   public IStoreOrderManager getStoreOrderManager() { return this.storeOrderManager; }

   public void setStoreOrderManager(IStoreOrderManager storeOrderManager) {
     this.storeOrderManager = storeOrderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\order\StoreOrderDetailTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */