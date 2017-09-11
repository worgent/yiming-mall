 package com.enation.app.shop.component.gallery.model;

 import com.enation.framework.database.PrimaryKeyField;

 public class GoodsGallery
 {
   private int img_id;
   private int goods_id;
   private String thumbnail;
   private String small;
   private String big;
   private String original;
   private String tiny;
   private int isdefault;

   @PrimaryKeyField
   public int getImg_id() {
     return this.img_id;
   }

   public void setImg_id(int img_id) { this.img_id = img_id; }

   public int getGoods_id() {
     return this.goods_id;
   }

   public void setGoods_id(int goods_id) { this.goods_id = goods_id; }

   public String getThumbnail() {
     return this.thumbnail;
   }

   public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

   public String getSmall() {
     return this.small;
   }

   public void setSmall(String small) { this.small = small; }

   public String getBig() {
     return this.big;
   }

   public void setBig(String big) { this.big = big; }

   public String getOriginal() {
     return this.original;
   }

   public void setOriginal(String original) { this.original = original; }

   public String getTiny() {
     return this.tiny;
   }

   public void setTiny(String tiny) { this.tiny = tiny; }

   public int getIsdefault() {
     return this.isdefault;
   }

   public void setIsdefault(int isdefault) { this.isdefault = isdefault; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\model\GoodsGallery.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */