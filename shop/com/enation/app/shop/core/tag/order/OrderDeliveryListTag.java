 package com.enation.app.shop.core.tag.order;

 import com.enation.app.shop.core.model.Delivery;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IOrderReportManager;
 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component
 @Scope("prototype")
 public class OrderDeliveryListTag
   extends BaseFreeMarkerTag
 {
   private IOrderManager orderManager;
   private IMemberManager memberManager;
   private IOrderReportManager orderReportManager;
   private Integer orderid;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer orderid = (Integer)params.get("orderid");
     if (orderid == null) {
       throw new TemplateModelException("必须传递orderid参数");
     }
     List<Delivery> deliveryList = this.orderReportManager.getDeliveryList(orderid.intValue());
     Map result = new HashMap();
     result.put("deliveryList", deliveryList);
     result.putAll(OrderStatus.getOrderStatusMap());
     return result;
   }

   public IOrderManager getOrderManager() { return this.orderManager; }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IMemberManager getMemberManager() { return this.memberManager; }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public IOrderReportManager getOrderReportManager() { return this.orderReportManager; }

   public void setOrderReportManager(IOrderReportManager orderReportManager) {
     this.orderReportManager = orderReportManager;
   }

   public Integer getOrderid() { return this.orderid; }

   public void setOrderid(Integer orderid) {
     this.orderid = orderid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\order\OrderDeliveryListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */