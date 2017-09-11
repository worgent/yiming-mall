 package com.enation.app.b2b2c.component.plugin.store;

 import com.enation.app.b2b2c.core.model.store.Store;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.HashMap;
 import java.util.Map;
 import org.springframework.stereotype.Component;



 @Component
 public class StoreAddSildePlugin
   extends AutoRegisterPlugin
   implements IAfterStorePassEvent
 {
   private IDaoSupport daoSupport;

   public void AfterStorePassEvent(Store store)
   {
     Map map = new HashMap();
     for (int i = 0; i < 5; i++) {
       map.put("store_id", Integer.valueOf(store.getStore_id()));
       map.put("img", "fs:/images/s_side.jpg");
       this.daoSupport.insert("es_store_silde", map);
     }
   }

   public IDaoSupport getDaoSupport() { return this.daoSupport; }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\store\StoreAddSildePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */