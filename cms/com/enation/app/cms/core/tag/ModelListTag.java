 package com.enation.app.cms.core.tag;

 import com.enation.app.cms.core.service.IDataModelManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;






 @Component
 @Scope("prototype")
 public class ModelListTag
   extends BaseFreeMarkerTag
 {
   private IDataModelManager dataModelManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     return this.dataModelManager.list();
   }

   public IDataModelManager getDataModelManager() {
     return this.dataModelManager;
   }

   public void setDataModelManager(IDataModelManager dataModelManager) {
     this.dataModelManager = dataModelManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\tag\ModelListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */