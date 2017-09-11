 package com.enation.app.b2b2c.core.service.store.impl;

 import com.enation.app.b2b2c.component.plugin.store.StorePluginBundle;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.model.store.Store;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.store.IStoreManager;
 import com.enation.app.b2b2c.core.service.store.IStoreSildeManager;
 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.Regions;
 import com.enation.app.base.core.service.IRegionsManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.database.Page;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;


 @Component
 public class StoreManager
   extends BaseSupport
   implements IStoreManager
 {
   private IStoreMemberManager storeMemberManager;
   private IStoreSildeManager storeSildeManager;
   private IRegionsManager regionsManager;
   private StorePluginBundle storePluginBundle;

   @Transactional(propagation=Propagation.REQUIRED)
   public void apply(Store store)
   {
     Member member = this.storeMemberManager.getStoreMember();
     if (member != null) {
       store.setMember_id(member.getMember_id().intValue());
       store.setMember_name(member.getUname());
     }
     getStoreRegions(store);
     this.daoSupport.insert("es_store", store);
     this.storePluginBundle.onAfterApply(store);
   }



   private void getStoreRegions(Store store)
   {
     store.setStore_province(this.regionsManager.get(store.getStore_provinceid()).getLocal_name());
     store.setStore_city(this.regionsManager.get(store.getStore_cityid()).getLocal_name());
     store.setStore_region(this.regionsManager.get(store.getStore_regionid()).getLocal_name());
   }




   @Transactional(propagation=Propagation.REQUIRED)
   public void audit_pass(Integer member_id, Integer storeId, Integer pass, Integer name_auth, Integer store_auth)
   {
     if (pass.intValue() == 1) {
       store_auth = Integer.valueOf(store_auth == null ? 0 : store_auth.intValue());
       name_auth = Integer.valueOf(name_auth == null ? 0 : name_auth.intValue());
       this.daoSupport.execute("update es_store set create_time=?,name_auth=?,store_auth=? where store_id=?", new Object[] { Long.valueOf(DateUtil.getDatelineLong()), name_auth, store_auth, storeId });
       editStoredis(Integer.valueOf(1), storeId);
       this.storePluginBundle.onAfterPass(getStore(storeId));
     }
     else {
       this.daoSupport.execute("update es_store set disabled=? where store_id=?", new Object[] { Integer.valueOf(-1), storeId });

       this.daoSupport.execute("update es_member set is_store=? where member_id=?", new Object[] { Integer.valueOf(0), member_id });
     }
   }




   public Page store_list(Map other, Integer disabled, int pageNo, int pageSize)
   {
     StringBuffer sql = new StringBuffer("");
     disabled = Integer.valueOf(disabled == null ? 1 : disabled.intValue());
     String store_name = other.get("name") == null ? "" : other.get("name").toString();
     String searchType = other.get("searchType") == null ? "" : other.get("searchType").toString();


     if (disabled.equals("-2")) {
       sql.append("select s.*,sl.level_name as level_name from es_store s INNER JOIN es_store_level as sl ON s.store_level=sl.level_id  where  disabled!=" + disabled);
     } else
       sql.append("select s.*,sl.level_name as level_name from es_store s INNER JOIN es_store_level as sl ON s.store_level=sl.level_id  where  disabled=" + disabled);
     if (!StringUtil.isEmpty(store_name)) {
       sql.append("  and s.store_name like '%" + store_name + "%'");
     }
     if ((!StringUtil.isEmpty(searchType)) && (!searchType.equals("default"))) {
       sql.append(" order by " + searchType + " desc");
     } else {
       sql.append(" order by store_id desc");
     }

     return this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, new Object[0]);
   }

   @Override
   public List store_recommend_list() {

         StringBuffer sql = new StringBuffer("");

         sql.append("select s.*,m.* from es_store s " +
                 "INNER JOIN es_member as m ON s.store_id=m.store_id  " +
                 "where  disabled=1 and s.store_recommend = 1 ");
         sql.append(" order by s.store_id desc");


         return this.daoSupport.queryForList(sql.toString());

   }


   @Transactional(propagation=Propagation.REQUIRED)
   public void disStore(Integer storeId)
   {
     this.daoSupport.execute("update es_store set  end_time=? where store_id=?", new Object[] { Long.valueOf(DateUtil.getDatelineLong()), storeId });
     editStoredis(Integer.valueOf(2), storeId);
     this.daoSupport.execute("update es_member set is_store=3 where member_id=" + getStore(storeId).getMember_id(), new Object[0]);
   }




   public void useStore(Integer storeId)
   {
     editStoredis(Integer.valueOf(1), storeId);
   }




   private void editStoredis(Integer disabled, Integer store_id)
   {
     this.daoSupport.execute("update es_store set disabled=? where store_id=?", new Object[] { disabled, store_id });
   }




   public Store getStore(Integer storeId)
   {
     String sql = "select * from es_store where store_id=" + storeId;
     List<Store> list = this.baseDaoSupport.queryForList(sql, Store.class, new Object[0]);
     Store store = (Store)list.get(0);
     if ((store.getId_img() != null) && (!StringUtil.isEmpty(store.getId_img()))) {
       store.setId_img(UploadUtil.replacePath(store.getId_img()));
     }
     if ((store.getLicense_img() != null) && (!StringUtil.isEmpty(store.getLicense_img()))) {
       store.setLicense_img(UploadUtil.replacePath(store.getLicense_img()));
     }
     return store;
   }

   public void editStore(Store store) {
     StoreMember member = this.storeMemberManager.getMemberByStoreId(store.getStore_id());
     this.daoSupport.update("es_store", store, "store_id=" + store.getStore_id());
     if (store.getDisabled() == 1) {
       this.daoSupport.execute("update  es_member set is_store=1 where member_id=?", new Object[] { member.getMember_id() });
     } else
       this.daoSupport.execute("update  es_member set is_store=2 where member_id=?", new Object[] { member.getMember_id() });
   }

   public void editStore(Map store) {
     this.daoSupport.update("es_store", store, " store_id=" + store.get("store_id"));
   }




   public boolean checkStore()
   {
     Member member = (Member)ThreadContextHolder.getSessionContext().getAttribute("curr_member");
     String sql = "select count(store_id) from es_store where member_id=?";
     int isHas = this.daoSupport.queryForInt(sql, new Object[] { member.getMember_id() });
     if (isHas > 0) {
       return true;
     }
     return false;
   }




   @Transactional(propagation=Propagation.REQUIRED)
   public void save(Store store)
   {
     this.baseDaoSupport.insert("es_store", store);
   }





   public Integer checkIdNumber(String idNumber)
   {
     String sql = "select member_id from store where id_number=?";
     List result = this.baseDaoSupport.queryForList(sql, new Object[] { idNumber });
     return Integer.valueOf(result.size() > 0 ? 1 : 0);
   }




   @Transactional(propagation=Propagation.REQUIRED)
   public void editStoreOnekey(String key, String value)
   {
     StoreMember member = this.storeMemberManager.getStoreMember();
     Map map = new HashMap();
     map.put(key, value);
     this.daoSupport.update("es_store", map, "store_id=" + member.getStore_id());
   }




   @Transactional(propagation=Propagation.REQUIRED)
   public void addcollectNum(Integer storeid)
   {
     String sql = "update es_store set store_collect = store_collect+1 where store_id=?";
     this.baseDaoSupport.execute(sql, new Object[] { storeid });
   }




   public boolean checkStoreName(String storeName)
   {
     String sql = "select  count(store_id) from es_store where store_name=?";
     Integer count = Integer.valueOf(this.daoSupport.queryForInt(sql, new Object[] { storeName }));
     return count.intValue() != 0;
   }




   public void reduceCollectNum(Integer storeid)
   {
     String sql = "update es_store set store_collect = store_collect-1 where store_id=?";
     this.baseDaoSupport.execute(sql, new Object[] { storeid });
   }





   @Transactional(propagation=Propagation.REQUIRED)
   public void saveStoreLicense(Integer storeid, String id_img, String license_img, Integer store_auth, Integer name_auth)
   {
     if (store_auth.intValue() == 2) {
       this.daoSupport.execute("update es_store set store_auth=?,license_img='?' where store_id=?", new Object[] { store_auth, license_img, storeid });
     }
     if (name_auth.intValue() == 2) {
       this.daoSupport.execute("update es_store set name_auth=?,id_img='?' where store_id=?", new Object[] { name_auth, id_img, storeid });
     }
   }




   public Page auth_list(Map other, Integer disabled, int pageNo, int pageSize)
   {
     StringBuffer sql = new StringBuffer("select s.*,sl.level_name as level_name from es_store s INNER JOIN es_store_level as sl ON s.store_level=sl.level_id  where  disabled=" + disabled);
     sql.append(" and (store_auth=2 or name_auth=2)");
     return this.daoSupport.queryForPage(sql.toString(), pageNo, pageSize, new Object[0]);
   }





   public void auth_pass(Integer store_id, Integer name_auth, Integer store_auth)
   {
     if (store_auth != null) {
       this.daoSupport.execute("update es_store set store_auth=? where store_id=?", new Object[] { store_auth, store_id });
     }
     if (name_auth != null) {
       this.daoSupport.execute("update es_store set name_auth=? where store_id=?", new Object[] { name_auth, store_id });
     }
   }

   public StorePluginBundle getStorePluginBundle()
   {
     return this.storePluginBundle;
   }

   public void setStorePluginBundle(StorePluginBundle storePluginBundle) { this.storePluginBundle = storePluginBundle; }

   public IStoreMemberManager getStoreMemberManager()
   {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) { this.storeMemberManager = storeMemberManager; }

   public IStoreSildeManager getStoreSildeManager() {
     return this.storeSildeManager;
   }

   public void setStoreSildeManager(IStoreSildeManager storeSildeManager) {
     this.storeSildeManager = storeSildeManager;
   }

   public IRegionsManager getRegionsManager() { return this.regionsManager; }

   public void setRegionsManager(IRegionsManager regionsManager) {
     this.regionsManager = regionsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\store\impl\StoreManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */