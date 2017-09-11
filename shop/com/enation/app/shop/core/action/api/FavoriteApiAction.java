 package com.enation.app.shop.core.action.api;

 import com.enation.app.shop.core.service.IFavoriteManager;
 import com.enation.framework.action.WWAction;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;









 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("favorite")
 public class FavoriteApiAction
   extends WWAction
 {
   private IFavoriteManager favoriteManager;
   private Integer favorite_id;

   public String delete()
   {
     try
     {
       this.favoriteManager.delete(this.favorite_id.intValue());
       showSuccessJson("删除成功");
     } catch (Exception e) {
       if (this.logger.isDebugEnabled()) {
         this.logger.error(e.getStackTrace());
       }
       showErrorJson("删除失败[" + e.getMessage() + "]");
     }
     return "json_message";
   }

   public IFavoriteManager getFavoriteManager() {
     return this.favoriteManager;
   }

   public void setFavoriteManager(IFavoriteManager favoriteManager) {
     this.favoriteManager = favoriteManager;
   }

   public Integer getFavorite_id() {
     return this.favorite_id;
   }

   public void setFavorite_id(Integer favorite_id) {
     this.favorite_id = favorite_id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\FavoriteApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */