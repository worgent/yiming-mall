 package com.enation.app.shop.core.model.support;

 import com.enation.app.shop.core.model.Goods;

 public class GoodsDTO {
   private Goods goods;
   private String[] photos;

   public Goods getGoods() { return this.goods; }

   public void setGoods(Goods goods) {
     this.goods = goods;
   }

   public String[] getPhotos() { return this.photos; }

   public void setPhotos(String[] photos) {
     this.photos = photos;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\support\GoodsDTO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */