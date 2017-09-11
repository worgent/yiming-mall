 package com.enation.app.b2b2c.core.action.backend.store;

 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.model.store.Store;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.b2b2c.core.service.store.IStoreLevelManager;
 import com.enation.app.b2b2c.core.service.store.IStoreManager;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.StringUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.stereotype.Component;





















 @Component
 @ParentPackage("eop_default")
 @Namespace("/b2b2c/admin")
 @Results({
         @org.apache.struts2.convention.annotation.Result(name="store_list", type="freemarker", location="/b2b2c/admin/store/store_list.html"),
         @org.apache.struts2.convention.annotation.Result(name="audit_list", type="freemarker", location="/b2b2c/admin/store/audit_list.html"),
         @org.apache.struts2.convention.annotation.Result(name="license_list", type="freemarker", location="/b2b2c/admin/store/license_list.html"),
         @org.apache.struts2.convention.annotation.Result(name="disStore_list", type="freemarker", location="/b2b2c/admin/store/disStore_list.html"),
         @org.apache.struts2.convention.annotation.Result(name="edit", type="freemarker", location="/b2b2c/admin/store/store_edit.html"),
         @org.apache.struts2.convention.annotation.Result(name="add", type="freemarker", location="/b2b2c/admin/store/store_add.html"), @org.apache.struts2.convention.annotation.Result(name="opt", type="freemarker", location="/b2b2c/admin/store/opt_member.html"), @org.apache.struts2.convention.annotation.Result(name="pass", type="freemarker", location="/b2b2c/admin/store/pass.html"), @org.apache.struts2.convention.annotation.Result(name="auth_list", type="freemarker", location="/b2b2c/admin/store/auth_list.html")})
 @Action("store")
 public class StoreAction
   extends WWAction
 {
   private IStoreLevelManager storeLevelManager;
   private IStoreManager storeManager;
   private IStoreMemberManager storeMemberManager;
   private Map other;
   private Integer disabled;
   private Integer storeId;
   private Store store;
   private List level_list;
   private Integer member_id;
   private Integer pass;
   private Integer name_auth;
   private Integer store_auth;
   private String storeName;
   private String uname;
   private String password;
   private Integer assign_password;

   public String store_list()
   {
     return "store_list";
   }



   public String audit_list()
   {
     return "audit_list";
   }



   public String license_list()
   {
     return "license_list";
   }



   public String disStore_list()
   {
     return "disStore_list";
   }



   public String pass()
   {
     this.store = this.storeManager.getStore(this.storeId);
     if (this.store.getName_auth() == 2) {
       this.store.setId_img(UploadUtil.replacePath(this.store.getId_img()));
     }
     if (this.store.getStore_auth() == 2) {
       this.store.setLicense_img(UploadUtil.replacePath(this.store.getLicense_img()));
     }
     return "pass";
   }

   public String store_listJson() { this.other = new HashMap();
     this.other.put("disabled", this.disabled);
     this.other.put("name", this.storeName);
     showGridJson(this.storeManager.store_list(this.other, this.disabled, getPage(), getPageSize()));
     return "json_message";
   }


   public String audit_pass()
   {
     try
     {
       this.storeManager.audit_pass(this.member_id, this.storeId, this.pass, this.name_auth, this.store_auth);
       showSuccessJson("店铺审核通过");
     } catch (Exception e) {
       showErrorJson("审核失败");
       this.logger.error("店铺审核失败:" + e);
     }
     return "json_message";
   }


   public String disStore()
   {
     try
     {
       this.storeManager.disStore(this.storeId);
       showSuccessJson("店铺禁用成功");
     } catch (Exception e) {
       showErrorJson("店铺禁用失败");
       this.logger.error("店铺禁用失败:" + e);
     }
     return "json_message";
   }


   public String useStore()
   {
     try
     {
       this.storeManager.useStore(this.storeId);
       showSuccessJson("店铺恢复使用成功");
     } catch (Exception e) {
       showErrorJson("店铺恢复使用失败");
       this.logger.error("店铺恢复使用失败" + e);
     }
     return "json_message";
   }

   public String save() {
     this.store = assign();
     try {
       this.storeManager.save(this.store);
       showSuccessJson("保存成功！");
     } catch (Exception e) {
       showErrorJson("保存失败");
     }
     return "json_message";
   }

   public String edit()
   {
     this.store = this.storeManager.getStore(this.storeId);

     this.level_list = this.storeLevelManager.storeLevelList();

     return "edit";
   }

   public String saveEdit() {
     this.store = assign();
     this.store.setStore_id(this.storeId.intValue());
     try {
       this.storeManager.editStore(this.store);
       showSuccessJson("修改成功");
     } catch (Exception e) {
       showErrorJson("修改失败，请稍后重试！");
     }
     return "json_message";
   }

   private Store assign() {
     Store store = new Store();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();

     store.setMember_name(request.getParameter("member_name"));
     store.setId_number(request.getParameter("id_number"));
     store.setStore_name(request.getParameter("store_name"));
     store.setStore_provinceid(Integer.valueOf(request.getParameter("province_id")).intValue());
     store.setStore_cityid(Integer.valueOf(request.getParameter("city_id")).intValue());
     store.setStore_regionid(Integer.valueOf(request.getParameter("region_id")).intValue());
     store.setStore_province(request.getParameter("province"));
     store.setStore_city(request.getParameter("city"));
     store.setStore_region(request.getParameter("region"));
     store.setAttr(request.getParameter("attr"));
     store.setZip(request.getParameter("zip"));
     store.setTel(request.getParameter("tel"));
     store.setStore_level(Integer.valueOf(request.getParameter("store_level")).intValue());

     store.setDisabled(Integer.valueOf(request.getParameter("disabled")).intValue());
     store.setName_auth(Integer.valueOf(request.getParameter("name_auth")).intValue());
     store.setStore_auth(Integer.valueOf(request.getParameter("store_auth")).intValue());
     store.setStore_recommend(Integer.valueOf(request.getParameter("store_recommend")).intValue());


     return store;
   }






   public String opt() { return "opt"; }

   public String optMember() {
     try {
       StoreMember storeMember = this.storeMemberManager.getMember(this.uname);
       if ((this.assign_password != null) && (this.assign_password.intValue() == 1) &&
         (!storeMember.getPassword().equals(StringUtil.md5(this.password)))) {
         showErrorJson("密码不正确");
         return "json_message";
       }

       if (storeMember.getIs_store().intValue() == -1) {
         showSuccessJson("1");
       } else {
         showSuccessJson("2");
       }
     } catch (Exception e) {
       showErrorJson("没有此用户");
     }
     return "json_message";
   }

   public String add() {
     this.level_list = this.storeLevelManager.storeLevelList();
     return "add";
   }





   public String auth_list() { return "auth_list"; }

   public String auth_listJson() {
     showGridJson(this.storeManager.auth_list(this.other, this.disabled, getPage(), getPageSize()));
     return "json_message";
   }


   public String auth_pass()
   {
     try
     {
       this.storeManager.auth_pass(this.storeId, this.name_auth, this.store_auth);
       showSuccessJson("审核成功");
     } catch (Exception e) {
       showErrorJson("审核失败a");
     }
     return "json_message";
   }

   public IStoreLevelManager getStoreLevelManager()
   {
     return this.storeLevelManager;
   }

   public void setStoreLevelManager(IStoreLevelManager storeLevelManager) { this.storeLevelManager = storeLevelManager; }

   public IStoreManager getStoreManager() {
     return this.storeManager;
   }

   public void setStoreManager(IStoreManager storeManager) { this.storeManager = storeManager; }

   public Map getOther() {
     return this.other;
   }

   public void setOther(Map other) { this.other = other; }

   public Integer getDisabled() {
     return this.disabled;
   }

   public void setDisabled(Integer disabled) { this.disabled = disabled; }

   public Integer getStoreId() {
     return this.storeId;
   }

   public void setStoreId(Integer storeId) { this.storeId = storeId; }

   public Store getStore() {
     return this.store;
   }

   public void setStore(Store store) { this.store = store; }

   public List getLevel_list() {
     return this.level_list;
   }

   public void setLevel_list(List level_list) { this.level_list = level_list; }

   public Integer getMember_id() {
     return this.member_id;
   }

   public void setMember_id(Integer member_id) { this.member_id = member_id; }

   public Integer getPass() {
     return this.pass;
   }

   public void setPass(Integer pass) { this.pass = pass; }

   public Integer getName_auth() {
     return this.name_auth;
   }

   public void setName_auth(Integer name_auth) { this.name_auth = name_auth; }

   public Integer getStore_auth() {
     return this.store_auth;
   }

   public void setStore_auth(Integer store_auth) { this.store_auth = store_auth; }

   public String getStoreName() {
     return this.storeName;
   }

   public void setStoreName(String storeName) { this.storeName = storeName; }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) { this.storeMemberManager = storeMemberManager; }

   public String getUname() {
     return this.uname;
   }

   public void setUname(String uname) { this.uname = uname; }

   public String getPassword() {
     return this.password;
   }

   public void setPassword(String password) { this.password = password; }

   public Integer getAssign_password() {
     return this.assign_password;
   }

   public void setAssign_password(Integer assign_password) { this.assign_password = assign_password; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\backend\store\StoreAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */