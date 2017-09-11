 package com.enation.app.shop.core.service.impl;

 import com.enation.app.shop.core.model.Cat;
 import com.enation.app.shop.core.service.IDepotMonitorManager;
 import com.enation.app.shop.core.service.IGoodsCatManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.jdbc.core.RowMapper;

 public class DepotMonitorManager
   extends BaseSupport implements IDepotMonitorManager
 {
   private IGoodsCatManager goodsCatManager;

   public List getTaskList()
   {
     String sql = "select  count(0)  num, depotid,d.name from  " + getTableName("depot") + " d left join " + getTableName("goods_depot") + " gd on d.id = gd.depotid left join " + getTableName("goods") + " g on gd.goodsid=g.goods_id   where  gd.iscmpl=0 and g.disabled=0 group by depotid";
     List list = this.daoSupport.queryForList(sql, new Object[0]);

     return list;
   }

   public List getAllocationList()
   {
     String sql = "select count(0) num,ai.depotid,d.name  from  " + getTableName("depot") + " d  left join " + getTableName("allocation_item") + " ai on ai.depotid=d.id  where ai.iscmpl=0 group by depotid";
     List list = this.daoSupport.queryForList(sql, new Object[0]);
     return list;
   }

   public List getSendList()
   {
     String sql = "select count(0) num,o.depotid,d.name  from  " + getTableName("depot") + " d  left join " + getTableName("order") + " o on o.depotid=d.id  where o.status=4 group by depotid";
     List list = this.daoSupport.queryForList(sql, new Object[0]);
     return list;
   }

   public int getTotalByStatus(int status)
   {
     String sql = "select count(0) from " + getTableName("order") + " where status=?";
     return this.daoSupport.queryForInt(sql, new Object[] { Integer.valueOf(status) });
   }

   public List depotidDepotByGoodsid(int goodsid, int catid) {
     String tablename = "product_store";

     if (tablename == "") {
       return null;
     }
     String sql = "select d.name, d.name,sum(s.store) num from " + getTableName("depot") + " d left join " + getTableName(tablename) + " s on d.id=s.depotid where s.goodsid=" + goodsid + " group by depotid";

     List list = this.daoSupport.queryForList(sql, new Object[0]);
     return list;
   }



   public List searchOrderSalesAmout(int startDate, int endDate)
   {
     String sql = "";
     if (EopSetting.DBTYPE.equals("1")) {
       sql = "select FROM_UNIXTIME(sale_cmpl_time,'%Y-%m-%d') as isdate,sum(order_amount) as amount  from es_order where  sale_cmpl=1 ";
     } else if (EopSetting.DBTYPE.equals("3")) {
       sql = "select substring(convert(varchar(10),dateadd(ss,sale_cmpl_time + 28800,'1970-01-01'),120),1,10) as isdate,sum(order_amount) as amount  from es_order where  sale_cmpl=1 ";
     } else {
       sql = "select to_char(TO_DATE('01011970','mmddyyyy')+1/24/60/60*(sale_cmpl_time),'yyyy-mm-dd') as isdate,sum(order_amount) as amount  from es_order where  sale_cmpl=1 ";
     }
     if ((startDate > 0) && (endDate > 0)) {
       sql = sql + " and sale_cmpl_time>=" + startDate;
       sql = sql + " and sale_cmpl_time<" + endDate;
     }
     sql = sql + " group by isdate";

     RowMapper mapper = new RowMapper()
     {
       public Object mapRow(ResultSet rs, int arg1) throws SQLException
       {
         Date isdate = rs.getDate("isdate");
         Double amount = Double.valueOf(rs.getDouble("amount"));
         Map map = new HashMap();
         map.put("isdate", isdate);
         map.put("amount", amount);
         return map;
       }
     };
     List list = this.daoSupport.queryForList(sql, mapper, new Object[0]);
     return list;
   }

   public List searchOrderSaleNumber(int startDate, int endDate, int catid) {
     String sql = "select * from (select g.goods_id, g.name,sum(oi.num) as total   from " + getTableName("order_items") + " oi left join " + getTableName("goods") + " g on oi.goods_id=g.goods_id left join " + getTableName("order") + " o on oi.order_id=o.order_id where o.sale_cmpl=1";

     if ((startDate > 0) && (endDate > 0)) {
       sql = sql + " and o.sale_cmpl_time>=" + startDate;
       sql = sql + " and o.sale_cmpl_time<" + endDate;
     }
     if (catid > 0) {
       Cat cat = this.goodsCatManager.getById(catid);
       sql = sql + " and  g.cat_id in(";
       sql = sql + "select c.cat_id from " + getTableName("goods_cat") + " c where c.cat_path like '" + cat.getCat_path() + "%')  ";
     }

     sql = sql + " group by oi.goods_id) t order by t.total desc";


     List list = this.daoSupport.queryForList(sql, new Object[0]);
     return list;
   }

   public Page searchStoreLog(int startDate, int endDate, int depotid, int depotType, int opType, int page, int pageSize) {
     String sql = "select g.name,d.name as depotname,sl.*  from " + getTableName("store_log") + " sl left join " + getTableName("goods") + " g on sl.goodsid=g.goods_id left join " + getTableName("depot") + " d on sl.depotid=d.id where 1=1 ";
     if ((startDate > 0) && (endDate > 0)) {
       sql = sql + " and sl.dateline>=" + startDate;
       sql = sql + " and sl.dateline<" + endDate;
     }
     if (depotid > 0) {
       sql = sql + " and sl.depotid=" + depotid;
     }
     if (depotType != -1) {
       sql = sql + " and sl.depot_type=" + depotType;
     }
     if (opType != -1) {
       sql = sql + " and sl.op_type=" + opType;
     }
     sql = sql + " order by dateline desc";
     Page webpage = this.daoSupport.queryForPage(sql, page, pageSize, new Object[0]);
     return webpage;
   }

   public IGoodsCatManager getGoodsCatManager()
   {
     return this.goodsCatManager;
   }

   public void setGoodsCatManager(IGoodsCatManager goodsCatManager) {
     this.goodsCatManager = goodsCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\DepotMonitorManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */