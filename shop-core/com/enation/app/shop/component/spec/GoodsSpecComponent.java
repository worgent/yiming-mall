 package com.enation.app.shop.component.spec;

 import com.enation.app.base.core.service.auth.IAuthActionManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.eop.resource.IMenuManager;
 import com.enation.eop.resource.model.Menu;
 import com.enation.framework.component.IComponent;
 import org.springframework.stereotype.Component;






 @Component
 public class GoodsSpecComponent
   implements IComponent
 {
   private final String parentMenuName = "设置";

   private IMenuManager menuManager;
   private IAuthActionManager authActionManager;

   public void install()
   {
     installMenu();
     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/spec/spec_install.xml", "es_");
   }

   public void unInstall()
   {
     unInstallMenu();
     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/spec/spec_uninstall.xml", "es_");
   }

   private void unInstallMenu() {
     int superAdminAuthId = PermissionConfig.getAuthId("super_admin");
     int addmenuid = this.menuManager.get("添加规格").getId().intValue();
     int listmenuid = this.menuManager.get("规格列表").getId().intValue();
     int menuid = this.menuManager.get("规格管理").getId().intValue();

     this.authActionManager.deleteMenu(superAdminAuthId, new Integer[] { Integer.valueOf(menuid), Integer.valueOf(listmenuid), Integer.valueOf(addmenuid) });

     this.menuManager.delete("添加规格");
     this.menuManager.delete("规格列表");
     this.menuManager.delete("规格管理");
   }
















   private void installMenu() {}
















   public IMenuManager getMenuManager()
   {
     return this.menuManager;
   }

   public void setMenuManager(IMenuManager menuManager) {
     this.menuManager = menuManager;
   }

   public IAuthActionManager getAuthActionManager() {
     return this.authActionManager;
   }

   public void setAuthActionManager(IAuthActionManager authActionManager) {
     this.authActionManager = authActionManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\GoodsSpecComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */