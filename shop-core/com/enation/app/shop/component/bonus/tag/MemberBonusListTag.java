 package com.enation.app.shop.component.bonus.tag;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.component.bonus.model.MemberBonus;
 import com.enation.app.shop.component.bonus.service.BonusSession;
 import com.enation.app.shop.component.bonus.service.IBonusManager;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;













 @Component
 @Scope("prototype")
 public class MemberBonusListTag
   extends BaseFreeMarkerTag
 {
   private IBonusManager bonusManager;
   private ICartManager cartManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     try
     {
       Integer type = (Integer)params.get("type");
       Member member = UserServiceFactory.getUserService().getCurrentMember();
       if (member == null) {
         return "未登陆，不能使用此api";
       }
       Double goodsprice = this.cartManager.countGoodsTotal(getRequest().getSession().getId());
       if (type.intValue() == 1) {
         goodsprice = Double.valueOf(9.9999999E7D);
       }
       List<Map> bonusList = this.bonusManager.getMemberBonusList(member.getMember_id().intValue(), goodsprice);

       MemberBonus useBonus = BonusSession.getOne();
       if (useBonus != null) {
         for (Map bonus : bonusList) {
           Integer bonusid = (Integer)bonus.get("bonus_id");
           if (bonusid.intValue() == useBonus.getBonus_id()) {
             bonus.put("used", Integer.valueOf(1));
           } else {
             bonus.put("used", Integer.valueOf(0));
           }
         }
       }

       return bonusList;
     } catch (Exception e) {
       throw new TemplateModelException(e);
     }
   }

   public IBonusManager getBonusManager() {
     return this.bonusManager;
   }

   public void setBonusManager(IBonusManager bonusManager) {
     this.bonusManager = bonusManager;
   }

   public ICartManager getCartManager() {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) {
     this.cartManager = cartManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\tag\MemberBonusListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */