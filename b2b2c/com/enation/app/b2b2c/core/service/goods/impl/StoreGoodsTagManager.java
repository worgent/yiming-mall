 package com.enation.app.b2b2c.core.service.goods.impl;

 import com.enation.app.b2b2c.core.model.goods.StoreTag;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsTagManager;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.StringUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;



 @Component
 public class StoreGoodsTagManager
   implements IStoreGoodsTagManager
 {
   private IDaoSupport daoSupport;

   public List list(Integer store_id)
   {
     String sql = "select * from es_tags where store_id=?";
     return this.daoSupport.queryForList(sql, new Object[] { store_id });
   }




   public void add(StoreTag tag)
   {
     this.daoSupport.insert("es_tags", tag);
   }




   public void deleteRel(Integer tagId, Integer[] reg_id)
   {
     if ((reg_id == null) || (reg_id.length == 0)) return;
     String idstr = StringUtil.arrayToString(reg_id, ",");

     this.daoSupport.execute("delete from es_tag_rel where rel_id in(" + idstr + ") and tag_id=" + tagId, new Object[0]);
   }





   public void addRels(Integer tagId, Integer[] reg_id)
   {
     for (int i = 0; i < reg_id.length; i++) {
       int count = this.daoSupport.queryForInt("select count(*) from es_tag_rel where tag_id=? and rel_id=?", new Object[] { tagId, reg_id[i] });
       if (count == 0) {
         Map map = new HashMap();
         map.put("tag_id", tagId);
         map.put("rel_id", reg_id[i]);
         map.put("ordernum", Integer.valueOf(0));
         this.daoSupport.insert("es_tag_rel", map);
       }
     }
   }




   private void editRels(Map map, String where)
   {
     this.daoSupport.update("es_tag_rel", map, where);
   }




   public void saveSort(Integer tagId, Integer[] reg_id, Integer[] ordernum)
   {
     for (int i = 0; i < ordernum.length; i++) {
       Map map = new HashMap();
       map.put("ordernum", ordernum[i]);
       editRels(map, " tag_id=" + tagId + " and rel_id=" + reg_id[i]);
     }
   }




   public Page getGoodsList(int tagid, int page, int pageSize)
   {
     StringBuffer sql = new StringBuffer();
     sql.append("select g.goods_id,g.name,r.tag_id,r.ordernum,g.price,g.buy_num from es_tag_rel r LEFT JOIN es_goods g ON g.goods_id=r.rel_id where g.disabled=0 and g.market_enable=1");
     sql.append(" and r.tag_id = ?");
     sql.append(" order by r.ordernum desc");
     Page webpage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[] { Integer.valueOf(tagid) });

     return webpage;
   }




   public Page getGoodsList(Map map, int page, int pageSize)
   {
     String mark = (String)map.get("mark");
     Integer storeid = (Integer)map.get("storeid");
     Integer tag_id = Integer.valueOf(this.daoSupport.queryForInt("select tag_id from es_tags where store_id=? and mark=?", new Object[] { storeid, mark }));

     StringBuffer sql = new StringBuffer();
     sql.append("select g.goods_id,g.name,r.tag_id,r.ordernum,g.price,g.buy_num,g.thumbnail,g.small from es_tag_rel r LEFT JOIN es_goods g ON g.goods_id=r.rel_id where g.disabled=0 and g.market_enable=1");
     sql.append(" and r.tag_id=? ");
     sql.append(" order by r.ordernum desc");
     Page webpage = this.daoSupport.queryForPage(sql.toString(), page, pageSize, new Object[] { tag_id });
     return webpage;
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) { this.daoSupport = daoSupport; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\goods\impl\StoreGoodsTagManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */