 package com.enation.app.cms.core.action;

 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.framework.action.WWAction;
 import java.util.Map;





 public class CmsIndexItemAction
   extends WWAction
 {
   private IDataManager dataManager;
   private Map datass;

   public String article()
   {
     this.datass = this.dataManager.census();
     return "article";
   }

   public IDataManager getDataManager() { return this.dataManager; }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }

   public Map getDatass() { return this.datass; }

   public void setDatass(Map datass) {
     this.datass = datass;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\action\CmsIndexItemAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */