 package com.enation.app.shop.component.member.plugin.edit;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IAjaxExecuteEnable;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;




 @Component
 public class MemberEditorPlugin
   extends AutoRegisterPlugin
   implements IMemberTabShowEvent, IAjaxExecuteEnable
 {
   private IMemberManager memberManager;
   private IMemberLvManager memberLvManager;

   public String execute()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String action = request.getParameter("action");

     return null;
   }








   public String onShowMemberDetailHtml(Member member)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     List lvlist = this.memberLvManager.list();
     freeMarkerPaser.putData("memberid", member.getMember_id());
     freeMarkerPaser.putData("lvlist", lvlist);
     freeMarkerPaser.setPageName("editor");

     return freeMarkerPaser.proessPageContent();
   }




   public String getTabName(Member member)
   {
     return "编辑会员";
   }


   public int getOrder()
   {
     return 3;
   }


   public boolean canBeExecute(Member member)
   {
     return true;
   }

   public IMemberManager getMemberManager() {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\member\plugin\edit\MemberEditorPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */