 package com.enation.app.base.core.tag;

 import com.enation.app.base.core.service.ISiteMenuManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component
 @Scope("prototype")
 public class MenuTag
   extends BaseFreeMarkerTag
 {
   private ISiteMenuManager siteMenuManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     String parentid = (String)params.get("parentid");
     if (parentid == null)
       parentid = "0";
     List menuList = this.siteMenuManager.list(Integer.valueOf(parentid));
     return menuList;
   }

   public ISiteMenuManager getSiteMenuManager() { return this.siteMenuManager; }

   public void setSiteMenuManager(ISiteMenuManager siteMenuManager) {
     this.siteMenuManager = siteMenuManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\tag\MenuTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */