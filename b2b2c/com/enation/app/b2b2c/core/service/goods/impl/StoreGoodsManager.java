 package com.enation.app.b2b2c.core.service.goods.impl;

 import com.enation.app.b2b2c.core.model.goods.StoreGoods;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.model.Product;
 import com.enation.app.shop.core.plugin.goods.GoodsPluginBundle;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.app.shop.core.service.IProductManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import java.io.PrintStream;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import org.apache.commons.beanutils.BeanUtils;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;

 @Component
 public class StoreGoodsManager extends BaseSupport implements IStoreGoodsManager
 {
   private IStoreMemberManager storeMemberManager;
   private GoodsPluginBundle goodsPluginBundle;
   private IGoodsCatManager goodsCatManager;
   private IProductManager productManager;

   public Page storeGoodsList(Integer pageNo, Integer pageSize, Map map)
   {
     Integer store_id = Integer.valueOf(map.get("store_id").toString());
     Integer disable = Integer.valueOf(map.get("disable") + "");
     String store_cat = String.valueOf(map.get("store_cat"));
     String goodsName = String.valueOf(map.get("goodsName"));
     String market_enable = String.valueOf(map.get("market_enable"));

     StringBuffer sql = new StringBuffer("SELECT g.*,c.store_cat_name from es_goods g LEFT JOIN es_store_cat c ON g.store_cat_id=c.store_cat_id where g.store_id=" + store_id + " and  g.disabled=" + disable);

     if ((!StringUtil.isEmpty(store_cat)) && (!StringUtil.equals(store_cat, "null")) && (!StringUtil.equals(store_cat, "0"))) {
       sql.append(" and g.store_cat_id=" + store_cat);
     }
     if ((!StringUtil.isEmpty(goodsName)) && (!StringUtil.equals(goodsName, "null"))) {
       sql.append(" and ((g.name like '%" + goodsName + "%') or (g.sn like '%" + goodsName + "%') )");
     }
     if ((!StringUtil.isEmpty(market_enable)) && (!StringUtil.equals(market_enable, "null")) &&
       (!market_enable.equals("-1"))) {
       sql.append(" and g.market_enable=" + market_enable);
     }

     return this.daoSupport.queryForPage(sql.toString(), pageNo.intValue(), pageSize.intValue(), new Object[0]);
   }






   public List<Map> storeGoodsList(int storeid, Map map)
   {
     StringBuffer sql = new StringBuffer("SELECT g.* from es_goods g where g.store_id=" + storeid + " and  g.disabled=0");
     String store_catid = String.valueOf(map.get("store_catid"));
     String keyword = String.valueOf(map.get("keyword"));
     if ((!StringUtil.isEmpty(store_catid)) && (!"0".equals(store_catid))) {
       sql.append(" and g.store_cat_id=" + store_catid);
     }

     if (!StringUtil.isEmpty(keyword)) {
       sql.append(" and ((g.name like '%" + keyword + "%') or (g.sn like '%" + keyword + "%') )");
     }

     return this.daoSupport.queryForList(sql.toString(), new Object[0]);
   }

   protected Map po2Map(Object po)
   {
     Map poMap = new HashMap();
     Map map = new HashMap();
     try {
       map = BeanUtils.describe(po);
     }
     catch (Exception ex) {}
     Object[] keyArray = map.keySet().toArray();
     for (int i = 0; i < keyArray.length; i++) {
       String str = keyArray[i].toString();
       if ((str != null) && (!str.equals("class")) &&
         (map.get(str) != null)) {
         poMap.put(str, map.get(str));
       }
     }

     return poMap;
   }




   public Page b2b2cGoodsList(Integer pageNo, Integer pageSize, Map map)
   {
     String keyword = (String)(map.get("namekeyword") == null ? "" : map.get("namekeyword"));
     String cat_id = (String)(map.get("cat_id") == null ? "" : map.get("cat_id"));
     String search_type = (String)(map.get("search_type") == null ? "" : map.get("search_type"));
     StringBuffer sql = new StringBuffer("select g.*,s.store_name as store_name,s.qq as qq from es_goods g inner join es_store s on s.store_id=g.store_id INNER JOIN es_brand b ON b.brand_id=g.brand_id  where g.disabled=0 and g.market_enable=1");

     if (!StringUtil.isEmpty(keyword)) {
       sql.append("  and ((g.name like '%" + keyword + "%') or ( g.sn like '%" + keyword + "%') or(b.name like '%" + keyword + "%'))");
     }
     if ((!StringUtil.isEmpty(cat_id)) && (cat_id != "0")) {
       Cat cat = this.goodsCatManager.getById(Integer.parseInt(cat_id));
       sql.append(" and  g.cat_id in(select c.cat_id from es_goods_cat c where c.cat_path like '" + cat.getCat_path() + "%')");
     }
     if (!StringUtil.isEmpty(search_type)) {
       if (search_type.equals("1")) {
         sql.append(" order by buy_num desc");
       } else if (search_type.equals("2")) {
         sql.append(" order by price desc");
       } else {
         sql.append(" order by goods_id desc");
       }
     }
     System.out.println(sql.toString());
     return this.daoSupport.queryForPage(sql.toString(), pageNo.intValue(), pageSize.intValue(), new Object[0]);
   }




   public Page store_searchGoodsList(Integer page, Integer pageSize, Map params)
   {
     Integer storeid = (Integer)params.get("storeid");
     String keyword = (String)params.get("keyword");
     String start_price = (String)params.get("start_price");
     String end_price = (String)params.get("end_price");
     Integer key = (Integer)params.get("key");
     String order = (String)params.get("order");
     Integer cat_id = (Integer)params.get("stc_id");

     StringBuffer sql = new StringBuffer("select * from es_goods g where disabled=0 and market_enable=1 and store_id=" + storeid);

     if (!StringUtil.isEmpty(keyword)) {
       sql.append("  and g.name like '%" + keyword + "%' ");
     }

     if (!StringUtil.isEmpty(start_price)) {
       sql.append(" and price>=" + Double.valueOf(start_price));
     }
     if (!StringUtil.isEmpty(end_price)) {
       sql.append(" and price<=" + Double.valueOf(end_price));
     }

     if ((cat_id != null) && (cat_id.intValue() != 0)) {
       sql.append(" and  g.store_cat_id=" + cat_id);
     }

     if (key != null) {
       if (key.intValue() == 1) {
         sql.append(" order by buy_num " + order);
       } else if (key.intValue() == 2) {
         sql.append(" order by price " + order);
       } else if (key.intValue() == 3) {
         sql.append(" order by goods_id " + order);
       } else if (key.intValue() == 4) {
         sql.append(" order by goods_id " + order);
       } else if (key.intValue() == 5) {
         sql.append(" order by goods_id " + order);
       }
     }
     Page webpage = this.baseDaoSupport.queryForPage(sql.toString(), page.intValue(), pageSize.intValue(), new Object[0]);
     return webpage;
   }





   @Transactional(propagation=Propagation.REQUIRED)
   public void saveGoodsStore(Integer storeid, Integer goods_id, Integer storeNum)
   {
     Product product = this.productManager.getByGoodsId(goods_id);
     Integer productid = product.getProduct_id();
     if (storeid.intValue() == 0) {
       this.daoSupport.execute("insert into es_product_store(goodsid,productid,depotid,store,enable_store)values(?,?,?,?,?)", new Object[] { goods_id, productid, Integer.valueOf(1), storeNum, storeNum });
     }
     else {
       this.daoSupport.execute("update es_product_store set store=?,enable_store=? where goodsid=?", new Object[] { storeNum, storeNum, goods_id });
     }

     this.daoSupport.execute("update es_goods set store=?,enable_store=? where goods_id=?", new Object[] { storeNum, storeNum, goods_id });
     this.daoSupport.execute("update es_product set store=?,enable_store=? where product_id=?", new Object[] { storeNum, storeNum, productid });
   }






   public List transactionList(Integer pageNo, Integer pageSize, Integer goods_id)
   {
     String sql = "select * from  es_transaction_record where goods_id=? order by record_id";
     return this.daoSupport.queryForListPage(sql, pageNo.intValue(), pageSize.intValue(), new Object[] { goods_id });
   }




   public int transactionCount(Integer goods_id)
   {
     String sql = "select count(0) from  es_transaction_record where goods_id=? ";
     return this.daoSupport.queryForInt(sql, new Object[] { goods_id });
   }




   public StoreGoods getGoods(Integer goods_id)
   {
     String sql = "select * from es_goods where goods_id=?";
     StoreGoods goods = (StoreGoods)this.baseDaoSupport.queryForObject(sql, StoreGoods.class, new Object[] { goods_id });
     return goods;
   }




   public int getStoreGoodsNum(int struts)
   {
     StoreMember member = this.storeMemberManager.getStoreMember();
     StringBuffer sql = new StringBuffer("SELECT count(goods_id) from es_goods where store_id=? and  disabled=0 and market_enable=?");
     return this.daoSupport.queryForInt(sql.toString(), new Object[] { member.getStore_id(), Integer.valueOf(struts) });
   }

   public IProductManager getProductManager() { return this.productManager; }

   public void setProductManager(IProductManager productManager)
   {
     this.productManager = productManager;
   }

   public Map getGoodsStore(Integer goods_id)
   {
     List<Map> list = this.daoSupport.queryForList("select * from es_product_store where goodsid=?", new Object[] { goods_id });
     if (list.size() > 0) {
       return (Map)list.get(0);
     }
     return null;
   }

   public GoodsPluginBundle getGoodsPluginBundle() {
     return this.goodsPluginBundle;
   }

   public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) {
     this.goodsPluginBundle = goodsPluginBundle;
   }

   public IGoodsCatManager getGoodsCatManager() {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
   }

   public IStoreMemberManager getStoreMemberManager()
   {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\goods\impl\StoreGoodsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */