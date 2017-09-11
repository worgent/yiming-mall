 package com.enation.app.b2b2c.core.tag.storesite;

 import com.enation.app.b2b2c.core.model.Navigation;
 import com.enation.app.b2b2c.core.service.INavigationManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.stereotype.Component;

 @Component
 public class NavigationTag
   extends BaseFreeMarkerTag
 {
   private INavigationManager navigationManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer nav_id = (Integer)params.get("nav_id");
     Navigation navigation = this.navigationManager.getNavication(nav_id);
     return navigation;
   }

   public INavigationManager getNavigationManager() { return this.navigationManager; }

   public void setNavigationManager(INavigationManager navigationManager) {
     this.navigationManager = navigationManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\storesite\NavigationTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */