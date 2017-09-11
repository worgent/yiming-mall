 package com.enation.app.shop.component.member.plugin.comments;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
 import com.enation.app.shop.core.service.IMemberCommentManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.database.Page;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.ArrayList;
 import java.util.List;
 import org.springframework.stereotype.Component;







 @Component
 public class MemberDiscussPlugin
   extends AutoRegisterPlugin
   implements IMemberTabShowEvent
 {
   private IMemberCommentManager memberCommentManager;

   public boolean canBeExecute(Member member)
   {
     return true;
   }

   public int getOrder()
   {
     return 19;
   }

   public String getTabName(Member member)
   {
     return "他的评论";
   }

   public String onShowMemberDetailHtml(Member member)
   {
     Page page = this.memberCommentManager.getMemberComments(1, 100, 1, member.getMember_id().intValue());
     List listComments = new ArrayList();
     if (page != null) {
       listComments = (List)page.getResult();
     }
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("listComments", listComments);
     freeMarkerPaser.setPageName("comments");
     return freeMarkerPaser.proessPageContent();
   }

   public IMemberCommentManager getMemberCommentManager() {
     return this.memberCommentManager;
   }

   public void setMemberCommentManager(IMemberCommentManager memberCommentManager) {
     this.memberCommentManager = memberCommentManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\member\plugin\comments\MemberDiscussPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */