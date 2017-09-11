 package com.enation.app.b2b2c.core.model.groupbuy;

 import com.enation.app.shop.core.model.Goods;
 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;










 public class GroupBuy
 {
   private int store_id;
   private int gb_id;
   private int act_id;
   private int area_id;
   private String gb_name;
   private String gb_title;
   private String goods_name;
   private int goods_id;
   private double price;
   private String img_url;
   private int goods_num;
   private int cat_id;
   private int visual_num;
   private int limit_num;
   private int buy_num;
   private String remark;
   private int gb_status;
   private int view_num;
   private long add_time;
   private Goods goods;

   @PrimaryKeyField
   public int getGb_id()
   {
     return this.gb_id;
   }

   public void setGb_id(int gb_id) { this.gb_id = gb_id; }

   public int getAct_id() {
     return this.act_id;
   }

   public void setAct_id(int act_id) { this.act_id = act_id; }

   public String getGb_name() {
     return this.gb_name;
   }

   public void setGb_name(String gb_name) { this.gb_name = gb_name; }

   public String getGb_title() {
     return this.gb_title;
   }

   public void setGb_title(String gb_title) { this.gb_title = gb_title; }

   public String getGoods_name() {
     return this.goods_name;
   }

   public void setGoods_name(String goods_name) { this.goods_name = goods_name; }

   public int getGoods_id() {
     return this.goods_id;
   }

   public void setGoods_id(int goods_id) { this.goods_id = goods_id; }

   public double getPrice() {
     return this.price;
   }

   public void setPrice(double price) { this.price = price; }

   public String getImg_url() {
     return this.img_url;
   }

   public void setImg_url(String img_url) { this.img_url = img_url; }

   public int getGoods_num() {
     return this.goods_num;
   }

   public void setGoods_num(int goods_num) { this.goods_num = goods_num; }

   public int getCat_id() {
     return this.cat_id;
   }

   public void setCat_id(int cat_id) { this.cat_id = cat_id; }

   public int getVisual_num() {
     return this.visual_num;
   }

   public void setVisual_num(int visual_num) { this.visual_num = visual_num; }

   public int getLimit_num() {
     return this.limit_num;
   }

   public void setLimit_num(int limit_num) { this.limit_num = limit_num; }

   public String getRemark() {
     return this.remark;
   }

   public void setRemark(String remark) { this.remark = remark; }

   public int getGb_status() {
     return this.gb_status;
   }

   public void setGb_status(int gb_status) { this.gb_status = gb_status; }

   public int getArea_id()
   {
     return this.area_id;
   }

   public void setArea_id(int area_id) { this.area_id = area_id; }

   public int getStore_id() {
     return this.store_id;
   }

   public void setStore_id(int store_id) { this.store_id = store_id; }

   public long getAdd_time() {
     return this.add_time;
   }

   public void setAdd_time(long add_time) { this.add_time = add_time; }

   public int getBuy_num() {
     return this.buy_num;
   }

   public void setBuy_num(int buy_num) { this.buy_num = buy_num; }

   @NotDbField
   public Goods getGoods()
   {
     return this.goods;
   }

   public void setGoods(Goods goods) {
     this.goods = goods;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\groupbuy\GroupBuy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */