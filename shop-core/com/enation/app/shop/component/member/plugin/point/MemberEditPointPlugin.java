 package com.enation.app.shop.component.member.plugin.point;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.model.PointHistory;
 import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IPointHistoryManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IAjaxExecuteEnable;
 import com.enation.framework.util.DateUtil;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.log4j.Logger;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;





 @Component
 public class MemberEditPointPlugin
   extends AutoRegisterPlugin
   implements IMemberTabShowEvent, IAjaxExecuteEnable
 {
   private IMemberManager memberManager;
   private IPointHistoryManager pointHistoryManager;

   public boolean canBeExecute(Member member)
   {
     return true;
   }

   public int getOrder() {
     return 9;
   }

   public String getTabName(Member member) {
     return "他的积分";
   }

   public String onShowMemberDetailHtml(Member member) {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("memberid", member.getMember_id());
     freeMarkerPaser.setPageName("point");
     return freeMarkerPaser.proessPageContent();
   }

   @Transactional(propagation=Propagation.REQUIRED)
   public String execute() {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     int point = StringUtil.toInt(request.getParameter("modi_point"), true);
     int memberid = StringUtil.toInt(request.getParameter("memberid"), true);
     Member member = this.memberManager.get(Integer.valueOf(memberid));
     member.setPoint(Integer.valueOf(member.getPoint().intValue() + point));
     PointHistory pointHistory = new PointHistory();
     pointHistory.setMember_id(memberid);
     pointHistory.setOperator("管理员");
     pointHistory.setPoint(point);
     pointHistory.setReason("operator_adjust");
     pointHistory.setTime(Long.valueOf(DateUtil.getDatelineLong()));
     try {
       this.memberManager.edit(member);
       this.pointHistoryManager.addPointHistory(pointHistory);
       return JsonMessageUtil.getSuccessJson("会员积分修改成功");
     } catch (Exception e) {
       this.logger.error("会员积分修改失败", e); }
     return JsonMessageUtil.getErrorJson("修改失败");
   }

   public IMemberManager getMemberManager()
   {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager) {
     this.memberManager = memberManager;
   }

   public IPointHistoryManager getPointHistoryManager() {
     return this.pointHistoryManager;
   }

   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
     this.pointHistoryManager = pointHistoryManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\member\plugin\point\MemberEditPointPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */