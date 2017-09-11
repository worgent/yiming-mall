 package com.enation.app.shop.component.receipt;

 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.framework.component.IComponent;
 import org.springframework.stereotype.Component;






 @Component
 public class ReceiptComponent
   implements IComponent
 {
   public void install()
   {
     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/receipt/receipt_install.xml", "es_");
   }

   public void unInstall()
   {
     DBSolutionFactory.dbImport("file:com/enation/app/shop/component/receipt/receipt_uninstall.xml", "es_");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\receipt\ReceiptComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */