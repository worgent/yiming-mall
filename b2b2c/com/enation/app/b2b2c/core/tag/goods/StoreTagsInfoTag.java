 package com.enation.app.b2b2c.core.tag.goods;

 import com.enation.app.shop.core.service.ITagManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;



 @Component
 public class StoreTagsInfoTag
   extends BaseFreeMarkerTag
 {
   private ITagManager tagManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     return this.tagManager.getById(Integer.valueOf(ThreadContextHolder.getHttpRequest().getParameter("tag_id").toString()));
   }

   public ITagManager getTagManager() { return this.tagManager; }

   public void setTagManager(ITagManager tagManager) {
     this.tagManager = tagManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\goods\StoreTagsInfoTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */