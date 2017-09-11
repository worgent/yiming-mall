 package com.enation.app.b2b2c.component.plugin.store;

 import com.enation.app.b2b2c.core.model.goods.StoreTag;
 import com.enation.app.b2b2c.core.model.store.Store;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsTagManager;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;



 @Component
 public class StoreAddGoodsTagsPlugin
   extends AutoRegisterPlugin
   implements IAfterStorePassEvent
 {
   private IStoreGoodsTagManager storeGoodsTagManager;

   public void AfterStorePassEvent(Store store)
   {
     StoreTag storeTag = new StoreTag();
     storeTag.setStore_id(Integer.valueOf(store.getStore_id()));

     storeTag.setTag_name("热卖排行");
     storeTag.setMark("hot");
     this.storeGoodsTagManager.add(storeTag);

     storeTag.setTag_name("新品推荐");
     storeTag.setMark("new");
     this.storeGoodsTagManager.add(storeTag);

     storeTag.setTag_name("推荐商品");
     storeTag.setMark("recommend");
     this.storeGoodsTagManager.add(storeTag);
   }

   public IStoreGoodsTagManager getStoreGoodsTagManager() { return this.storeGoodsTagManager; }

   public void setStoreGoodsTagManager(IStoreGoodsTagManager storeGoodsTagManager) {
     this.storeGoodsTagManager = storeGoodsTagManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\store\StoreAddGoodsTagsPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */