 package com.enation.app.b2b2c.core.action.api.member;

 import com.enation.app.b2b2c.core.model.MemberCollect;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.IStoreCollectManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.store.IStoreManager;
 import com.enation.framework.action.WWAction;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;











 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("storeCollect")
 public class StoreCollectApiAction
   extends WWAction
 {
   private IStoreCollectManager storeCollectManager;
   private IStoreMemberManager storeMemberManager;
   private IStoreManager storeManager;
   private Integer store_id;
   private Integer celloct_id;

   public String addCollect()
   {
     StoreMember member = this.storeMemberManager.getStoreMember();
     if (member != null) {
       if ((member.getStore_id() != null) && (member.getStore_id().equals(this.store_id))) {
         showErrorJson("不能收藏自己的店铺！");
         return "json_message";
       }
       MemberCollect collect = new MemberCollect();
       collect.setMember_id(member.getMember_id());
       collect.setStore_id(this.store_id);
       try {
         this.storeCollectManager.addCollect(collect);
         this.storeManager.addcollectNum(this.store_id);
         showSuccessJson("收藏成功！");
       }
       catch (RuntimeException e) {
         showErrorJson(e.getMessage());
       }
     } else {
       showErrorJson("请登录！收藏失败！");
     }
     return "json_message";
   }





   public String del()
   {
     try
     {
       this.storeCollectManager.delCollect(this.celloct_id);
       this.storeManager.reduceCollectNum(this.store_id);
       showSuccessJson("删除成功！");
     } catch (Exception e) {
       showErrorJson("删除失败，请重试！");
     }
     return "json_message";
   }

   public IStoreCollectManager getStoreCollectManager()
   {
     return this.storeCollectManager;
   }

   public void setStoreCollectManager(IStoreCollectManager storeCollectManager) {
     this.storeCollectManager = storeCollectManager;
   }

   public IStoreMemberManager getStoreMemberManager()
   {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }

   public Integer getStore_id() {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) {
     this.store_id = store_id;
   }

   public Integer getCelloct_id() {
     return this.celloct_id;
   }

   public void setCelloct_id(Integer celloct_id) {
     this.celloct_id = celloct_id;
   }

   public IStoreManager getStoreManager() {
     return this.storeManager;
   }

   public void setStoreManager(IStoreManager storeManager) {
     this.storeManager = storeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\member\StoreCollectApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */