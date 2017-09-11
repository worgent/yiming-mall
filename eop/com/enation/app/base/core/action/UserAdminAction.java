 package com.enation.app.base.core.action;

 import com.enation.app.base.core.plugin.user.AdminUserPluginBundle;
 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.auth.IPermissionManager;
 import com.enation.app.base.core.service.auth.IRoleManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.action.WWAction;
 import java.util.List;
 import org.apache.log4j.Logger;




 public class UserAdminAction
   extends WWAction
 {
   private AdminUserPluginBundle adminUserPluginBundle;
   private IAdminUserManager adminUserManager;
   private IRoleManager roleManager;
   private IPermissionManager permissionManager;
   private AdminUser adminUser;
   private Integer id;
   private List roleList;
   private List userRoles;
   private int[] roleids;
   private List userList;
   private String newPassword;
   private String updatePwd;
   private int multiSite;
   private List<String> htmlList;

   public String list()
   {
     this.userList = this.adminUserManager.list();
     return "success";
   }



   public String listJson()
   {
     this.userList = this.adminUserManager.list();
     showGridJson(this.userList);
     return "json_message";
   }

   public String add() throws Exception
   {
     this.multiSite = EopContext.getContext().getCurrentSite().getMulti_site().intValue();
     this.roleList = this.roleManager.list();
     this.htmlList = this.adminUserPluginBundle.getInputHtml(null);
     return "add";
   }

   public String addSave() throws Exception {
     try {
       this.adminUser.setRoleids(this.roleids);
       this.adminUserManager.add(this.adminUser);
       showSuccessJson("新增管理员成功");
     } catch (RuntimeException e) {
       showErrorJson("新增管理员失败");
       this.logger.error("新增管理员失败", e);
     }
     return "json_message";
   }

   public String edit() throws Exception
   {
     this.multiSite = EopContext.getContext().getCurrentSite().getMulti_site().intValue();
     this.roleList = this.roleManager.list();
     this.userRoles = this.permissionManager.getUserRoles(this.id.intValue());
     this.adminUser = this.adminUserManager.get(this.id);
     this.htmlList = this.adminUserPluginBundle.getInputHtml(this.adminUser);
     return "edit";
   }

   public String editSave() throws Exception {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try {
       if (this.updatePwd != null) {
         this.adminUser.setPassword(this.newPassword);
       }
       this.adminUser.setRoleids(this.roleids);
       this.adminUserManager.edit(this.adminUser);
       showSuccessJson("修改管理员成功");
     } catch (RuntimeException e) {
       e.printStackTrace();
       this.logger.error(e, e.fillInStackTrace());
       showErrorJson("修改管理员失败");
     }

     return "json_message";
   }

   public String delete()
     throws Exception
   {
     if (EopSetting.IS_DEMO_SITE) {
       showErrorJson("抱歉，当前为演示站点，以不能修改这些示例数据，请下载安装包在本地体验这些功能！");
       return "json_message";
     }
     try
     {
       this.adminUserManager.delete(this.id);
       showSuccessJson("管理员删除成功");
     } catch (RuntimeException e) {
       showErrorJson("管理员删除失败");
       this.logger.error("管理员删除失败", e);
     }

     return "json_message";
   }

   public String editPassword() throws Exception {
     return "editPassword";
   }


   public IAdminUserManager getAdminUserManager()
   {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public IRoleManager getRoleManager() {
     return this.roleManager;
   }

   public void setRoleManager(IRoleManager roleManager) {
     this.roleManager = roleManager;
   }

   public IPermissionManager getPermissionManager() {
     return this.permissionManager;
   }

   public void setPermissionManager(IPermissionManager permissionManager) {
     this.permissionManager = permissionManager;
   }

   public AdminUser getAdminUser() {
     return this.adminUser;
   }

   public void setAdminUser(AdminUser adminUser) {
     this.adminUser = adminUser;
   }

   public Integer getId() {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public List getRoleList() {
     return this.roleList;
   }

   public void setRoleList(List roleList) {
     this.roleList = roleList;
   }

   public List getUserRoles() {
     return this.userRoles;
   }

   public void setUserRoles(List userRoles) {
     this.userRoles = userRoles;
   }

   public int[] getRoleids() {
     return this.roleids;
   }

   public void setRoleids(int[] roleids) {
     this.roleids = roleids;
   }

   public List getUserList() {
     return this.userList;
   }

   public void setUserList(List userList) {
     this.userList = userList;
   }

   public String getNewPassword() {
     return this.newPassword;
   }

   public void setNewPassword(String newPassword) {
     this.newPassword = newPassword;
   }

   public String getUpdatePwd() {
     return this.updatePwd;
   }

   public void setUpdatePwd(String updatePwd) {
     this.updatePwd = updatePwd;
   }

   public int getMultiSite() {
     return this.multiSite;
   }

   public void setMultiSite(int multiSite) {
     this.multiSite = multiSite;
   }

   public AdminUserPluginBundle getAdminUserPluginBundle() {
     return this.adminUserPluginBundle;
   }

   public void setAdminUserPluginBundle(AdminUserPluginBundle adminUserPluginBundle) {
     this.adminUserPluginBundle = adminUserPluginBundle;
   }

   public List<String> getHtmlList() {
     return this.htmlList;
   }

   public void setHtmlList(List<String> htmlList) {
     this.htmlList = htmlList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\action\UserAdminAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */