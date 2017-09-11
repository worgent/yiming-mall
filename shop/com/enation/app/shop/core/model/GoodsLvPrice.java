 package com.enation.app.shop.core.model;


 public class GoodsLvPrice
 {
   private Double price;

   private int lvid;

   private int productid;
   private int goodsid;

   public Double getPrice()
   {
     return this.price;
   }

   public void setPrice(Double price) { this.price = price; }

   public int getLvid() {
     return this.lvid;
   }

   public void setLvid(int lvid) { this.lvid = lvid; }

   public int getProductid() {
     return this.productid;
   }

   public void setProductid(int productid) { this.productid = productid; }

   public int getGoodsid() {
     return this.goodsid;
   }

   public void setGoodsid(int goodsid) { this.goodsid = goodsid; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\GoodsLvPrice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */