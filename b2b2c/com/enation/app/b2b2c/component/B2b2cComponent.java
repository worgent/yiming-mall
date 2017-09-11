 package com.enation.app.b2b2c.component;

 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.framework.component.IComponent;
 import org.springframework.stereotype.Component;



 @Component
 public class B2b2cComponent
   implements IComponent
 {
   public void install()
   {
     DBSolutionFactory.dbImport("file:com/enation/app/b2b2c/component/b2b2c_install.xml", "es_");
   }

   public void unInstall()
   {
     DBSolutionFactory.dbImport("file:com/enation/app/b2b2c/component/b2b2c_uninstall.xml", "es_");
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\B2b2cComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */