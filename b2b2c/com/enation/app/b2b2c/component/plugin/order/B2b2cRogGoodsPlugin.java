 package com.enation.app.b2b2c.component.plugin.order;

 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.plugin.order.IOrderRogconfirmEvent;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.List;
 import org.springframework.stereotype.Component;



 @Component
 public class B2b2cRogGoodsPlugin
   extends AutoRegisterPlugin
   implements IOrderRogconfirmEvent
 {
   private IOrderManager orderManager;
   private IDaoSupport daoSupport;

   public void rogConfirm(Order order)
   {
     List<OrderItem> orderItemList = this.orderManager.listGoodsItems(order.getOrder_id());

     for (OrderItem orderItem : orderItemList) {
       String sql = "update es_goods set buy_num=buy_num+? where goods_id=?";
       this.daoSupport.execute(sql, new Object[] { orderItem.getNum(), orderItem.getGoods_id() });
     }
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) { this.orderManager = orderManager; }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) { this.daoSupport = daoSupport; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\order\B2b2cRogGoodsPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */