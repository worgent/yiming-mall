 package com.enation.app.b2b2c.core.action.backend.store;

 import com.enation.app.b2b2c.core.model.store.StoreLevel;
 import com.enation.app.b2b2c.core.service.store.IStoreLevelManager;
 import com.enation.framework.action.WWAction;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.stereotype.Component;







 @Component
 @ParentPackage("eop_default")
 @Namespace("/b2b2c/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="storelevel", type="freemarker", location="/b2b2c/admin/level/store_level_list.html"), @org.apache.struts2.convention.annotation.Result(name="info", type="freemarker", location="/b2b2c/admin/level/store_level_info.html")})
 @Action("storelevel")
 public class StoreLevelAction
   extends WWAction
 {
   private IStoreLevelManager storeLevelManager;
   private Integer level_id;
   private StoreLevel storeLevel;
   private String level_name;

   public String storeLevel() { return "storelevel"; }

   public String storeLevelJson() {
     showGridJson(this.storeLevelManager.storeLevelList());
     return "json_message";
   }





   public String add() { return "info"; }

   public String edit() {
     this.storeLevel = this.storeLevelManager.getStoreLevel(this.level_id);
     return "info";
   }


   public String delStoreLevel()
   {
     try
     {
       this.storeLevelManager.delStoreLevel(this.level_id);
       showSuccessJson("删除店铺等级成功");
     } catch (Exception e) {
       showErrorJson("删除店铺等级失败");
       this.logger.error("删除店铺等级失败:" + e);
     }
     return "json_message";
   }


   public String addStoreLevel()
   {
     try
     {
       this.storeLevelManager.addStoreLevel(this.level_name);
       showSuccessJson("添加店铺等级成功");
     } catch (Exception e) {
       showErrorJson("添加店铺等级失败");
       this.logger.error("添加店铺等级失败:" + e);
     }
     return "json_message";
   }


   public String editStoreLevel()
   {
     try
     {
       this.storeLevelManager.editStoreLevel(this.storeLevel.getLevel_name(), this.storeLevel.getLevel_id());
       showSuccessJson("修改店铺等级成功");
     } catch (Exception e) {
       showErrorJson("修改店铺等级失败");
       this.logger.error("修改点评等级失败:" + e);
     }
     return "json_message";
   }

   public IStoreLevelManager getStoreLevelManager() { return this.storeLevelManager; }

   public void setStoreLevelManager(IStoreLevelManager storeLevelManager) {
     this.storeLevelManager = storeLevelManager;
   }

   public StoreLevel getStoreLevel() { return this.storeLevel; }

   public void setStoreLevel(StoreLevel storeLevel) {
     this.storeLevel = storeLevel;
   }

   public String getLevel_name() { return this.level_name; }

   public void setLevel_name(String level_name) {
     this.level_name = level_name;
   }

   public Integer getLevel_id() { return this.level_id; }

   public void setLevel_id(Integer level_id) {
     this.level_id = level_id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\backend\store\StoreLevelAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */