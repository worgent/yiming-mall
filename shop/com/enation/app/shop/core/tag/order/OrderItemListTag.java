 package com.enation.app.shop.core.tag.order;

 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;
















 @Component
 @Scope("prototype")
 public class OrderItemListTag
   extends BaseFreeMarkerTag
 {
   private IOrderManager orderManager;

   public Object exec(Map params)
     throws TemplateModelException
   {
     Integer orderid = (Integer)params.get("orderid");
     if (orderid == null) {
       throw new TemplateModelException("必须传递orderid参数");
     }

     List<OrderItem> itemList = this.orderManager.listGoodsItems(orderid);
     return itemList;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\order\OrderItemListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */