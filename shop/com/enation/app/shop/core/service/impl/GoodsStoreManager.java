 package com.enation.app.shop.core.service.impl;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.auth.IPermissionManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.app.shop.core.model.Depot;
 import com.enation.app.shop.core.model.DepotUser;
 import com.enation.app.shop.core.model.WarnNum;
 import com.enation.app.shop.core.plugin.goods.GoodsStorePluginBundle;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.app.shop.core.service.IGoodsStoreManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;

 public class GoodsStoreManager
   extends BaseSupport
   implements IGoodsStoreManager
 {
   private GoodsStorePluginBundle goodsStorePluginBundle;
   private IDepotManager depotManager;
   private IAdminUserManager adminUserManager;
   private IPermissionManager permissionManager;

   public List<Map> listProductStore(Integer productid)
   {
     List<Depot> depotList = this.depotManager.list();
     List<Map> depotStoreList = new ArrayList();

     String sql = "select d.*,p.storeid,p.goodsid,p.productid,p.store from " + getTableName("depot") + " d left join " + getTableName("product_store") + " p on d.id=p.depotid where p.productid=?";


     List<Map> storeList = this.daoSupport.queryForList(sql, new Object[] { productid });
     for (Depot depot : depotList) {
       Map depotStore = new HashMap();
       depotStore.put("storeid", Integer.valueOf(0));
       depotStore.put("store", Integer.valueOf(0));
       depotStore.put("goodsid", Integer.valueOf(0));
       depotStore.put("productid", Integer.valueOf(0));
       if ((storeList != null) && (!storeList.isEmpty())) {
         for (Map store : storeList) {
           int depotid = Integer.parseInt(store.get("id").toString());
           if (depotid == depot.getId().intValue()) {
             depotStore.put("storeid", store.get("storeid"));
             depotStore.put("store", store.get("store"));
             depotStore.put("goodsid", store.get("goodsid"));
             depotStore.put("productid", store.get("productid"));
           }
         }
       }
       depotStore.put("name", depot.getName());
       depotStore.put("depotid", depot.getId());

       depotStoreList.add(depotStore);
     }
     return depotStoreList;
   }


   public Integer getbStoreByProId(Integer productid, Integer depotId)
   {
     try
     {
       return Integer.valueOf(this.daoSupport.queryForInt("select store from es_product_store where productid=? and depotid=?", new Object[] { productid, depotId }));
     } catch (Exception e) {}
     return Integer.valueOf(0);
   }




   public List<Map> ListProductDepotStore(Integer productid, Integer finddepotid)
   {
     List<Depot> depotList = this.depotManager.list();
     List<Map> depotStoreList = new ArrayList();

     String sql = "select d.*,p.storeid,p.goodsid,p.productid,p.store from " + getTableName("depot") + " d left join " + getTableName("product_store") + " p on d.id=p.depotid where p.productid=?";


     List<Map> storeList = this.daoSupport.queryForList(sql, new Object[] { productid });

     for (Depot depot : depotList)
       if (finddepotid.intValue() == depot.getId().intValue())
       {

         Map depotStore = new HashMap();
         depotStore.put("storeid", Integer.valueOf(0));
         depotStore.put("store", Integer.valueOf(0));
         depotStore.put("goodsid", Integer.valueOf(0));
         depotStore.put("productid", Integer.valueOf(0));

         if ((storeList != null) && (!storeList.isEmpty())) {
           for (Map store : storeList) {
             int depotid = ((Integer)store.get("id")).intValue();
             if (depotid == depot.getId().intValue()) {
               depotStore.put("storeid", store.get("storeid"));
               depotStore.put("store", store.get("store"));
               depotStore.put("goodsid", store.get("goodsid"));
               depotStore.put("productid", store.get("productid"));
             }
           }
         }
         depotStore.put("name", depot.getName());
         depotStore.put("depotid", depot.getId());

         depotStoreList.add(depotStore);
       }
     return depotStoreList;
   }

   public List<Map> listProductAllo(Integer orderid, Integer itemid) {
     String sql = "select d.name,a.num from " + getTableName("depot") + " d, " + getTableName("allocation_item") + " a where a.orderid=? and d.id=a.depotid and a.itemid=?";



     return this.daoSupport.queryForList(sql, new Object[] { orderid, itemid });
   }




   public String getStoreHtml(Integer goodsid)
   {
     Map goods = getGoods(goodsid.intValue());
     return this.goodsStorePluginBundle.getStoreHtml(goods);
   }






   public String getStockHtml(Integer goodsid)
   {
     Map goods = getGoods(goodsid.intValue());
     return this.goodsStorePluginBundle.getStockHtml(goods);
   }






   public String getWarnHtml(Integer goodsid)
   {
     Map goods = getGoods(goodsid.intValue());
     return this.goodsStorePluginBundle.getWarnHtml(goods);
   }






   public String getShipHtml(Integer goodsid)
   {
     Map goods = getGoods(goodsid.intValue());
     return this.goodsStorePluginBundle.getShipHtml(goods);
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void saveStore(int goodsid)
   {
     Map goods = getGoods(goodsid);
     this.goodsStorePluginBundle.onStoreSave(goods);
   }




   @Transactional(propagation=Propagation.REQUIRED)
   public void saveStock(int goodsid)
   {
     Map goods = getGoods(goodsid);
     this.goodsStorePluginBundle.onStockSave(goods);
   }




   @Transactional(propagation=Propagation.REQUIRED)
   public void saveWarn(int goodsid)
   {
     Map goods = getGoods(goodsid);
     this.goodsStorePluginBundle.onWarnSave(goods);
   }




   @Transactional(propagation=Propagation.REQUIRED)
   public void saveShip(int goodsid)
   {
     Map goods = getGoods(goodsid);
     this.goodsStorePluginBundle.onShipSave(goods);
   }

   public void saveCmpl(int goodsid)
   {
     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));

     if ((!isSuperAdmin) && (!isDepotAdmin)) {
       throw new RuntimeException("没有维护库存的权限");
     }


     if (isSuperAdmin) {
       this.baseDaoSupport.execute("update goods_depot set iscmpl=1  where goodsid=?", new Object[] { Integer.valueOf(goodsid) });
     }
     else {
       DepotUser user = (DepotUser)this.adminUserManager.getCurrentUser();
       this.baseDaoSupport.execute("update goods_depot set iscmpl=1  where goodsid=? and depotid=? ", new Object[] { Integer.valueOf(goodsid), user.getDepotid() });
     }
   }

   private Map getGoods(int goodsid) {
     String sql = "select * from goods  where goods_id=?";
     Map goods = this.baseDaoSupport.queryForMap(sql, new Object[] { Integer.valueOf(goodsid) });
     return goods;
   }

   public IDepotManager getDepotManager() {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) {
     this.depotManager = depotManager;
   }

   public GoodsStorePluginBundle getGoodsStorePluginBundle() {
     return this.goodsStorePluginBundle;
   }

   public void setGoodsStorePluginBundle(GoodsStorePluginBundle goodsStorePluginBundle) {
     this.goodsStorePluginBundle = goodsStorePluginBundle;
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }



   public List<WarnNum> listWarns(Integer goods_id)
   {
     String sql = "select * from warn_num where  goods_id=?";
     List<WarnNum> list = this.baseDaoSupport.queryForList(sql, WarnNum.class, new Object[] { goods_id });
     List<WarnNum> warnList = new ArrayList();
     if ((list != null) && (!list.isEmpty())) {
       for (WarnNum warnNum : list) {
         warnList.add(warnNum);
       }
     } else {
       WarnNum warnNum = new WarnNum();
       warnNum.setId(Integer.valueOf(0));
       warnNum.setGoods_id(goods_id);
       warnNum.setWarn_num(Integer.valueOf(0));
       warnList.add(warnNum);
     }
     return warnList;
   }

   public List<Map> getDegreeDepotStore(int goodsid, int depotid)
   {
     String sql = "select p.* from  product_store p where p.goodsid=? and p.depotid=?";
     return this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(goodsid), Integer.valueOf(depotid) });
   }

   public IPermissionManager getPermissionManager() {
     return this.permissionManager;
   }

   public void setPermissionManager(IPermissionManager permissionManager) {
     this.permissionManager = permissionManager;
   }



   public Page listGoodsStore(Map map, int page, int pageSize, String other, String sort, String order)
   {
     Integer stype = (Integer)map.get("stype");
     String keyword = (String)map.get("keyword");
     String name = (String)map.get("name");
     String sn = (String)map.get("sn");
     Integer order_store = (Integer)map.get("store");

     StringBuffer sql = new StringBuffer();
     sql.append("select g.goods_id,g.sn,g.name,g.store,c.name cname,g.enable_store from es_goods g,es_goods_cat c where g.cat_id=c.cat_id ");

     if ((stype != null) && (keyword != null) &&
       (stype.intValue() == 0)) {
       sql.append(" and ( g.name like '%" + keyword.trim() + "%'");
       sql.append(" or g.sn like '%" + keyword.trim() + "%')");
     }


     if (!StringUtil.isEmpty(name)) {
       sql.append(" and g.name like '%" + name + "%'");
     }

     if (!StringUtil.isEmpty(sn)) {
       sql.append(" and g.sn like '%" + sn + "%'");
     }

     if ((order_store != null) && (order_store.intValue() != 0)) {
       sql.append(" and g.store=" + order_store);
     }
     sql.append("order by " + sort + " " + order);
     Page webPage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[0]);

     List<Map> goodslist = (List)webPage.getResult();

     StringBuffer goodsidstr = new StringBuffer();
     for (Map goods : goodslist) {
       Integer goodsid = (Integer)goods.get("goods_id");
       if (goodsidstr.length() != 0) {
         goodsidstr.append(",");
       }
       goodsidstr.append(goodsid); }
     int depotid;
     List<Map> storeList;
     if (goodsidstr.length() != 0)
     {
       String ps_sql = "select ps.* from  es_product_store ps where  ps.goodsid in(" + goodsidstr + ") ";
       depotid = ((Integer)map.get("depotid")).intValue();
       if (depotid != 0) {
         ps_sql = ps_sql + " and depotid=" + depotid;
       }
       ps_sql = ps_sql + " order by goodsid,depotid ";

       storeList = this.daoSupport.queryForList(ps_sql, new Object[0]);

       for (Map goods : goodslist) {
         Integer goodsid = (Integer)goods.get("goods_id");
         if (depotid != 0) {
           goods.put("d_store", Integer.valueOf(0));
           goods.put("enable_store", Integer.valueOf(0));
           for (Map store : storeList) {
             Integer store_goodsid = (Integer)store.get("goodsid");
             if (store_goodsid.compareTo(goodsid) == 0) {
               goods.put("d_store", store.get("store"));
               goods.put("enable_store", store.get("enable_store"));
             }

           }

         }
         else
         {
           goods.put("d_store", goods.get("store"));
           goods.put("enable_store", goods.get("enable_store"));
         }
       }
     }



     return webPage;
   }



   @Transactional(propagation=Propagation.REQUIRED)
   public void increaseStroe(int goodsid, int productid, int depotid, int num)
   {
     if (checkExists(goodsid, depotid)) {
       this.daoSupport.execute("update es_product_store set store =store+?,enable_store=enable_store+? where goodsid=? and depotid=?", new Object[] { Integer.valueOf(num), Integer.valueOf(num), Integer.valueOf(goodsid), Integer.valueOf(depotid) });
     } else {
       this.daoSupport.execute("insert into es_product_store(goodsid,productid,depotid,store,enable_store)values(?,?,?,?,?)", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(productid), Integer.valueOf(depotid), Integer.valueOf(num), Integer.valueOf(num) });
     }

     this.daoSupport.execute("update es_product set  store=store+?,enable_store=enable_store+?  where product_id=?", new Object[] { Integer.valueOf(num), Integer.valueOf(num), Integer.valueOf(productid) });
     this.daoSupport.execute("update es_goods set store=store+?,enable_store=enable_store+?  where goods_id=?", new Object[] { Integer.valueOf(num), Integer.valueOf(num), Integer.valueOf(goodsid) });
     this.daoSupport.execute("update es_goods set market_enable=?, where goods_id=?", new Object[] { Integer.valueOf(1), Integer.valueOf(goodsid) });
   }






   private boolean checkExists(int goodsid, int depotid)
   {
     int count = this.daoSupport.queryForInt("select count(0) from es_product_store where goodsid=? and depotid=?", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(depotid) });
     return count != 0;
   }

   public List getStoreList() {
     String sql = "select * from es_depot";
     List list = this.baseDaoSupport.queryForList(sql, new Object[0]);
     Depot depot = new Depot();
     depot.setName("总库存");
     list.add(0, depot);
     return list;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\GoodsStoreManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */