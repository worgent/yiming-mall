 package com.enation.app.shop.core.model;

 public class LimitBuyGoods {
   private Integer limitbuyid;
   private Integer goodsid;
   private Double price;

   public Integer getGoodsid() { return this.goodsid; }

   public void setGoodsid(Integer goodsid) {
     this.goodsid = goodsid;
   }

   public Double getPrice() { return this.price; }

   public void setPrice(Double price) {
     this.price = price;
   }

   public Integer getLimitbuyid() { return this.limitbuyid; }

   public void setLimitbuyid(Integer limitbuyid) {
     this.limitbuyid = limitbuyid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\LimitBuyGoods.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */