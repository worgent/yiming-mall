 package com.enation.app.shop.component.bonus.tag;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.component.bonus.service.impl.BonusManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import com.enation.framework.util.StringUtil;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;

 @Component
 @Scope("prototype")
 public class MyCouponTag
   extends BaseFreeMarkerTag
 {
   private BonusManager bonusManager;

   protected Object exec(Map params) throws TemplateModelException
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     int pagenum = StringUtil.toInt(request.getParameter("page"), Integer.valueOf(1)).intValue();
     Page pages = this.bonusManager.pageList(pagenum, 10, member.getMember_id().intValue());
     return pages;
   }

   public BonusManager getBonusManager() { return this.bonusManager; }

   public void setBonusManager(BonusManager bonusManager) {
     this.bonusManager = bonusManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\tag\MyCouponTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */