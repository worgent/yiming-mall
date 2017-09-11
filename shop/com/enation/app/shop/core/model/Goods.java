 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;





 public class Goods
   implements Serializable
 {
   private Integer goods_id;
   private String name;
   private String sn;
   private Integer brand_id;
   private Integer cat_id;
   private Integer type_id;
   private String goods_type;
   private String unit;
   private Double weight;
   private Integer market_enable;
   private String thumbnail;
   private String big;
   private String small;
   private String original;
   private String brief;
   private String intro;
   private Double price;
   private Double mktprice;
   private Integer store;
   private String adjuncts;
   private String params;
   private String specs;
   private Long create_time;
   private Long last_modify;
   private Integer view_count;
   private Integer buy_count;
   private Integer disabled;
   private String page_title;
   private String meta_keywords;
   private String meta_description;
   private Integer point;
   private Integer sord;

   public Integer getBrand_id()
   {
     if (this.brand_id == null)
       this.brand_id = Integer.valueOf(0);
     return this.brand_id;
   }

   public void setBrand_id(Integer brand_id) {
     this.brand_id = brand_id;
   }

   public String getBrief() {
     return this.brief;
   }

   public void setBrief(String brief) {
     this.brief = brief;
   }

   public Integer getBuy_count() {
     return this.buy_count;
   }

   public void setBuy_count(Integer buy_count) {
     this.buy_count = buy_count;
   }

   public Integer getDisabled() {
     return this.disabled;
   }

   public void setDisabled(Integer disabled) {
     this.disabled = disabled;
   }

   @PrimaryKeyField
   public Integer getGoods_id() {
     return this.goods_id;
   }

   public void setGoods_id(Integer goods_id) {
     this.goods_id = goods_id;
   }













   public String getThumbnail()
   {
     return this.thumbnail;
   }

   public void setThumbnail(String thumbnail) {
     this.thumbnail = thumbnail;
   }

   public String getBig() {
     return this.big;
   }

   public void setBig(String big) {
     this.big = big;
   }

   public String getSmall() {
     return this.small;
   }

   public void setSmall(String small) {
     this.small = small;
   }

   public String getOriginal() {
     return this.original;
   }

   public void setOriginal(String original) {
     this.original = original;
   }

   public String getIntro() {
     return this.intro;
   }

   public void setIntro(String intro) {
     this.intro = intro;
   }

   public Integer getMarket_enable() {
     return this.market_enable;
   }

   public void setMarket_enable(Integer market_enable) {
     this.market_enable = market_enable;
   }

   public String getMeta_description() {
     return this.meta_description;
   }

   public void setMeta_description(String meta_description) {
     this.meta_description = meta_description;
   }

   public String getMeta_keywords() {
     return this.meta_keywords;
   }

   public void setMeta_keywords(String meta_keywords) {
     this.meta_keywords = meta_keywords;
   }

   public Double getMktprice() {
     return this.mktprice;
   }

   public void setMktprice(Double mktprice) {
     this.mktprice = mktprice;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getPage_title() {
     return this.page_title;
   }

   public void setPage_title(String page_title) {
     this.page_title = page_title;
   }

   public String getParams() {
     return this.params;
   }

   public void setParams(String params) {
     this.params = params;
   }

   public Double getPrice() {
     return this.price;
   }

   public void setPrice(Double price) {
     this.price = price;
   }

   public String getSn() {
     return this.sn;
   }

   public void setSn(String sn) {
     this.sn = sn;
   }

   public Integer getType_id() {
     return this.type_id;
   }

   public void setType_id(Integer type_id) {
     this.type_id = type_id;
   }

   public String getGoods_type() {
     return this.goods_type;
   }

   public void setGoods_type(String goodsType) {
     this.goods_type = goodsType;
   }

   public String getUnit() {
     return this.unit;
   }

   public void setUnit(String unit) {
     this.unit = unit;
   }

   public Integer getView_count() {
     return this.view_count;
   }

   public void setView_count(Integer view_count) {
     this.view_count = view_count;
   }

   public Double getWeight() {
     this.weight = (this.weight == null ? (this.weight = Double.valueOf(0.0D)) : this.weight);
     return this.weight;
   }

   public void setWeight(Double weight) {
     this.weight = weight;
   }

   public Integer getCat_id() {
     return this.cat_id;
   }

   public void setCat_id(Integer cat_id) {
     this.cat_id = cat_id;
   }

   public Integer getStore() {
     return this.store;
   }

   public void setStore(Integer store) {
     this.store = store;
   }

   public Long getCreate_time() {
     return this.create_time;
   }

   public void setCreate_time(Long create_time) {
     this.create_time = create_time;
   }

   public Long getLast_modify() {
     return this.last_modify;
   }

   public void setLast_modify(Long last_modify) {
     this.last_modify = last_modify;
   }

   public String getSpecs() {
     return this.specs;
   }

   public void setSpecs(String specs) {
     this.specs = specs;
   }

   public String getAdjuncts() {
     return this.adjuncts;
   }

   public void setAdjuncts(String adjuncts) {
     this.adjuncts = adjuncts;
   }

   public Integer getPoint() {
     this.point = Integer.valueOf(this.point == null ? 0 : this.point.intValue());
     return this.point;
   }

   public void setPoint(Integer point) {
     this.point = point;
   }

   public Integer getSord() {
     return this.sord;
   }

   public void setSord(Integer sord) {
     this.sord = sord;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\Goods.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */