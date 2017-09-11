 package com.enation.app.base.core.service.solution.impl;

 import com.enation.app.base.core.service.auth.IAdminUserManager;
 import com.enation.app.base.core.service.solution.IInstaller;
 import com.enation.eop.resource.IUserManager;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.resource.model.EopUser;
 import com.enation.eop.sdk.context.EopContext;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.framework.database.IDaoSupport;
 import org.w3c.dom.Node;

 public class AdminUserInstaller implements IInstaller
 {
   private IUserManager userManager;
   private IAdminUserManager adminUserManager;
   private IDaoSupport daoSupport;

   public void install(String productId, Node fragment)
   {
     if ("base".equals(productId)) {
       EopUser user = this.userManager.getCurrentUser();
       EopSite site = EopContext.getContext().getCurrentSite();
       int userid = site.getUserid().intValue();
       int siteid = site.getId().intValue();
       if (user != null)
       {

         AdminUser adminUser = new AdminUser();
         adminUser.setUsername(user.getUsername());
         adminUser.setPassword(user.getPassword());
         adminUser.setFounder(1);
         int adminUserId = this.adminUserManager.add(site.getUserid().intValue(), siteid, adminUser).intValue();

         if (EopSetting.RUNMODE.equals("2")) {
           this.daoSupport.execute("update es_adminuser_" + userid + "_" + siteid + " set password=? where userid=?", new Object[] { user.getPassword(), Integer.valueOf(adminUserId) });
         }
         else
         {
           this.daoSupport.execute("update es_adminuser set password=? where userid=?", new Object[] { user.getPassword(), Integer.valueOf(adminUserId) });
         }


         AdminUser chanpin = new AdminUser();
         chanpin.setUsername("chanpin");
         chanpin.setPassword("123456");
         chanpin.setState(1);
         chanpin.setRoleids(new int[] { 2 });
         this.adminUserManager.add(chanpin);


         AdminUser kuguan = new AdminUser();
         kuguan.setUsername("kuguan");
         kuguan.setPassword("123456");
         kuguan.setState(1);
         kuguan.setRoleids(new int[] { 3 });
         this.adminUserManager.add(kuguan);


         AdminUser caiwu = new AdminUser();
         caiwu.setUsername("caiwu");
         caiwu.setPassword("123456");
         caiwu.setState(1);
         caiwu.setRoleids(new int[] { 4 });
         this.adminUserManager.add(caiwu);


         AdminUser kefu = new AdminUser();
         kefu.setUsername("kefu");
         kefu.setPassword("123456");
         kefu.setState(1);
         kefu.setRoleids(new int[] { 5 });
         this.adminUserManager.add(kefu);
       }
       else
       {
         AdminUser adminUser = this.adminUserManager.getCurrentUser();
         String tablename = "es_adminuser";
         if (EopSetting.RUNMODE.equals("2")) {
           tablename = tablename + "_" + userid + "_" + siteid;
         }
         this.daoSupport.insert(tablename, adminUser);
         Integer adminuserid = adminUser.getUserid();


         if (EopSetting.RUNMODE.equals("2")) {
           this.daoSupport.execute("update es_adminuser_" + userid + "_" + siteid + " set password=? where userid=?", new Object[] { adminUser.getPassword(), adminuserid });
         }
         else
         {
           this.daoSupport.execute("update es_adminuser set password=? where userid=?", new Object[] { adminUser.getPassword(), Integer.valueOf(userid) });
         }
       }
     }
   }

   public IUserManager getUserManager() {
     return this.userManager;
   }

   public void setUserManager(IUserManager userManager) {
     this.userManager = userManager;
   }

   public IAdminUserManager getAdminUserManager() {
     return this.adminUserManager;
   }

   public void setAdminUserManager(IAdminUserManager adminUserManager) {
     this.adminUserManager = adminUserManager;
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\impl\AdminUserInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */