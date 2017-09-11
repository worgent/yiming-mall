 package com.enation.app.shop.component.member.plugin.base;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.plugin.member.IMemberTabShowEvent;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import org.springframework.stereotype.Component;






 @Component
 public class MemberDetailBasePlugin
   extends AutoRegisterPlugin
   implements IMemberTabShowEvent
 {
   public String onShowMemberDetailHtml(Member member)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.putData("member", member);
     freeMarkerPaser.setPageName("base");
     return freeMarkerPaser.proessPageContent();
   }



   public String getTabName(Member member)
   {
     return "基本信息";
   }


   public int getOrder()
   {
     return 1;
   }


   public boolean canBeExecute(Member member)
   {
     return false;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\member\plugin\base\MemberDetailBasePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */