 package com.enation.app.shop.component.shortmsg.plugin;

 import com.enation.app.base.core.model.ShortMsg;
 import com.enation.app.base.core.plugin.shortmsg.IShortMessageEvent;
 import com.enation.app.base.core.service.IShortMsgManager;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.auth.IPermissionManager;
 import com.enation.app.base.core.service.auth.IRoleManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.app.shop.core.model.Allocation;
 import com.enation.app.shop.core.model.AllocationItem;
 import com.enation.app.shop.core.model.DepotUser;
 import com.enation.app.shop.core.service.IDepotUserManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.ArrayList;
 import java.util.List;
 import org.springframework.stereotype.Component;












 @Component
 public class ShopShortMsgPlugin
   extends AutoRegisterPlugin
   implements IShortMessageEvent
 {
   private IShortMsgManager shortMsgManager;
   private IDepotUserManager depotUserManager;
   private IRoleManager roleManager;
   private IAdminUserManager adminUserManager;
   private IPermissionManager permissionManager;
   private IDaoSupport daoSupport;
   private IDaoSupport baseDaoSupport;
   private IDBRouter baseDBRouter;

   public List<ShortMsg> getMessage()
   {
     List<ShortMsg> msgList = new ArrayList();
     AdminUser adminUser = this.adminUserManager.getCurrentUser();

     boolean haveDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
     boolean haveFinance = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("finance"));
     boolean haveOrder = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("order"));
     boolean haveCustomerService = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("customer_service"));


     if ((haveOrder) || (haveCustomerService)) {
       ShortMsg msg = getReturnsOrderMessage();
       if (msg != null) {
         msgList.add(msg);
       }
     }


     if (haveCustomerService) {
       ShortMsg discuss = getDiscussMessage();
       ShortMsg ask = getAskMessage();
       ShortMsg confirm = getOrderConfirm();
       if (discuss != null) {
         msgList.add(discuss);
       }
       if (ask != null) {
         msgList.add(ask);
       }
       if (confirm == null) {}
     }





     if (haveDepotAdmin) {
       ShortMsg msg = getShipMessage(adminUser);
       if (msg != null) {
         msgList.add(msg);
       }
     }

     if (haveFinance) {
       ShortMsg msg = getPayConfirmMessage();
       if (msg != null) {
         msgList.add(msg);
       }
     }
     return msgList;
   }




   private ShortMsg getOrderConfirm()
   {
     String sql = "select count(0) from order where status=? and   payment_type = 'cod'";
     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(0) });
     if (count > 0) {
       ShortMsg msg = new ShortMsg();
       msg.setUrl("/shop/admin/order!listbyship.do?state=0&shipping_id=2");
       msg.setTitle("有" + count + "个订单需要完成确认订单任务");
       msg.setTarget("ajax");
       return msg;
     }
     return null;
   }




   private ShortMsg getPayConfirmMessage()
   {
     String sql = "select count(0) from order where  disabled=0 and status=? and payment_type != 'cod'  ";
     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(0) });
     String sql_1 = "select count(0) from order where disabled=0 and status=? and payment_type = 'cod' ";
     int count_1 = this.baseDaoSupport.queryForInt(sql_1, new Object[] { Integer.valueOf(6) });
     if (count + count_1 > 0) {
       ShortMsg msg = new ShortMsg();
       msg.setUrl("/shop/admin/order!notPayOrder.do");
       msg.setTitle("待结算订单");
       msg.setTarget("ajax");
       msg.setContent("有" + (count + count_1) + "个新订单需要您结算");
       return msg;
     }
     return null;
   }




   private ShortMsg getShipMessage(AdminUser adminUser)
   {
     int count = 0;
     if (adminUser.getFounder() == 0) {
       DepotUser depotUser = (DepotUser)adminUser;
       String sql = "select count(0) from order where status=? and depotid=? and disabled=0";
       count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(4), depotUser.getDepotid() });
     } else {
       String sql = "select count(0) from order where (status=? or (payment_type='cod' and status=0)) and disabled=0 ";
       count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(2) });
     }

     if (count > 0) {
       ShortMsg msg = new ShortMsg();
       msg.setUrl("/shop/admin/order!notShipOrder.do");
       msg.setTitle("待发货订单");
       msg.setTarget("ajax");
       msg.setContent("有" + count + "个订单需要您发货");
       return msg;
     }
     return null;
   }








   private ShortMsg getStockMessage(AdminUser adminUser)
   {
     int count = 0;
     if (adminUser.getFounder() == 0) {
       DepotUser depotUser = (DepotUser)adminUser;

       String sql = "select count(0) from " + this.baseDBRouter.getTableName("goods") + " where goods_id in (select goodsid from " + this.baseDBRouter.getTableName("goods_depot") + "   where iscmpl=0 and depotid=?) ";
       count = this.daoSupport.queryForInt(sql, new Object[] { depotUser.getDepotid() });
     }
     else {
       String sql = "select count(0) from " + this.baseDBRouter.getTableName("goods") + " where goods_id in (select goodsid from " + this.baseDBRouter.getTableName("goods_depot") + "   where iscmpl=0 ) ";
       count = this.daoSupport.queryForInt(sql, new Object[0]);
     }
     if (count > 0) {
       ShortMsg msg = new ShortMsg();
       msg.setUrl("/shop/admin/goods!list.do?optype=stock");
       msg.setTitle("进货");
       msg.setContent("有" + count + "个商品需要您完成进货任务");

       return msg;
     }


     return null;
   }





   private ShortMsg getAlloMessage()
   {
     String sql = "select count(0) from order where status=? ";
     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(2) });

     if (count > 0) {
       ShortMsg msg = new ShortMsg();
       msg.setUrl("/shop/admin/order!list.do?state=2");
       msg.setTitle("有" + count + "个订单需要您下达配货任务");
       msg.setTarget("ajax");
       return msg;
     }
     return null;
   }




   private ShortMsg getReturnsOrderMessage()
   {
     String sql = "select count(0) from returns_order where state =? ";
     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { Integer.valueOf(0) });

     if (count > 0) {
       ShortMsg msg = new ShortMsg();
       msg.setUrl("/shop/admin/returnOrder!returnsApplyList.do?state=0");
       msg.setTitle("退换货申请单");
       msg.setTarget("ajax");
       msg.setContent("有" + count + "个退换货申请单");
       return msg;
     }
     return null;
   }





   private ShortMsg getDiscussMessage()
   {
     String sql = "select count(0) from member_comment where type=1 and status=0 ";
     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);

     if (count > 0) {
       ShortMsg msg = new ShortMsg();
       msg.setUrl("/shop/admin/comments!msgList.do?type=1&status=0");
       msg.setTitle("评论列表");
       msg.setTarget("ajax");
       msg.setContent("有" + count + "个评论需要您处理。");
       return msg;
     }
     return null;
   }




   private ShortMsg getAskMessage()
   {
     String sql = "select count(0) from member_comment where type=2 and status=0 ";
     int count = this.baseDaoSupport.queryForInt(sql, new Object[0]);

     if (count > 0) {
       ShortMsg msg = new ShortMsg();
       msg.setUrl("/shop/admin/comments!list.do?type=2");
       msg.setTitle("咨询列表");
       msg.setTarget("ajax");
       msg.setContent("有" + count + "个咨询需要您处理。");
       return msg;
     }
     return null;
   }







   private ShortMsg getDepotAlloMessage(AdminUser adminUser)
   {
     int count = 0;
     if (adminUser.getFounder() == 0) {
       DepotUser depotUser = (DepotUser)adminUser;
       String sql = "select count(0) from allocation_item where iscmpl=0 and depotid=?";
       count = this.baseDaoSupport.queryForInt(sql, new Object[] { depotUser.getDepotid() });
     } else {
       String sql = "select count(0) from allocation_item where iscmpl=0 ";
       count = this.baseDaoSupport.queryForInt(sql, new Object[0]);
     }


     if (count > 0) {
       ShortMsg msg = new ShortMsg();
       msg.setUrl("/shop/admin/orderReport!allocationList.do");
       msg.setTitle("有" + count + "个商品需要您完成配货任务");
       return msg;
     }
     return null;
   }






























   public void onAllocation(Allocation allocation) {}





























   private int findItemCount(int depotid, List<AllocationItem> itemList)
   {
     int count = 0;
     for (AllocationItem item : itemList) {
       if (depotid == item.getDepotid()) {
         count++;
       }
     }

     return count;
   }




   public IShortMsgManager getShortMsgManager()
   {
     return this.shortMsgManager;
   }

   public void setShortMsgManager(IShortMsgManager shortMsgManager) {
     this.shortMsgManager = shortMsgManager;
   }

   public IDepotUserManager getDepotUserManager() {
     return this.depotUserManager;
   }

   public void setDepotUserManager(IDepotUserManager depotUserManager) {
     this.depotUserManager = depotUserManager;
   }

   public IRoleManager getRoleManager()
   {
     return this.roleManager;
   }

   public void setRoleManager(IRoleManager roleManager) {
     this.roleManager = roleManager;
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public IPermissionManager getPermissionManager() {
     return this.permissionManager;
   }

   public void setPermissionManager(IPermissionManager permissionManager) {
     this.permissionManager = permissionManager;
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IDaoSupport getBaseDaoSupport() {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
     this.baseDaoSupport = baseDaoSupport;
   }


   public IDBRouter getBaseDBRouter()
   {
     return this.baseDBRouter;
   }

   public void setBaseDBRouter(IDBRouter baseDBRouter) {
     this.baseDBRouter = baseDBRouter;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\shortmsg\plugin\ShopShortMsgPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */