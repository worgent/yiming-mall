 package com.enation.app.shop.component.bonus.tag;

 import com.enation.app.shop.component.bonus.model.MemberBonus;
 import com.enation.app.shop.component.bonus.service.BonusSession;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;















 @Component
 @Scope("prototype")
 public class MemberUsedBonusListTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map arg0)
     throws TemplateModelException
   {
     List<MemberBonus> bonusList = BonusSession.get();
     bonusList = bonusList == null ? new ArrayList() : bonusList;

     return bonusList;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\tag\MemberUsedBonusListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */