 package com.enation.app.shop.component.member.plugin.advance;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.model.AdvanceLogs;
 import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
 import com.enation.app.shop.core.service.IAdvanceLogsManager;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.eop.resource.model.AdminUser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IAjaxExecuteEnable;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;






 @Component
 public class MemberEditAdvancePlugin
   extends AutoRegisterPlugin
   implements IMemberTabShowEvent, IAjaxExecuteEnable
 {
   private IMemberManager memberManager;
   private IAdvanceLogsManager advanceLogsManager;

   public boolean canBeExecute(Member member)
   {
     return true;
   }

   public int getOrder()
   {
     return 15;
   }

   public String getTabName(Member member)
   {
     return "他的预存款";
   }

   public String onShowMemberDetailHtml(Member member)
   {
     return doHtml(member.getMember_id().intValue());
   }

   protected String doHtml(int memberid) {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("memberid", Integer.valueOf(memberid));
     if (freeMarkerPaser.getData("member") == null) {
       freeMarkerPaser.putData("member", this.memberManager.get(Integer.valueOf(memberid)));
     }
     List listAdvanceLogs = this.advanceLogsManager.listAdvanceLogsByMemberId(memberid);
     freeMarkerPaser.putData("listAdvanceLogs", listAdvanceLogs);
     freeMarkerPaser.setPageName("advance");
     return freeMarkerPaser.proessPageContent();
   }

   protected String doUpdate(HttpServletRequest request) {
     double modify_advance = StringUtil.toDouble(request.getParameter("modify_advance"), true).doubleValue();
     String modify_memo = request.getParameter("modify_memo");
     int memberid = StringUtil.toInt(request.getParameter("memberid"), true);
     Member member = this.memberManager.get(Integer.valueOf(memberid));
     member.setAdvance(Double.valueOf(member.getAdvance().doubleValue() + modify_advance));
     AdvanceLogs advanceLogs = new AdvanceLogs();
     advanceLogs.setMember_id(memberid);
     advanceLogs.setDisabled("false");
     advanceLogs.setMtime(Long.valueOf(DateUtil.getDatelineLong()));
     advanceLogs.setImport_money(Double.valueOf(modify_advance));
     advanceLogs.setMember_advance(member.getAdvance());
     advanceLogs.setShop_advance(member.getAdvance());
     advanceLogs.setMoney(Double.valueOf(modify_advance));
     advanceLogs.setMessage(modify_memo);
     AdminUser user = (AdminUser)ThreadContextHolder.getSessionContext().getAttribute("admin_user_key");
     advanceLogs.setMemo(user.getUsername() + "代充值");
     try {
       this.memberManager.edit(member);
       this.advanceLogsManager.add(advanceLogs);
       return JsonMessageUtil.getSuccessJson("会员预存款修改成功");
     } catch (Exception e) {
       this.logger.error("会员预存款修改失败", e); }
     return JsonMessageUtil.getErrorJson("修改失败");
   }


   @Transactional(propagation=Propagation.REQUIRED)
   public String execute()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     if ("yes".equals(request.getParameter("showHtml"))) {
       int memberid = StringUtil.toInt(request.getParameter("memberid"), true);
       return doHtml(memberid);
     }
     return doUpdate(request);
   }

   public IMemberManager getMemberManager()
   {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public IAdvanceLogsManager getAdvanceLogsManager() {
     return this.advanceLogsManager;
   }

   public void setAdvanceLogsManager(IAdvanceLogsManager advanceLogsManager) {
     this.advanceLogsManager = advanceLogsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\member\plugin\advance\MemberEditAdvancePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */