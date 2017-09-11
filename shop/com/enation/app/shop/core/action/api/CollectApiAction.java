 package com.enation.app.shop.core.action.api;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.service.IFavoriteManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.action.WWAction;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;

















 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("collect")
 public class CollectApiAction
   extends WWAction
 {
   private IFavoriteManager favoriteManager;
   private Integer goods_id;
   private Integer favorite_id;

   public String addCollect()
   {
     Member memberLogin = UserServiceFactory.getUserService().getCurrentMember();
     if (memberLogin != null) {
       int count = this.favoriteManager.getCount(getGoods_id(), memberLogin.getMember_id());
       if (count == 0) {
         this.favoriteManager.add(getGoods_id());
         showSuccessJson("添加收藏成功");
       } else {
         showErrorJson("已收藏该商品");
       }
     } else {
       showErrorJson("请您重新登录");
     }
     return "json_message";
   }











   public String cancelCollect()
   {
     this.favoriteManager.delete(getFavorite_id().intValue());
     showSuccessJson("取消成功");
     return "json_message";
   }




   public Integer getGoods_id()
   {
     return this.goods_id;
   }

   public IFavoriteManager getFavoriteManager() {
     return this.favoriteManager;
   }

   public void setFavoriteManager(IFavoriteManager favoriteManager)
   {
     this.favoriteManager = favoriteManager;
   }

   public void setGoods_id(Integer goods_id)
   {
     this.goods_id = goods_id;
   }

   public Integer getFavorite_id() {
     return this.favorite_id;
   }

   public void setFavorite_id(Integer favorite_id) {
     this.favorite_id = favorite_id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\CollectApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */