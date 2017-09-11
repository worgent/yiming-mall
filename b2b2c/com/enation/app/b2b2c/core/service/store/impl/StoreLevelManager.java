 package com.enation.app.b2b2c.core.service.store.impl;

 import com.enation.app.b2b2c.core.model.store.StoreLevel;
 import com.enation.app.b2b2c.core.service.store.IStoreLevelManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.stereotype.Component;

 @Component
 public class StoreLevelManager extends BaseSupport implements IStoreLevelManager
 {
   public List storeLevelList()
   {
     String sql = "select * from es_store_level";
     return this.daoSupport.queryForList(sql, new Object[0]);
   }

   public void addStoreLevel(String levelName)
   {
     StoreLevel storeLevel = new StoreLevel();
     storeLevel.setLevel_name(levelName);
     this.daoSupport.insert("es_store_level", storeLevel);
   }



   public void editStoreLevel(String levelName, Integer levelId)
   {
     String sql = "update es_store_level set level_name=? where level_id=?";
     this.daoSupport.execute(sql, new Object[] { levelName, levelId });
   }

   public void delStoreLevel(Integer levelId)
   {
     String sql = "DELETE from es_store_level WHERE level_id=?";
     this.daoSupport.execute(sql, new Object[] { levelId });
   }

   public StoreLevel getStoreLevel(Integer levelId)
   {
     String sql = "select * from es_store_level where level_id=?";
     return (StoreLevel)this.baseDaoSupport.queryForObject(sql, StoreLevel.class, new Object[] { levelId });
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\store\impl\StoreLevelManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */