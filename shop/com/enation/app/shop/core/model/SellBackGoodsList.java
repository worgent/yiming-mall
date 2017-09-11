 package com.enation.app.shop.core.model;


 public class SellBackGoodsList
 {
   private Integer id;

   private Integer recid;

   private Integer goods_id;
   private Integer ship_num;
   private Double price;
   private Integer return_num;
   private Integer storage_num;
   private String goods_remark;
   private Integer product_id;

   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) { this.id = id; }

   public Integer getRecid() {
     return this.recid;
   }

   public void setRecid(Integer recid) { this.recid = recid; }

   public Integer getGoods_id() {
     return this.goods_id;
   }

   public void setGoods_id(Integer goods_id) { this.goods_id = goods_id; }

   public Integer getShip_num() {
     return this.ship_num;
   }

   public void setShip_num(Integer ship_num) { this.ship_num = ship_num; }

   public Double getPrice() {
     return this.price;
   }

   public void setPrice(Double price) { this.price = price; }

   public Integer getReturn_num() {
     return this.return_num;
   }

   public void setReturn_num(Integer return_num) { this.return_num = return_num; }

   public Integer getStorage_num() {
     return this.storage_num;
   }

   public void setStorage_num(Integer storage_num) { this.storage_num = storage_num; }

   public String getGoods_remark() {
     return this.goods_remark;
   }

   public void setGoods_remark(String goods_remark) { this.goods_remark = goods_remark; }

   public Integer getProduct_id() {
     return this.product_id;
   }

   public void setProduct_id(Integer product_id) { this.product_id = product_id; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\SellBackGoodsList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */