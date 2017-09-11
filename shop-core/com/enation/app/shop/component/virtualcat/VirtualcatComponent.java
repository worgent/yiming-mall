 package com.enation.app.shop.component.virtualcat;

 import com.enation.app.base.core.service.auth.IAuthActionManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.eop.resource.IMenuManager;
 import com.enation.eop.resource.model.Menu;
 import com.enation.framework.component.IComponent;
 import org.springframework.stereotype.Component;










 @Component
 public class VirtualcatComponent
   implements IComponent
 {
   private IMenuManager menuManager;
   private IAuthActionManager authActionManager;

   public void install()
   {
     int superAdminAuthId = PermissionConfig.getAuthId("super_admin");

     Menu parentMenu = this.menuManager.get("商品设置");

     Menu menu = new Menu();
     menu.setTitle("自定义分类");
     menu.setPid(parentMenu.getId());
     menu.setUrl("#");
     menu.setSorder(Integer.valueOf(55));
     menu.setMenutype(Integer.valueOf(2));
     int menuid = this.menuManager.add(menu).intValue();


     Menu listMenu = new Menu();
     listMenu.setPid(Integer.valueOf(menuid));
     listMenu.setTitle("虚拟分类列表");
     listMenu.setUrl("/shop/admin/virtual-cat!list.do");
     listMenu.setSorder(Integer.valueOf(1));
     listMenu.setMenutype(Integer.valueOf(2));
     int listmenuid = this.menuManager.add(listMenu).intValue();

     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/virtualcat/virtualcat_install.xml", "es_");
   }



   public void unInstall()
   {
     int superAdminAuthId = PermissionConfig.getAuthId("super_admin");
     int listmenuid = this.menuManager.get("虚拟分类列表").getId().intValue();

     this.authActionManager.deleteMenu(superAdminAuthId, new Integer[] { Integer.valueOf(listmenuid) });
     this.menuManager.delete("虚拟分类列表");
     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/virtualcat/virtualcat_uninstall.xml", "es_");
   }

   public IMenuManager getMenuManager() {
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


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\virtualcat\VirtualcatComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */