 package com.enation.app.b2b2c.core.service.impl;

 import com.enation.app.b2b2c.core.model.StoreDlyCenter;
 import com.enation.app.b2b2c.core.service.IStoreDlyCenterManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.stereotype.Component;

 @Component
 public class StoreDlyCenterManager
   extends BaseSupport implements IStoreDlyCenterManager
 {
   public List getDlyCenterList(Integer store_id)
   {
     String sql = "select * from es_dly_center where store_id=?";
     List list = this.baseDaoSupport.queryForList(sql, new Object[] { store_id });
     return list;
   }

   public void addDlyCenter(StoreDlyCenter dlyCenter)
   {
     this.baseDaoSupport.insert("es_dly_center", dlyCenter);
   }


   public void editDlyCenter(StoreDlyCenter dlyCenter)
   {
     this.baseDaoSupport.update("es_dly_center", dlyCenter, " dly_center_id=" + dlyCenter.getDly_center_id());
   }


   public StoreDlyCenter getDlyCenter(Integer store_id, Integer dlyid)
   {
     String sql = "select * from es_dly_center where dly_center_id=? and store_id=?";
     List list = this.baseDaoSupport.queryForList(sql, StoreDlyCenter.class, new Object[] { dlyid, store_id });
     StoreDlyCenter dlyCenter = (StoreDlyCenter)list.get(0);
     return dlyCenter;
   }

   public void delete(Integer dly_id)
   {
     String sql = "delete from es_dly_center where dly_center_id=" + dly_id;
     this.baseDaoSupport.execute(sql, new Object[0]);
   }


   public void site_default(Integer dly_id, Integer store_id)
   {
     String sql = "update es_dly_center set disabled='false' where store_id=" + store_id;
     String sitesql = "update es_dly_center set disabled='true' where dly_center_id=" + dly_id;

     this.baseDaoSupport.execute(sql, new Object[0]);
     this.baseDaoSupport.execute(sitesql, new Object[0]);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\impl\StoreDlyCenterManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */