 package com.enation.app.base.core.service.solution.impl;

 import com.enation.app.base.core.service.dbsolution.DBSolutionFactory;
 import com.enation.app.base.core.service.solution.IInstaller;
 import com.enation.eop.sdk.context.EopSetting;
 import org.w3c.dom.Node;














 public class AuthInstaller
   implements IInstaller
 {
   public void install(String productId, Node fragment)
   {
     if (!"base".equals(productId)) {
       String dataSqlPath = EopSetting.PRODUCTS_STORAGE_PATH + "/" + productId + "/auth.xml";
       if (!DBSolutionFactory.dbImport(dataSqlPath, "")) {
         throw new RuntimeException("安装权限数据出错...");
       }
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\service\solution\impl\AuthInstaller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */