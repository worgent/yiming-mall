 package com.enation.app.shop.component.product.plugin.order;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.core.model.AllocationItem;
 import com.enation.app.shop.core.model.Delivery;
 import com.enation.app.shop.core.model.DeliveryItem;
 import com.enation.app.shop.core.model.Depot;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.model.StoreLog;
 import com.enation.app.shop.core.plugin.order.IOrderCanelEvent;
 import com.enation.app.shop.core.plugin.order.IOrderChangeDepotEvent;
 import com.enation.app.shop.core.plugin.order.IOrderItemSaveEvent;
 import com.enation.app.shop.core.plugin.order.IOrderShipEvent;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IProductStoreManager;
 import com.enation.app.shop.core.service.IStoreLogManager;
 import com.enation.app.shop.core.service.StoreLogType;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.DateUtil;
 import java.util.List;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;













 @Component
 public class GenericOrderPlugin
   extends AutoRegisterPlugin
   implements IOrderShipEvent, IOrderCanelEvent, IOrderItemSaveEvent, IOrderChangeDepotEvent
 {
   private IDaoSupport baseDaoSupport;
   private IDaoSupport daoSupport;
   private IProductStoreManager productStoreManager;
   private IOrderManager orderManager;
   private IStoreLogManager storeLogManager;
   private IDepotManager depotManager;
   private IAdminUserManager adminUserManager;

   @Transactional(propagation=Propagation.REQUIRED)
   public void onItemSave(Order order, OrderItem item)
   {
     Integer order_id = order.getOrder_id();
     String ordersn = order.getSn();
     int depotid = order.getDepotid().intValue();

     this.productStoreManager.decreaseEnable(item.getGoods_id().intValue(), item.getProduct_id().intValue(), depotid, item.getNum().intValue());


     StoreLog storeLog = new StoreLog();
     storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
     storeLog.setDepot_type(Integer.valueOf(0));
     storeLog.setDepotid(Integer.valueOf(depotid));
     storeLog.setGoodsid(item.getGoods_id());
     storeLog.setGoodsname(item.getName());
     storeLog.setNum(Integer.valueOf(0));
     storeLog.setEnable_store(0 - item.getNum().intValue());
     storeLog.setOp_type(Integer.valueOf(StoreLogType.create_order.getType()));
     storeLog.setProductid(item.getProduct_id());
     storeLog.setUserid(Integer.valueOf(0));
     storeLog.setRemark("创建订单[" + ordersn + "]，减少可用库存");
     storeLog.setUsername("系统");
     this.storeLogManager.add(storeLog);
   }








   public void itemShip(Order order, DeliveryItem deliveryItem, AllocationItem allocationItem)
   {
     int depotid = order.getDepotid().intValue();
     int num = deliveryItem.getNum().intValue();
     int goodsid = deliveryItem.getGoods_id().intValue();
     int productid = deliveryItem.getProduct_id().intValue();
     String name = deliveryItem.getName();


     StoreLog storeLog = new StoreLog();
     storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
     storeLog.setDepot_type(Integer.valueOf(0));
     storeLog.setDepotid(Integer.valueOf(depotid));
     storeLog.setGoodsid(Integer.valueOf(goodsid));
     storeLog.setGoodsname(name);
     storeLog.setNum(Integer.valueOf(0 - deliveryItem.getNum().intValue()));
     storeLog.setOp_type(Integer.valueOf(StoreLogType.ship.getType()));
     storeLog.setProductid(Integer.valueOf(productid));
     storeLog.setUserid(Integer.valueOf(0));
     storeLog.setUsername("系统");
     storeLog.setRemark("订单[" + order.getSn() + "]发货，减少库存");
     this.storeLogManager.add(storeLog);


     this.productStoreManager.decreaseStroe(goodsid, productid, depotid, num);
     String updatesql = "update goods set buy_count=buy_count+" + num + " where goods_id=" + goodsid;
     this.baseDaoSupport.execute(updatesql, new Object[0]);
   }







   @Transactional(propagation=Propagation.REQUIRED)
   public void canel(Order order)
   {
     int orderid = order.getOrder_id().intValue();
     List<OrderItem> itemList = this.orderManager.listGoodsItems(Integer.valueOf(orderid));
     for (OrderItem orderItem : itemList) {
       int goodsid = orderItem.getGoods_id().intValue();
       int productid = orderItem.getProduct_id().intValue();
       int num = orderItem.getNum().intValue();
       int depotid = order.getDepotid().intValue();
       String name = orderItem.getName();


       StoreLog storeLog = new StoreLog();
       storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
       storeLog.setDepot_type(Integer.valueOf(0));
       storeLog.setDepotid(Integer.valueOf(depotid));
       storeLog.setGoodsid(Integer.valueOf(goodsid));
       storeLog.setGoodsname(name);
       storeLog.setNum(Integer.valueOf(0));
       storeLog.setEnable_store(num);
       storeLog.setRemark("取消订单[" + order.getSn() + "],增加可用库存");
       storeLog.setOp_type(Integer.valueOf(StoreLogType.cancel_order.getType()));
       storeLog.setProductid(Integer.valueOf(productid));
       storeLog.setUserid(Integer.valueOf(0));
       storeLog.setUsername("系统");
       this.storeLogManager.add(storeLog);

       this.productStoreManager.increaseEnable(goodsid, productid, depotid, num);
     }
   }


   @Transactional(propagation=Propagation.REQUIRED)
   public void chaneDepot(Order order, int newdepotid, List<OrderItem> itemList)
   {
     Depot depot = this.depotManager.get(newdepotid);
     for (OrderItem item : itemList) {
       int goodsid = item.getGoods_id().intValue();
       int num = item.getNum().intValue();
       int store = this.productStoreManager.getEnableStroe(goodsid, newdepotid);
       if (store < num)
       {
         throw new RuntimeException("[" + item.getName() + "]库存不足，请求库存[" + num + "]在[" + depot.getName() + "]中不足，可用库存为[" + store + "]");
       }
       int olddepotid = order.getDepotid().intValue();
       this.productStoreManager.decreaseEnable(goodsid, item.getProduct_id().intValue(), newdepotid, num);
       this.productStoreManager.increaseEnable(goodsid, item.getProduct_id().intValue(), olddepotid, num);

       int userid = 0;
       String username = "系统";

       AdminUser adminUser = this.adminUserManager.getCurrentUser();
       if (adminUser != null) {
         userid = adminUser.getUserid().intValue();
         username = adminUser.getUsername();
       }




       StoreLog storeLog = new StoreLog();
       storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
       storeLog.setDepot_type(Integer.valueOf(0));
       storeLog.setDepotid(Integer.valueOf(olddepotid));
       storeLog.setGoodsid(Integer.valueOf(goodsid));
       storeLog.setGoodsname(item.getName());
       storeLog.setNum(Integer.valueOf(0));
       storeLog.setEnable_store(num);
       storeLog.setRemark("订单[" + order.getSn() + "]仓库修改为[" + depot.getName() + "],增加可用库存");
       storeLog.setOp_type(Integer.valueOf(StoreLogType.change_depot.getType()));
       storeLog.setProductid(item.getProduct_id());
       storeLog.setUserid(Integer.valueOf(userid));
       storeLog.setUsername(username);
       this.storeLogManager.add(storeLog);


       storeLog.setDepotid(Integer.valueOf(newdepotid));
       storeLog.setEnable_store(0 - num);
       storeLog.setRemark("订单[" + order.getSn() + "]仓库修改为[" + depot.getName() + "],减少可用库存");
       this.storeLogManager.add(storeLog);
     }
   }







   public void ship(Delivery delivery, List<DeliveryItem> itemList) {}






   public boolean canBeExecute(int catid)
   {
     return true;
   }

   public IDaoSupport getBaseDaoSupport() {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
     this.baseDaoSupport = baseDaoSupport;
   }

   public IProductStoreManager getProductStoreManager() {
     return this.productStoreManager;
   }

   public void setProductStoreManager(IProductStoreManager productStoreManager) {
     this.productStoreManager = productStoreManager;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }

   public IStoreLogManager getStoreLogManager() {
     return this.storeLogManager;
   }

   public void setStoreLogManager(IStoreLogManager storeLogManager) {
     this.storeLogManager = storeLogManager;
   }

   public IDaoSupport getDaoSupport()
   {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport)
   {
     this.daoSupport = daoSupport;
   }

   public IDepotManager getDepotManager()
   {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager)
   {
     this.depotManager = depotManager;
   }

   public IAdminUserManager getAdminUserManager()
   {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager)
   {
     this.adminUserManager = adminUserManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\product\plugin\order\GenericOrderPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */