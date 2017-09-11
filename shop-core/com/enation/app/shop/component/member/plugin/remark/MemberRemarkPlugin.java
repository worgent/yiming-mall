 package com.enation.app.shop.component.member.plugin.remark;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IAjaxExecuteEnable;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.springframework.stereotype.Component;









 @Component
 public class MemberRemarkPlugin
   extends AutoRegisterPlugin
   implements IMemberTabShowEvent, IAjaxExecuteEnable
 {
   private IMemberManager memberManager;

   public String execute()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String modify_memo = request.getParameter("modify_memo");
     int memberid = StringUtil.toInt(request.getParameter("memberid"), true);
     Member member = this.memberManager.get(Integer.valueOf(memberid));
     member.setRemark(modify_memo);
     try {
       this.memberManager.edit(member);
       return JsonMessageUtil.getSuccessJson("会员备注修改成功");
     } catch (Exception e) {
       this.logger.error("修改会员备注", e); }
     return JsonMessageUtil.getErrorJson("会员备注修改失败");
   }









   public String getTabName(Member member)
   {
     return "会员备注";
   }






   public int getOrder()
   {
     return 27;
   }






   public boolean canBeExecute(Member member)
   {
     return true;
   }







   public String onShowMemberDetailHtml(Member member)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("memberid", member.getMember_id());
     freeMarkerPaser.setPageName("remark");
     return freeMarkerPaser.proessPageContent();
   }




   public IMemberManager getMemberManager()
   {
     return this.memberManager;
   }



   public void setMemberManager(IMemberManager memberManager)
   {
     this.memberManager = memberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\member\plugin\remark\MemberRemarkPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */