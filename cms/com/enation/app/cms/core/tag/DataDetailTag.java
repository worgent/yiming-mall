 package com.enation.app.cms.core.tag;

 import com.enation.app.cms.core.service.IDataManager;
 import com.enation.eop.processor.core.UrlNotFoundException;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;












 @Component
 @Scope("prototype")
 public class DataDetailTag
   extends BaseFreeMarkerTag
 {
   protected IDataManager dataManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer catid = (Integer)params.get("catid");
     Integer articleid = (Integer)params.get("id");
     Map data = this.dataManager.get(articleid, catid, true);
     if (data == null) {
       throw new UrlNotFoundException();
     }
     return data;
   }

   public IDataManager getDataManager() { return this.dataManager; }

   public void setDataManager(IDataManager dataManager) {
     this.dataManager = dataManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\tag\DataDetailTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */