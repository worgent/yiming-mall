 package com.enation.app.shop.core.tag.member;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IFavoriteManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.database.Page;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;










 @Component
 @Scope("prototype")
 public class CollectTag
   extends BaseFreeMarkerTag
 {
   private IFavoriteManager favoriteManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Member memberLogin = UserServiceFactory.getUserService().getCurrentMember();
     if (memberLogin == null) {
       throw new TemplateModelException("未登录不能使用此标签");
     }
     Page page = this.favoriteManager.list(memberLogin.getMember_id().intValue(), getPage(), getPageSize());
     return page;
   }

   public IFavoriteManager getFavoriteManager() {
     return this.favoriteManager;
   }

   public void setFavoriteManager(IFavoriteManager favoriteManager) {
     this.favoriteManager = favoriteManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\member\CollectTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */