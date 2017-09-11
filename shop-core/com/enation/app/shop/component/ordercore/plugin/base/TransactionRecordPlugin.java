 package com.enation.app.shop.component.ordercore.plugin.base;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.plugin.order.IOrderRogconfirmEvent;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.DateUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;



 @Component
 public class TransactionRecordPlugin
   extends AutoRegisterPlugin
   implements IOrderRogconfirmEvent
 {
   private IDaoSupport daoSupport;
   private IMemberManager memberManager;
   private IOrderManager orderManager;

   public void rogConfirm(Order order)
   {
     Map map = new HashMap();
     map.put("order_id", order.getOrder_id());
     map.put("uname", this.memberManager.get(order.getMember_id()).getUname());
     map.put("member_id", order.getMember_id());
     map.put("rog_time", Long.valueOf(DateUtil.getDatelineLong()));
     List<OrderItem> orderItemList = this.orderManager.listGoodsItems(order.getOrder_id());

     for (OrderItem orderItem : orderItemList) {
       map.put("price", orderItem.getPrice());
       map.put("goods_num", orderItem.getNum());
       map.put("goods_id", orderItem.getGoods_id());
       this.daoSupport.insert("es_transaction_record", map);
     }
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) { this.daoSupport = daoSupport; }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) { this.memberManager = memberManager; }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) { this.orderManager = orderManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\ordercore\plugin\base\TransactionRecordPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */