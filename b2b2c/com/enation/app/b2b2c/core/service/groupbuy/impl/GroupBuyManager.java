 package com.enation.app.b2b2c.core.service.groupbuy.impl;

 import com.enation.app.b2b2c.core.model.groupbuy.GroupBuy;
 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyManager;
 import com.enation.app.shop.core.model.Goods;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.Map;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;








 @Component
 public class GroupBuyManager
   implements IGroupBuyManager
 {
   private IDaoSupport daoSupport;

   @Transactional(propagation=Propagation.REQUIRED)
   public int add(GroupBuy groupBuy)
   {
     groupBuy.setAdd_time(DateUtil.getDatelineLong());
     this.daoSupport.insert("es_groupbuy_goods", groupBuy);
     return this.daoSupport.getLastId("es_groupbuy_goods");
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void update(GroupBuy groupBuy)
   {
     this.daoSupport.update("es_groupbuy_goods", groupBuy, "gb_id=" + groupBuy.getGb_id());
   }



   public Page listByStoreId(int page, int pageSize, int storeid, Map params)
   {
     String gb_name = (String)params.get("gb_name");
     String gb_status = (String)params.get("gb_status");

     StringBuffer sql = new StringBuffer();
     sql.append("select g.*,a.act_name,a.start_time,a.end_time from es_groupbuy_goods g ,es_groupbuy_active a where g.store_id= ? and  g.act_id= a.act_id ");

     if (!StringUtil.isEmpty(gb_name)) {
       sql.append(" and g.gb_name like '%" + gb_name + "%'");
     }
     if (!StringUtil.isEmpty(gb_status)) {
       sql.append(" and g.gb_status=" + gb_status);
     }
     sql.append(" order by add_time ");

     return this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[] { Integer.valueOf(storeid) });
   }




   public Page listByActId(int page, int pageSize, int actid, Integer status)
   {
     StringBuffer sql = new StringBuffer();
     sql.append("select g.*,a.act_name,a.start_time,a.end_time,s.store_name from es_groupbuy_goods g ,es_groupbuy_active a,es_store s ");
     sql.append(" where g.act_id= ? and  g.act_id= a.act_id and g.store_id= s.store_id");
     if (status != null) {
       sql.append(" and g.gb_status=" + status);
     }
     sql.append(" order by add_time ");
     return this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[] { Integer.valueOf(actid) });
   }



   public Page search(int page, int pageSize, Integer catid, Double minprice, Double maxprice, int sort_key, int sort_type, Integer search_type)
   {
     StringBuffer sql = new StringBuffer("select * from es_groupbuy_goods where gb_status=1 ");
     if (catid != null) {
       sql.append(" and cat_id=" + catid);
     }

     if (minprice != null) {
       sql.append(" and price>=" + minprice);
     }


     if (maxprice != null) {
       sql.append(" and price<=" + maxprice);
     }


     if (search_type != null) {
       long now = DateUtil.getDatelineLong();
       if (search_type.intValue() == 0) {
         sql.append(" and start_time");
       }
     }


     if (sort_key == 0) {
       sql.append(" order by add_time ");
     }



     if (sort_key == 1) {
       sql.append(" order by price ");
     }

     if (sort_key == 2) {
       sql.append(" order by price/original_price ");
     }

     if (sort_key == 3) {
       sql.append(" order by buy_num ");
     }

     if (sort_type == 0) {
       sql.append(" asc");
     }

     if (sort_type == 1) {
       sql.append(" desc");
     }

     return this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[0]);
   }



   public GroupBuy get(int gbid)
   {
     String sql = "select * from es_groupbuy_goods where gb_id=?";
     GroupBuy groupBuy = (GroupBuy)this.daoSupport.queryForObject(sql, GroupBuy.class, new Object[] { Integer.valueOf(gbid) });
     sql = "select * from es_goods where goods_id=?";

     Goods goods = (Goods)this.daoSupport.queryForObject(sql, Goods.class, new Object[] { Integer.valueOf(groupBuy.getGoods_id()) });
     groupBuy.setGoods(goods);

     return groupBuy;
   }


   public void delete(int gbid)
   {
     String sql = "delete from  es_groupbuy_goods where gb_id=?";
     this.daoSupport.execute(sql, new Object[] { Integer.valueOf(gbid) });
   }

   public void auth(int gbid, int status)
   {
     String sql = "update es_groupbuy_goods set gb_status=? where gb_id=?";
     this.daoSupport.execute(sql, new Object[] { Integer.valueOf(status), Integer.valueOf(gbid) });
   }


   public IDaoSupport getDaoSupport()
   {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) { this.daoSupport = daoSupport; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\groupbuy\impl\GroupBuyManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */