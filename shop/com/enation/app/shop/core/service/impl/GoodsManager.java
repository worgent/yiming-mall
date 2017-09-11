 package com.enation.app.shop.core.service.impl;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.model.Goods;
 import com.enation.app.shop.core.model.GoodsStores;
 import com.enation.app.shop.core.model.support.GoodsEditDTO;
 import com.enation.app.shop.core.plugin.goods.GoodsDataFilterBundle;
 import com.enation.app.shop.core.plugin.goods.GoodsPluginBundle;
 import com.enation.app.shop.core.service.IDepotMonitorManager;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.app.shop.core.service.IMemberPriceManager;
 import com.enation.app.shop.core.service.IPackageProductManager;
 import com.enation.app.shop.core.service.ITagManager;
 import com.enation.app.shop.core.service.SnDuplicateException;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.beanutils.BeanUtils;
 import org.apache.log4j.Logger;
 import org.springframework.jdbc.core.RowMapper;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;






 public class GoodsManager
   extends BaseSupport
   implements IGoodsManager
 {
   private ITagManager tagManager;
   private GoodsPluginBundle goodsPluginBundle;
   private IPackageProductManager packageProductManager;
   private IGoodsCatManager goodsCatManager;
   private IMemberPriceManager memberPriceManager;
   private IMemberLvManager memberLvManager;
   private IDepotMonitorManager depotMonitorManager;
   private GoodsDataFilterBundle goodsDataFilterBundle;

   @Transactional(propagation=Propagation.REQUIRED)
   public void add(Goods goods)
   {
     try
     {
       Map goodsMap = po2Map(goods);


       this.goodsPluginBundle.onBeforeAdd(goodsMap);
       goodsMap.put("disabled", Integer.valueOf(0));
       goodsMap.put("create_time", Long.valueOf(DateUtil.getDatelineLong()));
       goodsMap.put("view_count", Integer.valueOf(0));
       goodsMap.put("buy_count", Integer.valueOf(0));
       goodsMap.put("last_modify", Long.valueOf(DateUtil.getDatelineLong()));
       this.baseDaoSupport.insert("goods", goodsMap);
       Integer goods_id = Integer.valueOf(this.baseDaoSupport.getLastId("goods"));
       goods.setGoods_id(goods_id);
       goodsMap.put("goods_id", goods_id);
       this.goodsPluginBundle.onAfterAdd(goodsMap);
     } catch (RuntimeException e) {
       if ((e instanceof SnDuplicateException)) {
         throw e;
       }
       e.printStackTrace();
     }
   }



   @Transactional(propagation=Propagation.REQUIRED)
   public void edit(Goods goods)
   {
     try
     {
       if (this.logger.isDebugEnabled()) {
         this.logger.debug("开始保存商品数据...");
       }
       Map goodsMap = po2Map(goods);
       this.goodsPluginBundle.onBeforeEdit(goodsMap);
       this.baseDaoSupport.update("goods", goodsMap, "goods_id=" + goods.getGoods_id());

       this.goodsPluginBundle.onAfterEdit(goodsMap);
       if (this.logger.isDebugEnabled()) {
         this.logger.debug("保存商品数据完成.");
       }
     } catch (RuntimeException e) {
       if ((e instanceof SnDuplicateException)) {
         throw e;
       }
       e.printStackTrace();
     }
   }







   public GoodsEditDTO getGoodsEditData(Integer goods_id)
   {
     GoodsEditDTO editDTO = new GoodsEditDTO();
     String sql = "select * from goods where goods_id=?";
     Map goods = this.baseDaoSupport.queryForMap(sql, new Object[] { goods_id });

     String intro = (String)goods.get("intro");
     if (intro != null) {
       intro = UploadUtil.replacePath(intro);
       goods.put("intro", intro);
     }

     Map<Integer, String> htmlMap = this.goodsPluginBundle.onFillEditInputData(goods);

     editDTO.setGoods(goods);
     editDTO.setHtmlMap(htmlMap);

     return editDTO;
   }







   public Map get(Integer goods_id)
   {
     String sql = "select g.*,b.name as brand_name from " + getTableName("goods") + " g left join " + getTableName("brand") + " b on g.brand_id=b.brand_id ";


     sql = sql + "  where goods_id=?";

     Map goods = this.daoSupport.queryForMap(sql, new Object[] { goods_id });





     String small = (String)goods.get("small");
     if (small != null) {
       small = UploadUtil.replacePath(small);
       goods.put("small", small);
     }
     String big = (String)goods.get("big");
     if (big != null) {
       big = UploadUtil.replacePath(big);
       goods.put("big", big);
     }


     return goods;
   }

   public void getNavdata(Map goods)
   {
     int catid = ((Integer)goods.get("cat_id")).intValue();
     List list = this.goodsCatManager.getNavpath(catid);
     goods.put("navdata", list);
   }

   private String getListSql(int disabled)
   {
     String selectSql = this.goodsPluginBundle.onGetSelector();
     String fromSql = this.goodsPluginBundle.onGetFrom();

     String sql = "select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name " + selectSql + " from " + getTableName("goods") + " g left join " + getTableName("goods_cat") + " c on g.cat_id=c.cat_id left join " + getTableName("brand") + " b on g.brand_id = b.brand_id and b.disabled=0 left join " + getTableName("goods_type") + " t on g.type_id =t.type_id " + fromSql + " where g.goods_type = 'normal' and g.disabled=" + disabled;













     return sql;
   }






   private String getBindListSql(int disabled)
   {
     String sql = "select g.*,b.name as brand_name ,t.name as type_name,c.name as cat_name from " + getTableName("goods") + " g left join " + getTableName("goods_cat") + " c on g.cat_id=c.cat_id left join " + getTableName("brand") + " b on g.brand_id = b.brand_id left join " + getTableName("goods_type") + " t on g.type_id =t.type_id" + " where g.goods_type = 'bind' and g.disabled=" + disabled;









     return sql;
   }











   public Page searchBindGoods(String name, String sn, String order, int page, int pageSize)
   {
     String sql = getBindListSql(0);

     if (order == null) {
       order = "goods_id desc";
     }

     if ((name != null) && (!name.equals(""))) {
       sql = sql + "  and g.name like '%" + name + "%'";
     }

     if ((sn != null) && (!sn.equals(""))) {
       sql = sql + "   and g.sn = '" + sn + "'";
     }

     sql = sql + " order by g." + order;
     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);

     List<Map> list = (List)webpage.getResult();

     for (Map map : list) {
       List productList = this.packageProductManager.list(Integer.valueOf(map.get("goods_id").toString()).intValue());

       productList = productList == null ? new ArrayList() : productList;
       map.put("productList", productList);
     }

     return webpage;
   }












   public Page pageTrash(String name, String sn, String order, int page, int pageSize)
   {
     String sql = getListSql(1);
     if (order == null) {
       order = "goods_id desc";
     }

     if ((name != null) && (!name.equals(""))) {
       sql = sql + "  and g.name like '%" + name + "%'";
     }

     if ((sn != null) && (!sn.equals(""))) {
       sql = sql + "   and g.sn = '" + sn + "'";
     }

     sql = sql + " order by g." + order;

     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);

     return webpage;
   }












   public List<GoodsStores> storeWarnGoods(int warnTotal, int page, int pageSize)
   {
     String select_sql = "select gc.name as gc_name,b.name as b_name,g.cat_id,g.goods_id,g.name,g.sn,g.price,g.last_modify,g.market_enable,s.sumstore ";
     String left_sql = " left join " + getTableName("goods") + " g  on s.goodsid = g.goods_id  left join " + getTableName("goods_cat") + " gc on gc.cat_id = g.cat_id left join " + getTableName("brand") + " b on b.brand_id = g.brand_id ";
     List<GoodsStores> list = new ArrayList();

     String sql_2 = select_sql + " from  (select ss.* from (select goodsid,productid,sum(store) sumstore from " + getTableName("product_store") + "  group by goodsid,productid   ) ss " + "  left join " + getTableName("warn_num") + " wn on wn.goods_id = ss.goodsid  where ss.sumstore <=  (case when (wn.warn_num is not null or wn.warn_num <> 0) then wn.warn_num else ?  end )  ) s  " + left_sql;



     List<GoodsStores> list_2 = this.daoSupport.queryForList(sql_2, new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException
       {
         GoodsStores gs = new GoodsStores();
         gs.setGoods_id(Integer.valueOf(rs.getInt("goods_id")));
         gs.setName(rs.getString("name"));
         gs.setSn(rs.getString("sn"));
         gs.setRealstore(Integer.valueOf(rs.getInt("sumstore")));
         gs.setPrice(Double.valueOf(rs.getDouble("price")));
         gs.setLast_modify(Long.valueOf(rs.getLong("last_modify")));
         gs.setBrandname(rs.getString("b_name"));
         gs.setCatname(rs.getString("gc_name"));
         gs.setMarket_enable(Integer.valueOf(rs.getInt("market_enable")));
         gs.setCat_id(Integer.valueOf(rs.getInt("cat_id")));
         return gs; } }, new Object[] { Integer.valueOf(warnTotal) });


     list.addAll(list_2);

     return list;
   }





   @Transactional(propagation=Propagation.REQUIRED)
   public void delete(Integer[] ids)
   {
     if (ids == null) {
       return;
     }
     for (Integer id : ids) {
       this.tagManager.saveRels(id, null);
     }
     String id_str = StringUtil.arrayToString(ids, ",");
     String sql = "update  goods set disabled=1  where goods_id in (" + id_str + ")";


     this.baseDaoSupport.execute(sql, new Object[0]);
   }





   public void revert(Integer[] ids)
   {
     if (ids == null)
       return;
     String id_str = StringUtil.arrayToString(ids, ",");
     String sql = "update  goods set disabled=0  where goods_id in (" + id_str + ")";

     this.baseDaoSupport.execute(sql, new Object[0]);
   }





   @Transactional(propagation=Propagation.REQUIRED)
   public void clean(Integer[] ids)
   {
     if (ids == null)
       return;
     for (Integer id : ids) {
       this.tagManager.saveRels(id, null);
     }
     this.goodsPluginBundle.onGoodsDelete(ids);
     String id_str = StringUtil.arrayToString(ids, ",");
     String sql = "delete  from goods  where goods_id in (" + id_str + ")";
     this.baseDaoSupport.execute(sql, new Object[0]);
   }

   public List list(Integer[] ids) {
     if ((ids == null) || (ids.length == 0))
       return new ArrayList();
     String idstr = StringUtil.arrayToString(ids, ",");
     String sql = "select * from goods where goods_id in(" + idstr + ")";
     return this.baseDaoSupport.queryForList(sql, new Object[0]);
   }

   public GoodsPluginBundle getGoodsPluginBundle() {
     return this.goodsPluginBundle;
   }

   public void setGoodsPluginBundle(GoodsPluginBundle goodsPluginBundle) {
     this.goodsPluginBundle = goodsPluginBundle;
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

   public IPackageProductManager getPackageProductManager() {
     return this.packageProductManager;
   }

   public void setPackageProductManager(IPackageProductManager packageProductManager)
   {
     this.packageProductManager = packageProductManager;
   }

   public Goods getGoods(Integer goods_id) {
     Goods goods = (Goods)this.baseDaoSupport.queryForObject("select * from goods where goods_id=?", Goods.class, new Object[] { goods_id });

     return goods;
   }

   public IGoodsCatManager getGoodsCatManager() {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void batchEdit() {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String[] ids = request.getParameterValues("goodsidArray");
     String[] names = request.getParameterValues("name");
     String[] prices = request.getParameterValues("price");
     String[] cats = request.getParameterValues("catidArray");
     String[] market_enable = request.getParameterValues("market_enables");
     String[] store = request.getParameterValues("store");
     String[] sord = request.getParameterValues("sord");

     String sql = "";

     for (int i = 0; i < ids.length; i++) {
       sql = "";
       if ((names != null) && (names.length > 0) &&
         (!StringUtil.isEmpty(names[i]))) {
         if (!sql.equals(""))
           sql = sql + ",";
         sql = sql + " name='" + names[i] + "'";
       }


       if ((prices != null) && (prices.length > 0) &&
         (!StringUtil.isEmpty(prices[i]))) {
         if (!sql.equals(""))
           sql = sql + ",";
         sql = sql + " price=" + prices[i];
       }

       if ((cats != null) && (cats.length > 0) &&
         (!StringUtil.isEmpty(cats[i]))) {
         if (!sql.equals(""))
           sql = sql + ",";
         sql = sql + " cat_id=" + cats[i];
       }

       if ((store != null) && (store.length > 0) &&
         (!StringUtil.isEmpty(store[i]))) {
         if (!sql.equals(""))
           sql = sql + ",";
         sql = sql + " store=" + store[i];
       }

       if ((market_enable != null) && (market_enable.length > 0) &&
         (!StringUtil.isEmpty(market_enable[i]))) {
         if (!sql.equals(""))
           sql = sql + ",";
         sql = sql + " market_enable=" + market_enable[i];
       }

       if ((sord != null) && (sord.length > 0) &&
         (!StringUtil.isEmpty(sord[i]))) {
         if (!sql.equals(""))
           sql = sql + ",";
         sql = sql + " sord=" + sord[i];
       }

       sql = "update  goods set " + sql + " where goods_id=?";
       this.baseDaoSupport.execute(sql, new Object[] { ids[i] });
     }
   }


   public Map census()
   {
     String sql = "select count(0) from goods ";
     int allcount = this.baseDaoSupport.queryForInt(sql, new Object[0]);


     sql = "select count(0) from goods where market_enable=1 and  disabled = 0";
     int salecount = this.baseDaoSupport.queryForInt(sql, new Object[0]);


     sql = "select count(0) from goods where market_enable=0 and  disabled = 0";
     int unsalecount = this.baseDaoSupport.queryForInt(sql, new Object[0]);


     sql = "select count(0) from goods where   disabled = 1";
     int disabledcount = this.baseDaoSupport.queryForInt(sql, new Object[0]);


//     sql = "select count(0) from comments where   for_comment_id is null  and commenttype='goods' and object_type='discuss'";
//     int discusscount = this.baseDaoSupport.queryForInt(sql, new Object[0]);
     int discusscount = 0;
 int askcount = 0;
//     sql = "select count(0) from comments where for_comment_id is null  and  commenttype='goods' and object_type='ask'";
//     int askcount = this.baseDaoSupport.queryForInt(sql, new Object[0]);

     Map<String, Integer> map = new HashMap(2);
     map.put("salecount", Integer.valueOf(salecount));
     map.put("unsalecount", Integer.valueOf(unsalecount));
     map.put("disabledcount", Integer.valueOf(disabledcount));
     map.put("allcount", Integer.valueOf(allcount));
     map.put("discuss", Integer.valueOf(discusscount));
     map.put("ask", Integer.valueOf(askcount));
     return map;
   }




   public List getRecommentList(int goods_id, int cat_id, int brand_id, int num)
   {
     return null;
   }

   public List list() {
     String sql = "select * from goods where disabled = 0";
     return this.baseDaoSupport.queryForList(sql, new Object[0]);
   }

   public IMemberPriceManager getMemberPriceManager() {
     return this.memberPriceManager;
   }

   public ITagManager getTagManager() {
     return this.tagManager;
   }

   public void setTagManager(ITagManager tagManager) {
     this.tagManager = tagManager;
   }

   public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
     this.memberPriceManager = memberPriceManager;
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }

   public void updateField(String filedname, Object value, Integer goodsid)
   {
     this.baseDaoSupport.execute("update goods set " + filedname + "=? where goods_id=?", new Object[] { value, goodsid });
   }

   public Goods getGoodBySn(String goodSn)
   {
     Goods goods = (Goods)this.baseDaoSupport.queryForObject("select * from goods where sn=?", Goods.class, new Object[] { goodSn });
     return goods;
   }

   public IDepotMonitorManager getDepotMonitorManager() {
     return this.depotMonitorManager;
   }

   public void setDepotMonitorManager(IDepotMonitorManager depotMonitorManager) {
     this.depotMonitorManager = depotMonitorManager;
   }

   public List listByCat(Integer catid)
   {
     String sql = getListSql(0);
     if (catid.intValue() != 0) {
       Cat cat = this.goodsCatManager.getById(catid.intValue());
       sql = sql + " and  g.cat_id in(";
       sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat.getCat_path() + "%')  ";
     }


     return this.daoSupport.queryForList(sql, new Object[0]);
   }

   public List listByTag(Integer[] tagid)
   {
     String sql = getListSql(0);
     if ((tagid != null) && (tagid.length > 0)) {
       String tagidstr = StringUtil.arrayToString(tagid, ",");
       sql = sql + " and g.goods_id in(select rel_id from " + getTableName("tag_rel") + " where tag_id in(" + tagidstr + "))";
     }


     return this.daoSupport.queryForList(sql, new Object[0]);
   }

   public void incViewCount(Integer goods_id)
   {
     this.baseDaoSupport.execute("update goods set view_count = view_count + 1 where goods_id = ?", new Object[] { goods_id });
   }

   public List listGoods(String catid, String tagid, String goodsnum)
   {
     int num = 10;
     if (!StringUtil.isEmpty(goodsnum)) {
       num = Integer.valueOf(goodsnum).intValue();
     }

     StringBuffer sql = new StringBuffer();
     sql.append("select g.*,m.member_id as m_member_id,m.name as m_name from "
             + getTableName("tag_rel") + " r LEFT JOIN "
             + getTableName("goods") + " g ON g.goods_id=r.rel_id LEFT JOIN "
             + getTableName("member")+" m On m.store_id = g.store_id "
             +"where g.disabled=0 and g.market_enable=1");

     if (!StringUtil.isEmpty(catid)) {
       Cat cat = this.goodsCatManager.getById(Integer.valueOf(catid).intValue());
       if (cat != null) {
         String cat_path = cat.getCat_path();
         if (cat_path != null) {
           sql.append(" and  g.cat_id in(");
           sql.append("select c.cat_id from " + getTableName("goods_cat") + " ");
           sql.append(" c where c.cat_path like '" + cat_path + "%')");
         }
       }
     }

     if (!StringUtil.isEmpty(tagid)) {
       sql.append(" AND r.tag_id=" + tagid + "");
     }

     sql.append(" order by r.ordernum desc");

     List list = this.daoSupport.queryForListPage(sql.toString(), 1, num, new Object[0]);
     this.goodsDataFilterBundle.filterGoodsData(list);
     return list;
   }

//   public List listGoods(String catid, String tagid, String goodsnum, String)
//   {
//     int num = 10;
//     if (!StringUtil.isEmpty(goodsnum)) {
//       num = Integer.valueOf(goodsnum).intValue();
//     }
//
//     StringBuffer sql = new StringBuffer();
//     sql.append("select g.* from " + getTableName("tag_rel") + " r LEFT JOIN " + getTableName("goods") + " g ON g.goods_id=r.rel_id where g.disabled=0 and g.market_enable=1");
//
//     if (!StringUtil.isEmpty(catid)) {
//       Cat cat = this.goodsCatManager.getById(Integer.valueOf(catid).intValue());
//       if (cat != null) {
//         String cat_path = cat.getCat_path();
//         if (cat_path != null) {
//           sql.append(" and  g.cat_id in(");
//           sql.append("select c.cat_id from " + getTableName("goods_cat") + " ");
//           sql.append(" c where c.cat_path like '" + cat_path + "%')");
//         }
//       }
//     }
//
//     if (!StringUtil.isEmpty(tagid)) {
//       sql.append(" AND r.tag_id=" + tagid + "");
//     }
//
//     sql.append(" order by r.ordernum desc");
//
//     List list = this.daoSupport.queryForListPage(sql.toString(), 1, num, new Object[0]);
//     this.goodsDataFilterBundle.filterGoodsData(list);
//     return list;
//   }
   public GoodsDataFilterBundle getGoodsDataFilterBundle() {
     return this.goodsDataFilterBundle;
   }

   public void setGoodsDataFilterBundle(GoodsDataFilterBundle goodsDataFilterBundle) {
     this.goodsDataFilterBundle = goodsDataFilterBundle;
   }

   public List goodsBuyer(int goods_id, int pageSize)
   {
     String sql = "select distinct m.* from es_order o left join es_member m on o.member_id=m.member_id where order_id in (select order_id from es_order_items where goods_id=?)";


     Page page = this.daoSupport.queryForPage(sql, 1, pageSize, new Object[] { Integer.valueOf(goods_id) });

     return (List)page.getResult();
   }


   public Page searchGoods(Map goodsMap, int page, int pageSize, String other, String sort, String order)
   {
     String sql = creatTempSql(goodsMap, other);
     StringBuffer _sql = new StringBuffer(sql);
     this.goodsPluginBundle.onSearchFilter(_sql);
     _sql.append(" order by " + sort + " " + order);
     Page webpage = this.daoSupport.queryForPage(_sql.toString(), page, pageSize, new Object[0]);
     return webpage;
   }

   public List searchGoods(Map goodsMap)
   {
     String sql = creatTempSql(goodsMap, null);
     return this.daoSupport.queryForList(sql, Goods.class, new Object[0]);
   }


   private String creatTempSql(Map goodsMap, String other)
   {
     other = other == null ? "" : other;
     String sql = getListSql(0);
     Integer brandid = (Integer)goodsMap.get("brandid");
     Integer catid = (Integer)goodsMap.get("catid");
     String name = (String)goodsMap.get("name");
     String sn = (String)goodsMap.get("sn");
     Integer[] tagid = (Integer[])goodsMap.get("tagid");
     Integer stype = (Integer)goodsMap.get("stype");
     String keyword = (String)goodsMap.get("keyword");
     String order = (String)goodsMap.get("order");

     if ((brandid != null) && (brandid.intValue() != 0)) {
       sql = sql + " and g.brand_id = " + brandid + " ";
     }

     if ("1".equals(other))
     {
       sql = sql + " and g.no_discount=1";
     }
     if ("2".equals(other))
     {
       sql = sql + " and (select count(0) from " + getTableName("goods_lv_price") + " glp where glp.goodsid=g.goods_id) >0";
     }

     if ((stype != null) && (keyword != null) &&
       (stype.intValue() == 0)) {
       sql = sql + " and ( g.name like '%" + keyword + "%'";
       sql = sql + " or g.sn like '%" + keyword + "%')";
     }


     if ((name != null) && (!name.equals(""))) {
       name = name.trim();
       String[] keys = name.split("\\s");
       for (String key : keys) {
         sql = sql + " and g.name like '%";
         sql = sql + key;
         sql = sql + "%'";
       }
     }

     if ((sn != null) && (!sn.equals(""))) {
       sql = sql + "   and g.sn like '%" + sn + "%'";
     }


     if ((catid != null) && (catid.intValue() != 0)) {
       Cat cat = this.goodsCatManager.getById(catid.intValue());
       sql = sql + " and  g.cat_id in(";
       sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat.getCat_path() + "%')  ";
     }



     if ((tagid != null) && (tagid.length > 0)) {
       String tagidstr = StringUtil.arrayToString(tagid, ",");
       sql = sql + " and g.goods_id in(select rel_id from " + getTableName("tag_rel") + " where tag_id in(" + tagidstr + "))";
     }



     return sql;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\GoodsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */