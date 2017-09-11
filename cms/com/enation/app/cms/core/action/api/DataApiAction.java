 package com.enation.app.cms.core.action.api;

 import com.enation.app.cms.core.service.IDataFieldManager;
 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.framework.action.WWAction;
 import net.sf.json.JSONArray;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;








 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/cms")
 @Action("data")
 public class DataApiAction
   extends WWAction
 {
   private IDataManager dataManager;
   private IDataFieldManager dataFieldManager;
   private Integer modelid;

   public String search()
   {
     this.json = JSONArray.fromObject(this.dataManager.search(this.modelid.intValue(), null)).toString();
     return "json_message";
   }

   public String fields() {
     this.json = JSONArray.fromObject(this.dataFieldManager.list(this.modelid)).toString();
     return "json_message";
   }

   public IDataManager getDataManager() {
     return this.dataManager;
   }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }

   public Integer getModelid() {
     return this.modelid;
   }

   public void setModelid(Integer modelid) {
     this.modelid = modelid;
   }

   public IDataFieldManager getDataFieldManager() {
     return this.dataFieldManager;
   }

   public void setDataFieldManager(IDataFieldManager dataFieldManager) {
     this.dataFieldManager = dataFieldManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\action\api\DataApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */