 package com.enation.app.shop.component.spec.plugin.store;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.auth.IPermissionManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.app.shop.component.spec.service.IGoodsSpecStoreManager;
 import com.enation.app.shop.core.model.DepotUser;
 import com.enation.app.shop.core.model.StoreLog;
 import com.enation.app.shop.core.plugin.goods.AbstractGoodsStorePlugin;
 import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
 import com.enation.app.shop.core.service.IProductManager;
 import com.enation.app.shop.core.service.IStoreLogManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;










 @Component
 public class GoodsSpecStorePlugin
   extends AbstractGoodsStorePlugin
   implements IGoodsDeleteEvent
 {
   private IGoodsSpecStoreManager goodsSpecStoreManager;
   private IProductManager productManager;
   private IStoreLogManager storeLogManager;
   private IAdminUserManager adminUserManager;
   private IPermissionManager permissionManager;

   public void onGoodsDelete(Integer[] goodsid) {}

   public String getStoreHtml(Map goods)
   {
     Integer goodsid = (Integer)goods.get("goods_id");
     List<Map> depotList = this.goodsSpecStoreManager.listGoodsStore(goodsid.intValue());
     List<String> specNameList = this.productManager.listSpecName(goodsid.intValue());

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("depotList", depotList);
     freeMarkerPaser.putData("specNameList", specNameList);
     freeMarkerPaser.setPageName("store_input");
     return freeMarkerPaser.proessPageContent();
   }




   public String getStockHtml(Map goods)
   {
     Integer goodsid = (Integer)goods.get("goods_id");
     List<Map> depotList = this.goodsSpecStoreManager.listGoodsStore(goodsid.intValue());
     List<String> specNameList = this.productManager.listSpecName(goodsid.intValue());

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("depotList", depotList);
     freeMarkerPaser.putData("specNameList", specNameList);
     freeMarkerPaser.setPageName("stock_input");
     return freeMarkerPaser.proessPageContent();
   }

   public String getShipHtml(Map goods)
   {
     Integer goodsid = (Integer)goods.get("goods_id");
     List<Map> depotList = this.goodsSpecStoreManager.listGoodsStore(goodsid.intValue());
     List<String> specNameList = this.productManager.listSpecName(goodsid.intValue());

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("depotList", depotList);
     freeMarkerPaser.putData("specNameList", specNameList);
     freeMarkerPaser.setPageName("ship_input");
     return freeMarkerPaser.proessPageContent();
   }

   public void onStoreSave(Map goods)
   {
     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     Integer goodsid = (Integer)goods.get("goods_id");
     String[] idAr = request.getParameterValues("id");
     String[] productidAr = request.getParameterValues("productid");
     String[] depotidAr = request.getParameterValues("depotid");
     String[] storeAr = request.getParameterValues("storeNum");

     int total = this.goodsSpecStoreManager.saveStore(goodsid.intValue(), idAr, productidAr, depotidAr, storeAr);


     AdminUser adminUser = this.adminUserManager.getCurrentUser();

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
       storeLog.setUserid(adminUser.getUserid());
       storeLog.setUsername(adminUser.getUsername());
       this.storeLogManager.add(storeLog);
     } else {
       throw new RuntimeException("没有操作库存的权限");
     }
   }





   public void onStockSave(Map goods)
   {
     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     Integer goodsid = (Integer)goods.get("goods_id");
     String[] idAr = request.getParameterValues("id");
     String[] productidAr = request.getParameterValues("productid");
     String[] depotidAr = request.getParameterValues("depotid");
     String[] storeAr = request.getParameterValues("storeNum");

     int total = this.goodsSpecStoreManager.stock(goodsid.intValue(), idAr, productidAr, depotidAr, storeAr);
     if (depotidAr == null) {
       return;
     }

     AdminUser adminUser = this.adminUserManager.getCurrentUser();

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
       storeLog.setUserid(adminUser.getUserid());
       storeLog.setUsername(adminUser.getUsername());
       this.storeLogManager.add(storeLog);
     } else {
       throw new RuntimeException("没有操作库存的权限");
     }
   }

   public void onShipSave(Map goods)
   {
     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));

     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     Integer goodsid = (Integer)goods.get("goods_id");
     String[] idAr = request.getParameterValues("id");
     String[] productidAr = request.getParameterValues("productid");
     String[] depotidAr = request.getParameterValues("depotid");
     String[] storeAr = request.getParameterValues("store");

     int total = this.goodsSpecStoreManager.ship(goodsid.intValue(), idAr, productidAr, depotidAr, storeAr);


     AdminUser adminUser = this.adminUserManager.getCurrentUser();
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
       storeLog.setUserid(adminUser.getUserid());
       storeLog.setUsername(adminUser.getUsername());
       this.storeLogManager.add(storeLog);
     } else {
       throw new RuntimeException("没有操作库存的权限");
     }
   }

   public boolean canBeExecute(Map goods)
   {
     return true;
   }

   public String getWarnNumHtml(Map goods)
   {
     return null;
   }


   public void onWarnSave(Map goods) {}


   public IGoodsSpecStoreManager getGoodsSpecStoreManager()
   {
     return this.goodsSpecStoreManager;
   }

   public void setGoodsSpecStoreManager(IGoodsSpecStoreManager goodsSpecStoreManager) {
     this.goodsSpecStoreManager = goodsSpecStoreManager;
   }

   public IProductManager getProductManager() {
     return this.productManager;
   }

   public void setProductManager(IProductManager productManager) {
     this.productManager = productManager;
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public IStoreLogManager getStoreLogManager() {
     return this.storeLogManager;
   }

   public void setStoreLogManager(IStoreLogManager storeLogManager) {
     this.storeLogManager = storeLogManager;
   }

   public IPermissionManager getPermissionManager() {
     return this.permissionManager;
   }

   public void setPermissionManager(IPermissionManager permissionManager) {
     this.permissionManager = permissionManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\plugin\store\GoodsSpecStorePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */