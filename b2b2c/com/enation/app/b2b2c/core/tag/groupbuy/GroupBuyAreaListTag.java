 package com.enation.app.b2b2c.core.tag.groupbuy;

 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyAreaManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;





 @Component
 @Scope("prototype")
 public class GroupBuyAreaListTag
   extends BaseFreeMarkerTag
 {
   private IGroupBuyAreaManager groupBuyAreaManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     return this.groupBuyAreaManager.listAll();
   }

   public IGroupBuyAreaManager getGroupBuyAreaManager() { return this.groupBuyAreaManager; }

   public void setGroupBuyAreaManager(IGroupBuyAreaManager groupBuyAreaManager) {
     this.groupBuyAreaManager = groupBuyAreaManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\groupbuy\GroupBuyAreaListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */