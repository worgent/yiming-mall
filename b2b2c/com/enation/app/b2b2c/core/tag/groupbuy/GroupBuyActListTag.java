 package com.enation.app.b2b2c.core.tag.groupbuy;

 import com.enation.app.b2b2c.core.model.groupbuy.GroupBuyActive;
 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyActiveManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;




 @Component
 @Scope("prototype")
 public class GroupBuyActListTag
   extends BaseFreeMarkerTag
 {
   private IGroupBuyActiveManager groupBuyActiveManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     List<GroupBuyActive> list = this.groupBuyActiveManager.listEnable();
     return list;
   }

   public IGroupBuyActiveManager getGroupBuyActiveManager() { return this.groupBuyActiveManager; }

   public void setGroupBuyActiveManager(IGroupBuyActiveManager groupBuyActiveManager)
   {
     this.groupBuyActiveManager = groupBuyActiveManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\groupbuy\GroupBuyActListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */