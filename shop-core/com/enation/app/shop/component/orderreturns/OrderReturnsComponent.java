 package com.enation.app.shop.component.orderreturns;

 import com.enation.app.base.core.service.auth.IAuthActionManager;
 import com.enation.app.base.core.service.auth.impl.PermissionConfig;
 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.eop.resource.IMenuManager;
 import com.enation.eop.resource.model.Menu;
 import com.enation.framework.component.IComponent;
 import org.springframework.stereotype.Component;







 @Component
 public class OrderReturnsComponent
   implements IComponent
 {
   private IMenuManager menuManager;
   private IAuthActionManager authActionManager;

   public void install()
   {
     int superAdminAuthId = PermissionConfig.getAuthId("super_admin");
     int orderAuthId = PermissionConfig.getAuthId("order");

     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/orderreturns/orderreturns_install.xml", "es_");
   }



   public void unInstall()
   {
     int superAdminAuthId = PermissionConfig.getAuthId("super_admin");
     int orderAuthId = PermissionConfig.getAuthId("order");

     int menuid = this.menuManager.get("退货申请").getId().intValue();
     this.authActionManager.deleteMenu(superAdminAuthId, new Integer[] { Integer.valueOf(menuid) });
     this.authActionManager.deleteMenu(orderAuthId, new Integer[] { Integer.valueOf(menuid) });

     this.menuManager.delete("退货申请");
     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/orderreturns/orderreturns_uninstall.xml", "es_");
   }

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


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\orderreturns\OrderReturnsComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */