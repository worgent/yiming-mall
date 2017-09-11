 package com.enation.app.shop.component.product.plugin.store;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.auth.IPermissionManager;
 import com.enation.app.base.core.service.auth.IRoleManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.app.shop.core.model.DepotUser;
 import com.enation.app.shop.core.model.StoreLog;
 import com.enation.app.shop.core.model.WarnNum;
 import com.enation.app.shop.core.plugin.goods.AbstractGoodsStorePlugin;
 import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
 import com.enation.app.shop.core.service.IGoodsStoreManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.app.shop.core.service.IStoreLogManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.IDBRouter;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.IntegerMapper;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;








 @Component
 public class GenericGoodsStorePlugin
   extends AbstractGoodsStorePlugin
   implements IGoodsDeleteEvent
 {
   private IAdminUserManager adminUserManager;
   private IPermissionManager permissionManager;
   private IGoodsStoreManager goodsStoreManager;
   private IRoleManager roleManager;
   private IDaoSupport baseDaoSupport;
   private IDaoSupport daoSupport;
   private IDBRouter baseDBRouter;
   private IStoreLogManager storeLogManager;
   private IOrderManager orderManager;

   public String getStoreHtml(Map goods)
   {
     Integer goodsid = (Integer)goods.get("goods_id");
     Integer productid = getProductId(goodsid);

     List<Map> storeList = null;
     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     if (adminUser.getFounder() != 1) {
       boolean isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));

       if (isStorer) {
         DepotUser depotUser = (DepotUser)adminUser;
         int depotid = depotUser.getDepotid().intValue();
         storeList = this.goodsStoreManager.ListProductDepotStore(productid, Integer.valueOf(depotid));
       } else {
         return "您没有修改库存的权限";
       }
     } else {
       storeList = this.goodsStoreManager.listProductStore(productid);
     }

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("storeList", storeList);
     return freeMarkerPaser.proessPageContent();
   }

   public String getStockHtml(Map goods)
   {
     Integer goodsid = (Integer)goods.get("goods_id");
     Integer productid = getProductId(goodsid);

     List<Map> storeList = null;
     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     if (adminUser.getFounder() != 1) {
       boolean isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
       if (isStorer) {
         DepotUser depotUser = (DepotUser)adminUser;
         if (depotUser.getDepotid() == null)
           return "管理员没有设置库房";
         int depotid = depotUser.getDepotid().intValue();
         storeList = this.goodsStoreManager.ListProductDepotStore(productid, Integer.valueOf(depotid));
       } else {
         return "您没有修改库存的权限";
       }
     } else {
       storeList = this.goodsStoreManager.listProductStore(productid);
     }

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setPageName("stockList");
     freeMarkerPaser.putData("storeList", storeList);
     return freeMarkerPaser.proessPageContent();
   }

   public String getShipHtml(Map goods)
   {
     Integer goodsid = (Integer)goods.get("goods_id");
     Integer productid = getProductId(goodsid);

     List<Map> storeList = null;
     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     if (adminUser.getFounder() != 1) {
       boolean isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
       if (isStorer) {
         DepotUser depotUser = (DepotUser)adminUser;
         int depotid = depotUser.getDepotid().intValue();
         storeList = this.goodsStoreManager.ListProductDepotStore(productid, Integer.valueOf(depotid));
       } else {
         return "您没有修改库存的权限";
       }
     } else {
       storeList = this.goodsStoreManager.listProductStore(productid);
     }

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setPageName("shipList");
     freeMarkerPaser.putData("storeList", storeList);
     return freeMarkerPaser.proessPageContent();
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void onStockSave(Map goods)
   {
     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));

     Integer goodsid = (Integer)goods.get("goods_id");
     Integer productid = getProductId(goodsid);

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String[] storeidAr = request.getParameterValues("storeid");
     String[] storeAr = request.getParameterValues("storeNum");
     String[] depotidAr = request.getParameterValues("depotid");

     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     boolean isFounder = adminUser.getFounder() == 1;
     boolean isStorer = false;
     if (!isFounder) {
       isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
     }

     if ((!isFounder) && (!isStorer)) {
       throw new RuntimeException("您没有权限维护库存");
     }
     int total = 0;
     for (int i = 0; i < storeidAr.length; i++) {
       int storeid = StringUtil.toInt(storeidAr[i], true);
       int store = StringUtil.toInt(storeAr[i], true);
       int depotid = StringUtil.toInt(depotidAr[i], true);

       if (storeid == 0) {
         this.baseDaoSupport.execute("insert into product_store(goodsid,productid,depotid,store,enable_store)values(?,?,?,?,?)", new Object[] { goodsid, productid, Integer.valueOf(depotid), Integer.valueOf(store), Integer.valueOf(store) });
       } else {
         this.baseDaoSupport.execute("update product_store set store=store+?,enable_store=enable_store+? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(store), Integer.valueOf(storeid) });
       }
       total += store;
     }


     this.baseDaoSupport.execute("update goods_depot set iscmpl=1 where goodsid=? and depotid=?", new Object[] { goodsid, depotidAr[0] });


     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("goods") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { goodsid, goodsid });



     this.daoSupport.execute("update es_goods set enable_store=(select sum(enable_store) from  es_product_store  where goodsid=?) where goods_id=? ", new Object[] { goodsid, goodsid });

     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("product") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { productid, productid });



     this.daoSupport.execute("update es_product set enable_store=(select sum(enable_store) from  es_product_store  where productid=?) where product_id=? ", new Object[] { productid, productid });
















     if (isSuperAdmin) {
       for (String deptoid : depotidAr) {
         int did = StringUtil.toInt(deptoid, true);
         StoreLog storeLog = new StoreLog();
         storeLog.setGoodsid(goodsid);
         storeLog.setGoodsname(goods.get("name").toString());
         storeLog.setDepot_type(Integer.valueOf(0));
         storeLog.setOp_type(Integer.valueOf(EopSetting.getDepotType(did)));
         storeLog.setDepotid(Integer.valueOf(did));
         storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
         storeLog.setNum(Integer.valueOf(total));
         storeLog.setUserid(adminUser.getUserid());
         storeLog.setUsername(adminUser.getUsername());
         storeLog.setEnable_store(total);
         this.storeLogManager.add(storeLog);
       }
     } else if (isDepotAdmin) {
       DepotUser depotUser = (DepotUser)adminUser;
       StoreLog storeLog = new StoreLog();
       storeLog.setGoodsid(goodsid);
       storeLog.setGoodsname(goods.get("name").toString());
       storeLog.setDepot_type(Integer.valueOf(EopSetting.getDepotType(depotUser.getDepotid().intValue())));
       storeLog.setOp_type(Integer.valueOf(0));
       storeLog.setDepotid(depotUser.getDepotid());
       storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
       storeLog.setNum(Integer.valueOf(total));
       storeLog.setEnable_store(total);
       storeLog.setUserid(adminUser.getUserid());
       storeLog.setUsername(adminUser.getUsername());

       this.storeLogManager.add(storeLog);
     } else {
       throw new RuntimeException("没有操作库存的权限");
     }
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void onShipSave(Map goods)
   {
     Integer goodsid = (Integer)goods.get("goods_id");
     Integer productid = getProductId(goodsid);

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String[] storeidAr = request.getParameterValues("storeid");
     String[] storeAr = request.getParameterValues("store");

     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     boolean isFounder = adminUser.getFounder() == 1;
     boolean isStorer = false;
     if (!isFounder) {
       isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
     }

     if ((!isFounder) && (!isStorer)) {
       throw new RuntimeException("您没有权限维护库存");
     }
     int total = 0;
     for (int i = 0; i < storeidAr.length; i++) {
       int storeid = StringUtil.toInt(storeidAr[i], true);
       int store = StringUtil.toInt(storeAr[i], true);

       if (storeid == 0) {
         throw new RuntimeException("商品库存为0，不能出货");
       }
       this.baseDaoSupport.execute("update product_store set store=store-? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });

       total += store;
     }

     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("goods") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { goodsid, goodsid });



     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("product") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { productid, productid });




     DepotUser depotUser = (DepotUser)adminUser;
     StoreLog storeLog = new StoreLog();
     storeLog.setGoodsid(goodsid);
     storeLog.setGoodsname(goods.get("name").toString());
     storeLog.setDepot_type(Integer.valueOf(EopSetting.getDepotType(depotUser.getDepotid().intValue())));
     storeLog.setOp_type(Integer.valueOf(1));
     storeLog.setDepotid(depotUser.getDepotid());
     storeLog.setDateline(Integer.valueOf(DateUtil.getDateline()));
     storeLog.setNum(Integer.valueOf(total));
     storeLog.setUserid(adminUser.getUserid());
     storeLog.setUsername(adminUser.getUsername());
     this.storeLogManager.add(storeLog);
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void onStoreSave(Map goods)
   {
     Integer goodsid = (Integer)goods.get("goods_id");
     Integer productid = getProductId(goodsid);

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String[] storeidAr = request.getParameterValues("storeid");
     String[] storeAr = request.getParameterValues("storeNum");
     String[] depotidAr = request.getParameterValues("depotid");

     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     boolean isFounder = adminUser.getFounder() == 1;
     boolean isStorer = false;
     if (!isFounder) {
       isStorer = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));
     }

     if ((!isFounder) && (!isStorer)) {
       throw new RuntimeException("您没有权限维护库存");
     }

     for (int i = 0; i < storeidAr.length; i++) {
       int storeid = StringUtil.toInt(storeidAr[i], true);
       int store = StringUtil.toInt(storeAr[i], true);
       int depotid = StringUtil.toInt(depotidAr[i], true);

       if (storeid == 0) {
         this.baseDaoSupport.execute("insert into product_store(goodsid,productid,depotid,store)values(?,?,?,?)", new Object[] { goodsid, productid, Integer.valueOf(depotid), Integer.valueOf(store) });
       } else {
         this.baseDaoSupport.execute("update product_store set store=? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });
       }
     }


     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("goods") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { goodsid, goodsid });






     this.daoSupport.execute("update " + this.baseDBRouter.getTableName("product") + " set store=(select sum(store) from " + this.baseDBRouter.getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { productid, productid });
   }







   @Transactional(propagation=Propagation.REQUIRED)
   public void onGoodsDelete(Integer[] goodsid)
   {
     if ((goodsid == null) || (goodsid.length == 0))
       return;
     this.baseDaoSupport.execute("delete from product_store where goodsid in(" + StringUtil.arrayToString(goodsid, ",") + ")", new Object[0]);
   }

   private Integer getProductId(Integer goodsid) {
     String sql = "select product_id from product where goods_id = ?";
     List<Integer> productidList = this.baseDaoSupport.queryForList(sql, new IntegerMapper(), new Object[] { goodsid });
     Integer productid = (Integer)productidList.get(0);
     return productid;
   }

   public boolean canBeExecute(Map goods)
   {
     return true;
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

   public IRoleManager getRoleManager() {
     return this.roleManager;
   }

   public void setRoleManager(IRoleManager roleManager) {
     this.roleManager = roleManager;
   }

   public IGoodsStoreManager getGoodsStoreManager() {
     return this.goodsStoreManager;
   }

   public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager) {
     this.goodsStoreManager = goodsStoreManager;
   }

   public IDaoSupport getBaseDaoSupport() {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
     this.baseDaoSupport = baseDaoSupport;
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IDBRouter getBaseDBRouter() {
     return this.baseDBRouter;
   }

   public void setBaseDBRouter(IDBRouter baseDBRouter) {
     this.baseDBRouter = baseDBRouter;
   }

   public IStoreLogManager getStoreLogManager() {
     return this.storeLogManager;
   }

   public void setStoreLogManager(IStoreLogManager storeLogManager) {
     this.storeLogManager = storeLogManager;
   }

   public String getWarnNumHtml(Map goods)
   {
     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     int foud = adminUser.getFounder();
     if (foud != 1) {
       return "您没有修改报警数的权限";
     }
     Integer goodsid = Integer.valueOf(goods.get("goods_id").toString());
     List<WarnNum> warnList = this.goodsStoreManager.listWarns(goodsid);
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setPageName("warn_num");
     freeMarkerPaser.putData("warnList", warnList);
     return freeMarkerPaser.proessPageContent();
   }

   public void onWarnSave(Map goods)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     Integer goodsid = (Integer)goods.get("goods_id");

     String[] idArr = request.getParameterValues("warnid");
     String[] warn_numAr = request.getParameterValues("warn_num");
     if ((idArr == null) || (idArr.length == 0))
       return;
     for (int i = 0; i < idArr.length; i++) {
       int id = StringUtil.toInt(idArr[i], true);
       int warn_num = StringUtil.toInt(warn_numAr[i], true);

       WarnNum warnNum = new WarnNum();
       warnNum.setId(Integer.valueOf(id));
       warnNum.setGoods_id(goodsid);
       warnNum.setWarn_num(Integer.valueOf(warn_num));
       if (warnNum.getId().intValue() == 0) {
         this.baseDaoSupport.insert("warn_num", warnNum);
       } else {
         this.baseDaoSupport.execute("update warn_num set warn_num = ? where id=?", new Object[] { warnNum.getWarn_num(), warnNum.getId() });
       }
     }
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\product\plugin\store\GenericGoodsStorePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */