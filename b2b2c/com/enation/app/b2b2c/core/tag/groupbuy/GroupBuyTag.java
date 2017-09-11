 package com.enation.app.b2b2c.core.tag.groupbuy;

 import com.enation.app.b2b2c.core.service.groupbuy.IGroupBuyManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;














 @Component
 @Scope("prototype")
 public class GroupBuyTag
   extends BaseFreeMarkerTag
 {
   private IGroupBuyManager groupBuyManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer gbid = (Integer)params.get("gbid");
     if (gbid == null) { throw new RuntimeException("gbid参数必须传递");
     }
     return this.groupBuyManager.get(gbid.intValue());
   }


   public IGroupBuyManager getGroupBuyManager()
   {
     return this.groupBuyManager;
   }

   public void setGroupBuyManager(IGroupBuyManager groupBuyManager) { this.groupBuyManager = groupBuyManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\groupbuy\GroupBuyTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */