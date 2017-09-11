 package com.enation.app.shop.core.tag.member;

 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;

 @Component
 public class MemberLvListTag
   extends BaseFreeMarkerTag
 {
   private IMemberLvManager memberLvManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     List list = this.memberLvManager.list();
     return list;
   }

   public IMemberLvManager getMemberLvManager() { return this.memberLvManager; }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\MemberLvListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */