 package com.enation.app.shop.component.member.plugin.point;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
 import com.enation.app.shop.core.service.IPointHistoryManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.List;
 import org.springframework.stereotype.Component;







 @Component
 public class MemberMkPointHistoryPlugin
   extends AutoRegisterPlugin
   implements IMemberTabShowEvent
 {
   private IPointHistoryManager pointHistoryManager;

   public boolean canBeExecute(Member member)
   {
     return true;
   }

   public int getOrder()
   {
     return 13;
   }

   public String getTabName(Member member)
   {
     return "消费积分";
   }

   public String onShowMemberDetailHtml(Member member)
   {
     List listPointHistory = this.pointHistoryManager.listPointHistory(member.getMember_id().intValue(), 1);
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("listPointHistory", listPointHistory);
     freeMarkerPaser.setPageName("point_history");
     return freeMarkerPaser.proessPageContent();
   }

   public IPointHistoryManager getPointHistoryManager() {
     return this.pointHistoryManager;
   }

   public void setPointHistoryManager(IPointHistoryManager pointHistoryManager) {
     this.pointHistoryManager = pointHistoryManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\member\plugin\point\MemberMkPointHistoryPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */