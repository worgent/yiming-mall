 package com.enation.app.shop.core.tag.comment;

 import com.enation.app.shop.core.model.MemberComment;
 import com.enation.app.shop.core.service.impl.MemberCommentManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;

 @Component
 @Scope("prototype")
 public class CommentDetailTag extends BaseFreeMarkerTag
 {
   private MemberCommentManager memberCommentManager;

   protected Object exec(Map params) throws TemplateModelException
   {
     Integer conmmentid = (Integer)params.get("comment_id");
     MemberComment memberComment = this.memberCommentManager.get(conmmentid.intValue());
     return memberComment;
   }

   public MemberCommentManager getMemberCommentManager() { return this.memberCommentManager; }

   public void setMemberCommentManager(MemberCommentManager memberCommentManager) {
     this.memberCommentManager = memberCommentManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\comment\CommentDetailTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */