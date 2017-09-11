 package com.enation.app.b2b2c.core.service.impl;

 import com.enation.app.b2b2c.core.model.StoreTemlplate;
 import com.enation.app.b2b2c.core.service.IStoreTemplateManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;

 @Component
 public class StoreTemplateManager
   extends BaseSupport
   implements IStoreTemplateManager
 {
   @Transactional(propagation=Propagation.REQUIRED)
   public Integer add(StoreTemlplate storeTemlplate)
   {
     this.baseDaoSupport.insert("es_store_template", storeTemlplate);
     return Integer.valueOf(this.baseDaoSupport.getLastId("es_store_template"));
   }

   public List getTemplateList(Integer store_id)
   {
     String sql = "select * from es_store_template where store_id=?";
     List list = this.baseDaoSupport.queryForList(sql, new Object[] { store_id });
     return list;
   }

   public Integer getLastId()
   {
     Integer id = Integer.valueOf(this.baseDaoSupport.getLastId("es_store_template"));
     return id;
   }

   public Map getTemplae(Integer store_id, Integer tempid)
   {
     String sql = "select * from es_store_template where store_id=? and id=?";
     List list = this.baseDaoSupport.queryForList(sql, new Object[] { store_id, tempid });
     Map map = (Map)list.get(0);
     return map;
   }


   public void edit(StoreTemlplate storeTemlplate)
   {
     this.baseDaoSupport.update("es_store_template", storeTemlplate, " id=" + storeTemlplate.getId());
   }


   public void delete(Integer tempid)
   {
     Integer def_temp = Integer.valueOf(this.baseDaoSupport.queryForInt("select def_temp from es_store_template where id=?", new Object[] { tempid }));
     if (def_temp.intValue() == 1) {
       throw new RuntimeException("不能删除默认物流模板");
     }

     String sql = "select * from es_dly_type where template_id=?";
     List<Map> list = this.baseDaoSupport.queryForList(sql, new Object[] { tempid });
     if (!list.isEmpty()) {
       StringBuffer dlyids = new StringBuffer();
       for (Map map : list) {
         Integer type_id = (Integer)map.get("type_id");
         dlyids.append(type_id + ",");
       }
       String ids = dlyids.toString().substring(0, dlyids.toString().length() - 1);

       String areadelsql = "delete from es_dly_type_area where type_id in (" + ids + ")";
       String dlydelsql = "delete from es_dly_type where template_id=?";
       this.baseDaoSupport.execute(areadelsql, new Object[0]);
       this.baseDaoSupport.execute(dlydelsql, new Object[] { tempid });
     }
     this.baseDaoSupport.execute("delete from es_store_template where id=?", new Object[] { tempid });
   }

   public Integer getDefTempid(Integer storeid)
   {
     String sql = "select * from es_store_template where store_id=? and def_temp=1";
     List list = this.baseDaoSupport.queryForList(sql, new Object[] { storeid });
     if (list.isEmpty()) return null;
     Map map = (Map)list.get(0);
     return (Integer)map.get("id");
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public void setDefTemp(Integer tempid, Integer storeid)
   {
     this.baseDaoSupport.execute("update es_store_template set def_temp=0 where store_id=?", new Object[] { storeid });
     this.baseDaoSupport.execute("update es_store_template set def_temp=1 where id=?", new Object[] { tempid });
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\impl\StoreTemplateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */