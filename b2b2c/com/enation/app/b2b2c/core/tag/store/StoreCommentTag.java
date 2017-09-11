 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.service.member.IStoreMemberCommentManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;







 @Component
 public class StoreCommentTag
   extends BaseFreeMarkerTag
 {
   private IStoreMemberCommentManager storeMemberCommentManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     return this.storeMemberCommentManager.get(Integer.valueOf(Integer.parseInt(request.getParameter("comment_id"))));
   }

   public IStoreMemberCommentManager getStoreMemberCommentManager() { return this.storeMemberCommentManager; }

   public void setStoreMemberCommentManager(IStoreMemberCommentManager storeMemberCommentManager)
   {
     this.storeMemberCommentManager = storeMemberCommentManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreCommentTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */