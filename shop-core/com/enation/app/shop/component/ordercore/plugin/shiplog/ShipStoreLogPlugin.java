 package com.enation.app.shop.component.ordercore.plugin.shiplog;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.shop.core.model.AllocationItem;
 import com.enation.app.shop.core.model.Delivery;
 import com.enation.app.shop.core.model.DeliveryItem;
 import com.enation.app.shop.core.model.DepotUser;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.StoreLog;
 import com.enation.app.shop.core.plugin.order.IOrderShipEvent;
 import com.enation.app.shop.core.service.IStoreLogManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.DateUtil;
 import java.util.List;
 import org.springframework.stereotype.Component;













 @Component
 public class ShipStoreLogPlugin
   extends AutoRegisterPlugin
   implements IOrderShipEvent
 {
   private IStoreLogManager storeLogManager;
   private IAdminUserManager adminUserManager;

   public void itemShip(Order order, DeliveryItem deliveryItem, AllocationItem allocationItem)
   {
     String other = allocationItem.getOther();
     int depotid = allocationItem.getDepotid();
     int num = allocationItem.getNum();
     int goodsid = allocationItem.getGoodsid();

     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     DepotUser depotUser = (DepotUser)adminUser;
     StoreLog storeLog = new StoreLog();
     storeLog.setGoodsid(Integer.valueOf(goodsid));

     storeLog.setDepot_type(Integer.valueOf(EopSetting.getDepotType(depotid)));
     storeLog.setOp_type(Integer.valueOf(2));
     storeLog.setDepotid(Integer.valueOf(depotid));
     storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
     storeLog.setNum(Integer.valueOf(num));
     storeLog.setUserid(adminUser.getUserid());
     storeLog.setUsername(adminUser.getUsername());
     this.storeLogManager.add(storeLog);
   }





   public void ship(Delivery delivery, List<DeliveryItem> itemList) {}




   public boolean canBeExecute(int catid)
   {
     return true;
   }

   public IStoreLogManager getStoreLogManager()
   {
     return this.storeLogManager;
   }

   public void setStoreLogManager(IStoreLogManager storeLogManager) {
     this.storeLogManager = storeLogManager;
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\ordercore\plugin\shiplog\ShipStoreLogPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */