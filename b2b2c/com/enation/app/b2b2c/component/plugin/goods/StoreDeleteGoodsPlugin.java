 package com.enation.app.b2b2c.component.plugin.goods;

 import com.enation.app.b2b2c.core.model.goods.StoreGoods;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.core.plugin.goods.IGoodsDeleteEvent;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;






 @Component
 public class StoreDeleteGoodsPlugin
   extends AutoRegisterPlugin
   implements IGoodsDeleteEvent
 {
   private IStoreGoodsManager storeGoodsManager;
   private IStoreMemberManager storeMemberManager;
   private IDaoSupport daoSupport;

   public void onGoodsDelete(Integer[] goodsid)
   {
     for (int i = 0; i < goodsid.length; i++) {
       StoreGoods goods = this.storeGoodsManager.getGoods(goodsid[i]);
       if (goods.getMarket_enable().equals("1")) {
         StoreMember member = this.storeMemberManager.getStoreMember();
         String sql = "update es_store set goods_num=goods_num+1 where store_id=?";
         this.daoSupport.execute(sql, new Object[] { member.getStore_id() });
       }
     }
   }

   public IStoreGoodsManager getStoreGoodsManager() { return this.storeGoodsManager; }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
     this.storeGoodsManager = storeGoodsManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }

   public IDaoSupport getDaoSupport() { return this.daoSupport; }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\goods\StoreDeleteGoodsPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */