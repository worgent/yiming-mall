 package com.enation.app.shop.component.spec.service;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.auth.IPermissionManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.app.shop.core.model.Depot;
 import com.enation.app.shop.core.model.DepotUser;
 import com.enation.app.shop.core.model.Product;
 import com.enation.app.shop.core.service.IDepotManager;
 import com.enation.app.shop.core.service.IProductManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;











 @Component
 public class GoodsSpecStoreManager
   extends BaseSupport
   implements IGoodsSpecStoreManager
 {
   private IDepotManager depotManager;
   private IProductManager productManager;
   private IPermissionManager permissionManager;
   private IAdminUserManager adminUserManager;

   public List<Map> listGoodsStore(int goodsid)
   {
     boolean isSuperAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("super_admin"));
     boolean isDepotAdmin = this.permissionManager.checkHaveAuth(PermissionConfig.getAuthId("depot_admin"));

     if ((!isSuperAdmin) && (!isDepotAdmin)) {
       throw new RuntimeException("没有维护库存的权限");
     }





     List<Product> prolist = this.productManager.list(Integer.valueOf(goodsid));


     String sql = "select * from product_store where goodsid=?";
     List<Map> storeList = this.baseDaoSupport.queryForList(sql, new Object[] { Integer.valueOf(goodsid) });


     List<Depot> depotList = this.depotManager.list();

     List list = new ArrayList();



     AdminUser adminUser = this.adminUserManager.getCurrentUser();
     Integer depotid = null;
     if (!isSuperAdmin) {
       DepotUser depotUser = (DepotUser)adminUser;
       depotid = depotUser.getDepotid();
     }

     for (Depot depot : depotList) {
       if ((isSuperAdmin) || (
         (depotid != null) && (depot.getId().intValue() == depotid.intValue())))
       {
         Map depotMap = new HashMap();
         depotMap.put("depotid", depot.getId());
         depotMap.put("depotname", depot.getName());

         List<Map> pList = getProductList(depot.getId().intValue(), prolist, storeList);
         depotMap.put("productList", pList);
         list.add(depotMap);
       }
     }

     return list;
   }



   private List<Map> getProductList(int depotid, List<Product> productList, List<Map> storeList)
   {
     List<Map> pList = new ArrayList();

     for (Product product : productList) {
       Map pro = new HashMap();

       pro.put("specList", product.getSpecList());
       pro.put("sn", product.getSn());
       pro.put("name", product.getName());
       pro.put("product_id", product.getProduct_id());
       pro.put("storeid", Integer.valueOf(0));
       pro.put("store", Integer.valueOf(0));
       for (Map store : storeList)
       {

         if ((depotid == ((Integer)store.get("depotid")).intValue()) && (product.getProduct_id().intValue() == ((Integer)store.get("productid")).intValue()))
         {



           pro.put("storeid", (Integer)store.get("storeid"));
           pro.put("store", (Integer)store.get("store"));
         }
       }



       pList.add(pro);
     }

     return pList;
   }








   public int stock(int goodsid, String[] storeidAr, String[] productidAr, String[] depotidAr, String[] storeAr)
   {
     if (storeidAr == null) {
       return 0;
     }

     int total = 0;
     for (int i = 0; i < storeidAr.length; i++) {
       int storeid = StringUtil.toInt(storeidAr[i], true);
       int store = StringUtil.toInt(storeAr[i], true);
       int depotid = StringUtil.toInt(depotidAr[i], true);
       int productid = StringUtil.toInt(productidAr[i], true);

       if (storeid == 0) {
         this.baseDaoSupport.execute("insert into product_store(goodsid,productid,depotid,store)values(?,?,?,?)", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(productid), Integer.valueOf(depotid), Integer.valueOf(store) });
       } else {
         this.baseDaoSupport.execute("update product_store set  store=store+? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });
       }


       this.daoSupport.execute("update " + getTableName("product") + " set store=(select sum(store) from " + getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { Integer.valueOf(productid), Integer.valueOf(productid) });

       total += store;
     }


     this.daoSupport.execute("update " + getTableName("goods") + " set store=(select sum(store) from " + getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(goodsid) });

     return total;
   }





   public int saveStore(int goodsid, String[] storeidAr, String[] productidAr, String[] depotidAr, String[] storeAr)
   {
     int total = 0;
     for (int i = 0; i < storeidAr.length; i++) {
       int storeid = StringUtil.toInt(storeidAr[i], true);
       int store = StringUtil.toInt(storeAr[i], true);
       int depotid = StringUtil.toInt(depotidAr[i], true);
       int productid = StringUtil.toInt(productidAr[i], true);

       if (storeid == 0) {
         this.baseDaoSupport.execute("insert into product_store(goodsid,productid,depotid,store)values(?,?,?,?)", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(productid), Integer.valueOf(depotid), Integer.valueOf(store) });
       } else {
         this.baseDaoSupport.execute("update product_store set  store=? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });
       }


       this.daoSupport.execute("update " + getTableName("product") + " set store=(select sum(store) from " + getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { Integer.valueOf(productid), Integer.valueOf(productid) });

       total += store;
     }


     this.daoSupport.execute("update " + getTableName("goods") + " set store=(select sum(store) from " + getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(goodsid) });

     return total;
   }






   public int ship(int goodsid, String[] storeidAr, String[] productidAr, String[] depotidAr, String[] storeAr)
   {
     int total = 0;
     for (int i = 0; i < storeidAr.length; i++) {
       int storeid = StringUtil.toInt(storeidAr[i], true);
       int store = StringUtil.toInt(storeAr[i], true);
       int depotid = StringUtil.toInt(depotidAr[i], true);
       int productid = StringUtil.toInt(productidAr[i], true);

       if (storeid == 0) {
         this.baseDaoSupport.execute("insert into product_store(goodsid,productid,depotid,store)values(?,?,?,?)", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(productid), Integer.valueOf(depotid), Integer.valueOf(store) });
       } else {
         this.baseDaoSupport.execute("update product_store set  store=store-? where storeid=?", new Object[] { Integer.valueOf(store), Integer.valueOf(storeid) });
       }


       this.daoSupport.execute("update " + getTableName("product") + " set store=(select sum(store) from " + getTableName("product_store") + " where productid=?) where product_id=? ", new Object[] { Integer.valueOf(productid), Integer.valueOf(productid) });

       total += store;
     }


     this.daoSupport.execute("update " + getTableName("goods") + " set store=(select sum(store) from " + getTableName("product_store") + " where goodsid=?) where goods_id=? ", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(goodsid) });

     return total;
   }


   public IDepotManager getDepotManager()
   {
     return this.depotManager;
   }

   public void setDepotManager(IDepotManager depotManager) {
     this.depotManager = depotManager;
   }

   public IProductManager getProductManager()
   {
     return this.productManager;
   }

   public void setProductManager(IProductManager productManager)
   {
     this.productManager = productManager;
   }

   public IPermissionManager getPermissionManager()
   {
     return this.permissionManager;
   }

   public void setPermissionManager(IPermissionManager permissionManager)
   {
     this.permissionManager = permissionManager;
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


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\service\GoodsSpecStoreManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */