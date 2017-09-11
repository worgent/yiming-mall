 package com.enation.app.b2b2c.core.action.api.store;

 import com.enation.app.b2b2c.core.model.store.Store;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.store.IStoreManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import java.io.File;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.stereotype.Component;












 @Component
 @ParentPackage("eop_default")
 @Namespace("/api/b2b2c")
 @Action("storeApi")
 public class StoreApiAction
   extends WWAction
 {
   private Store store;
   private IStoreManager storeManager;
   private IStoreMemberManager storeMemberManager;
   private File id_img;
   private File license_img;
   private String id_imgFileName;
   private String license_imgFileName;
   private String id_number;
   private Integer province_id;
   private Integer city_id;
   private Integer region_id;
   private String fsid_img;
   private String fslicense_img;
   private String status_id_img;
   private String status_license_img;
   private String logo;
   private String storeName;
   private Integer store_id;
   private Integer store_auth;
   private Integer name_auth;

   public String checkStoreName()
   {
     if (this.storeManager.checkStoreName(this.storeName)) {
       showErrorJson("店铺名称重复");
     } else {
       showSuccessJson("店铺名称可以使用");
     }
     return "json_message";
   }





   public String apply()
   {
     try
     {
       if (null == this.storeMemberManager.getStoreMember()) {
         showErrorJson("您没有登录不能申请开店");
       } else if (!this.storeManager.checkStore())
       {
         getAddress(this.store);

         this.store.setStore_level(1);
         this.storeManager.apply(this.store);
         showSuccessJson("申请成功,请等待审核");
       } else {
         showErrorJson("您已经申请过了，请不要重复申请");
       }
     } catch (Exception e) {
       this.logger.error("申请失败:" + e);
       e.printStackTrace();
       showErrorJson("申请失败");
     }
     return "json_message";
   }




   private void getAddress(Store store)
   {
     store.setStore_provinceid(this.province_id.intValue());
     store.setStore_cityid(this.city_id.intValue());
     store.setStore_regionid(this.region_id.intValue());
   }




   public String edit()
   {
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       Map map = new HashMap();
       map.put("store_id", request.getParameter("store_id"));
       map.put("attr", request.getParameter("attr"));
       map.put("zip", request.getParameter("zip"));
       map.put("tel", request.getParameter("tel"));
       map.put("qq", request.getParameter("qq"));
       map.put("description", request.getParameter("description"));
       map.put("store_logo", request.getParameter("store_logo"));
       map.put("store_banner", request.getParameter("store_banner"));
       map.put("store_provinceid", this.province_id);
       map.put("store_city", this.city_id);
       map.put("store_region", this.region_id);
       this.storeManager.editStore(map);
       showSuccessJson("修改店铺信息成功");
     } catch (Exception e) {
       e.printStackTrace();
       showErrorJson("修改店铺信息失败");
       this.logger.error("修改店铺信息失败:" + e);
     }
     return "json_message";
   }





   public String checkIdNumber()
   {
     int result = this.storeManager.checkIdNumber(this.id_number).intValue();
     if (result == 0) {
       showSuccessJson("身份证可以使用！");
     } else {
       showErrorJson("身份证已经存在！");
     }
     return "json_message";
   }




   public String editStoreLogo()
   {
     try
     {
       this.storeManager.editStoreOnekey("store_logo", this.logo);
       showSuccessJson("店铺Logo修改成功");
     } catch (Exception e) {
       this.logger.error("修改店铺Logo失败:" + e);
       showErrorJson("店铺Logo修改失败");
     }
     return "json_message";
   }








   public String store_auth()
   {
     try
     {
       this.storeManager.saveStoreLicense(this.store_id, this.fsid_img, this.fslicense_img, this.store_auth, this.name_auth);
       showSuccessJson("提交成功，等待审核");
     } catch (Exception e) {
       showErrorJson("提交失败，请重试");
     }
     return "json_message";
   }

   public String getStoreName() { return this.storeName; }

   public void setStoreName(String storeName)
   {
     this.storeName = storeName;
   }

   public String getLogo() {
     return this.logo;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }

   public void setLogo(String logo) { this.logo = logo; }

   public Store getStore() {
     return this.store;
   }

   public void setStore(Store store) { this.store = store; }

   public IStoreManager getStoreManager() {
     return this.storeManager;
   }

   public void setStoreManager(IStoreManager storeManager) { this.storeManager = storeManager; }

   public File getId_img() {
     return this.id_img;
   }

   public void setId_img(File id_img) { this.id_img = id_img; }

   public File getLicense_img() {
     return this.license_img;
   }

   public void setLicense_img(File license_img) { this.license_img = license_img; }

   public String getId_imgFileName() {
     return this.id_imgFileName;
   }

   public void setId_imgFileName(String id_imgFileName) { this.id_imgFileName = id_imgFileName; }

   public String getLicense_imgFileName() {
     return this.license_imgFileName;
   }

   public void setLicense_imgFileName(String license_imgFileName) { this.license_imgFileName = license_imgFileName; }

   public Integer getProvince_id() {
     return this.province_id;
   }

   public void setProvince_id(Integer province_id) { this.province_id = province_id; }

   public Integer getCity_id() {
     return this.city_id;
   }

   public void setCity_id(Integer city_id) { this.city_id = city_id; }

   public Integer getRegion_id() {
     return this.region_id;
   }

   public void setRegion_id(Integer region_id) { this.region_id = region_id; }

   public String getId_number() {
     return this.id_number;
   }

   public void setId_number(String id_number) { this.id_number = id_number; }

   public String getFsid_img() {
     return this.fsid_img;
   }

   public void setFsid_img(String fsid_img) { this.fsid_img = fsid_img; }

   public String getFslicense_img() {
     return this.fslicense_img;
   }

   public void setFslicense_img(String fslicense_img) { this.fslicense_img = fslicense_img; }

   public String getStatus_id_img() {
     return this.status_id_img;
   }

   public void setStatus_id_img(String status_id_img) { this.status_id_img = status_id_img; }

   public String getStatus_license_img() {
     return this.status_license_img;
   }

   public void setStatus_license_img(String status_license_img) { this.status_license_img = status_license_img; }

   public Integer getStore_id()
   {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) {
     this.store_id = store_id;
   }

   public Integer getStore_auth() {
     return this.store_auth;
   }

   public void setStore_auth(Integer store_auth) {
     this.store_auth = store_auth;
   }

   public Integer getName_auth() {
     return this.name_auth;
   }

   public void setName_auth(Integer name_auth) {
     this.name_auth = name_auth;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\store\StoreApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */