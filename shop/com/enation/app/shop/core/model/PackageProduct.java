 package com.enation.app.shop.core.model;

 import java.io.Serializable;

 public class PackageProduct
   implements Serializable
 {
   private int product_id;
   private int goods_id;
   private Double discount;
   private int pkgnum;

   public int getProduct_id()
   {
     return this.product_id;
   }

   public void setProduct_id(int productId) { this.product_id = productId; }

   public int getGoods_id() {
     return this.goods_id;
   }

   public void setGoods_id(int goodsId) { this.goods_id = goodsId; }

   public Double getDiscount() {
     return this.discount;
   }

   public void setDiscount(Double discount) { this.discount = discount; }

   public int getPkgnum() {
     return this.pkgnum;
   }

   public void setPkgnum(int pkgnum) { this.pkgnum = pkgnum; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\PackageProduct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */