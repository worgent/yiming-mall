 package com.enation.app.shop.core.tag.order;

 import com.enation.app.shop.core.model.OrderMeta;
 import com.enation.app.shop.core.service.IOrderMetaManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component
 @Scope("prototype")
 public class OrderMetaTag
   extends BaseFreeMarkerTag
 {
   private IOrderMetaManager orderMetaManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer orderid = (Integer)params.get("orderid");
     if (orderid == null) {
       throw new TemplateModelException("必须提供orderid参数");
     }
     List<OrderMeta> metaList = this.orderMetaManager.list(orderid.intValue());
     Map map = new HashMap();

     for (OrderMeta orderMeta : metaList) {
       String key = orderMeta.getMeta_key();
       String value = orderMeta.getMeta_value();
       map.put(key, value);
     }
     return map;
   }

   public IOrderMetaManager getOrderMetaManager() { return this.orderMetaManager; }

   public void setOrderMetaManager(IOrderMetaManager orderMetaManager) {
     this.orderMetaManager = orderMetaManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\order\OrderMetaTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */