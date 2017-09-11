 package com.enation.app.shop.core.service.impl;

 import com.enation.app.shop.core.model.StoreLog;
 import com.enation.app.shop.core.service.IStoreLogManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;




 public class StoreLogManager
   extends BaseSupport<StoreLog>
   implements IStoreLogManager
 {
   public void add(StoreLog storeLog)
   {
     this.baseDaoSupport.insert("store_log", storeLog);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\StoreLogManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */