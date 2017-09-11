 package com.enation.app.b2b2c.core.model.goods;

 import com.enation.app.shop.core.model.Goods;



 public class StoreGoods
   extends Goods
 {
   private Integer store_id;
   private Integer store_cat_id;
   private Integer buy_num;
   private Integer comment_num;
   private Integer template_id;
   private Integer goods_transfee_charge;

   public Integer getStore_id()
   {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) {
     this.store_id = store_id;
   }

   public Integer getStore_cat_id() {
     return this.store_cat_id;
   }

   public void setStore_cat_id(Integer store_cat_id) {
     this.store_cat_id = store_cat_id;
   }

   public Integer getBuy_num() {
     return this.buy_num;
   }

   public void setBuy_num(Integer buy_num) {
     this.buy_num = buy_num;
   }

   public Integer getComment_num() {
     return this.comment_num;
   }

   public void setComment_num(Integer comment_num) {
     this.comment_num = comment_num;
   }

   public Integer getTemplate_id() {
     return this.template_id;
   }

   public void setTemplate_id(Integer template_id) {
     this.template_id = template_id;
   }

   public Integer getGoods_transfee_charge() {
     return this.goods_transfee_charge;
   }

   public void setGoods_transfee_charge(Integer goods_transfee_charge) {
     this.goods_transfee_charge = goods_transfee_charge;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\goods\StoreGoods.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */