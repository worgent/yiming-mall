 package com.enation.app.shop.core.service.impl;

 import com.enation.app.shop.core.service.IProductStoreManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.IntegerMapper;
 import java.util.List;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;






 public class ProductStoreManager
   implements IProductStoreManager
 {
   private IDaoSupport daoSupport;

   @Transactional(propagation=Propagation.REQUIRED)
   public void decreaseEnable(int goodsid, int productid, int depotid, int num)
   {
     this.daoSupport.execute("update es_product_store set enable_store=enable_store-? where depotid=? and productid=?", new Object[] { Integer.valueOf(num), Integer.valueOf(depotid), Integer.valueOf(productid) });
     this.daoSupport.execute("update es_product set enable_store=enable_store-? where product_id=?", new Object[] { Integer.valueOf(num), Integer.valueOf(productid) });
     this.daoSupport.execute("update es_goods set enable_store=enable_store-? where goods_id=?", new Object[] { Integer.valueOf(num), Integer.valueOf(goodsid) });

     int enable_store = this.daoSupport.queryForInt("select enable_store from es_goods where goods_id=?", new Object[] { Integer.valueOf(goodsid) });
     if (enable_store == 0) {
       this.daoSupport.execute("update es_goods set market_enable=? where goods_id=?", new Object[] { Integer.valueOf(0), Integer.valueOf(goodsid) });
     }
   }


   @Transactional(propagation=Propagation.REQUIRED)
   public void increaseEnable(int goodsid, int productid, int depotid, int num)
   {
     if (checkExists(goodsid, depotid)) {
       this.daoSupport.execute("update es_product_store set enable_store =enable_store+? where goodsid=? and depotid=?", new Object[] { Integer.valueOf(num), Integer.valueOf(goodsid), Integer.valueOf(depotid) });
     } else {
       this.daoSupport.execute("insert into es_product_store(goodsid,productid,depotid,enable_store)values(?,?,?,?)", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(productid), Integer.valueOf(depotid), Integer.valueOf(num) });
     }


     this.daoSupport.execute("update es_product set enable_store=enable_store+? where product_id=?", new Object[] { Integer.valueOf(num), Integer.valueOf(productid) });
     this.daoSupport.execute("update es_goods set enable_store=enable_store+? where goods_id=?", new Object[] { Integer.valueOf(num), Integer.valueOf(goodsid) });

     this.daoSupport.execute("update es_goods set market_enable=? where goods_id=?", new Object[] { Integer.valueOf(1), Integer.valueOf(goodsid) });
   }


   @Transactional(propagation=Propagation.REQUIRED)
   public void decreaseStroe(int goodsid, int productid, int depotid, int num)
   {
     this.daoSupport.execute("update es_product_store set store=store-? where depotid=? and productid=?", new Object[] { Integer.valueOf(num), Integer.valueOf(depotid), Integer.valueOf(productid) });
     this.daoSupport.execute("update es_product set store=store-? where product_id=?", new Object[] { Integer.valueOf(num), Integer.valueOf(productid) });
     this.daoSupport.execute("update es_goods set store=store-? where goods_id=?", new Object[] { Integer.valueOf(num), Integer.valueOf(goodsid) });
   }




   @Transactional(propagation=Propagation.REQUIRED)
   public void increaseStroe(int goodsid, int productid, int depotid, int num)
   {
     if (checkExists(goodsid, depotid)) {
       this.daoSupport.execute("update es_product_store set enable_store=enable_store+?,store =store+? where goodsid=? and depotid=?", new Object[] { Integer.valueOf(num), Integer.valueOf(num), Integer.valueOf(goodsid), Integer.valueOf(depotid) });
     } else {
       this.daoSupport.execute("insert into es_product_store(goodsid,productid,depotid,store,enable_store)values(?,?,?,?,?)", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(productid), Integer.valueOf(depotid), Integer.valueOf(num), Integer.valueOf(num) });
     }


     this.daoSupport.execute("update es_product set enable_store=enable_store+?, store=store+?  where product_id=?", new Object[] { Integer.valueOf(num), Integer.valueOf(num), Integer.valueOf(productid) });
     this.daoSupport.execute("update es_goods set enable_store=enable_store+?,store=store+?  where goods_id=?", new Object[] { Integer.valueOf(num), Integer.valueOf(num), Integer.valueOf(goodsid) });
     this.daoSupport.execute("update es_goods set market_enable=? where goods_id=?", new Object[] { Integer.valueOf(1), Integer.valueOf(goodsid) });
   }








   private boolean checkExists(int goodsid, int depotid)
   {
     int count = this.daoSupport.queryForInt("select count(0) from es_product_store where goodsid=? and depotid=?", new Object[] { Integer.valueOf(goodsid), Integer.valueOf(depotid) });
     return count != 0;
   }





   public int getEnableStroe(int goodsid, int depotid)
   {
     String sql = "select enable_store from es_product_store where goodsid=? and depotid=?";
     List<Integer> storeList = this.daoSupport.queryForList(sql, new IntegerMapper(), new Object[] { Integer.valueOf(goodsid), Integer.valueOf(depotid) });
     int store = 0;
     if (!storeList.isEmpty()) {
       store = ((Integer)storeList.get(0)).intValue();
     }
     return store;
   }

   public IDaoSupport getDaoSupport()
   {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\ProductStoreManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */