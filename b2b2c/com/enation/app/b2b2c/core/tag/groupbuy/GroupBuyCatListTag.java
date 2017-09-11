 package com.enation.app.b2b2c.core.tag.groupbuy;

 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyCatManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;





 @Component
 @Scope("prototype")
 public class GroupBuyCatListTag
   extends BaseFreeMarkerTag
 {
   private IGroupBuyCatManager groupBuyCatManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     return this.groupBuyCatManager.listAll();
   }

   public IGroupBuyCatManager getGroupBuyCatManager() { return this.groupBuyCatManager; }

   public void setGroupBuyCatManager(IGroupBuyCatManager groupBuyCatManager) {
     this.groupBuyCatManager = groupBuyCatManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\groupbuy\GroupBuyCatListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */