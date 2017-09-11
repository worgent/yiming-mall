 package com.enation.app.shop.component.gallery.action;

 import com.enation.app.shop.component.gallery.service.IGoodsGalleryManager;
 import com.enation.framework.action.WWAction;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;







 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Results({@org.apache.struts2.convention.annotation.Result(name="input", type="freemarker", location="/com/enation/app/shop/component/gallery/action/gallery.html")})
 @Action
 public class GalleryRecreateAction
   extends WWAction
 {
   private IGoodsGalleryManager goodsGalleryManager;
   private int total;
   private int start;
   private int end;

   public int getStart()
   {
     return this.start;
   }

   public void setStart(int start) {
     this.start = start;
   }

   public int getEnd() {
     return this.end;
   }

   public void setEnd(int end) {
     this.end = end;
   }

   public int getTotal() {
     return this.total;
   }

   public void setTotal(int total) {
     this.total = total;
   }

   public String recreate() {
     try {
       this.goodsGalleryManager.recreate(this.start, this.end);
       showSuccessJson("生成商品相册缩略图成功 ");
     } catch (RuntimeException e) {
       this.logger.error("生成商品相册缩略图错误", e);
       showErrorJson("生成商品相册缩略图错误" + e.getMessage());
     }
     return "json_message";
   }

   public String execute() {
     this.total = this.goodsGalleryManager.getTotal();
     return "input";
   }

   public IGoodsGalleryManager getGoodsGalleryManager() {
     return this.goodsGalleryManager;
   }

   public void setGoodsGalleryManager(IGoodsGalleryManager goodsGalleryManager) {
     this.goodsGalleryManager = goodsGalleryManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\action\GalleryRecreateAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */