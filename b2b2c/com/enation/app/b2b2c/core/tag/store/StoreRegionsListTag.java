 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.service.IStoreRegionsManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;









 @Component
 public class StoreRegionsListTag
   extends BaseFreeMarkerTag
 {
   private IStoreRegionsManager storeRegionsManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     List list = this.storeRegionsManager.getRegionsToAreaList();
     return list;
   }

   public IStoreRegionsManager getStoreRegionsManager() {
     return this.storeRegionsManager;
   }

   public void setStoreRegionsManager(IStoreRegionsManager storeRegionsManager) {
     this.storeRegionsManager = storeRegionsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreRegionsListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */