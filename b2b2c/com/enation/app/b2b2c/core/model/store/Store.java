 package com.enation.app.b2b2c.core.model.store;

 import com.enation.framework.database.PrimaryKeyField;








 public class Store
 {
   private int store_id;
   private String store_name;
   private int store_provinceid;
   private int store_cityid;
   private int store_regionid;
   private String store_province;
   private String store_city;
   private String store_region;
   private String attr;
   private String zip;
   private String tel;
   private int store_level;
   private int member_id;
   private String member_name;
   private String id_number;
   private String id_img;
   private String license_img;
   private int disabled;
   private Long create_time;
   private Long end_time;
   private String store_logo;
   private String store_banner;
   private String description;
   private int store_recommend;
   private int store_theme;
   private int store_credit;
   private double praise_rate;
   private double store_desccredit;
   private double store_servicecredit;
   private double store_deliverycredit;
   private int store_collect;
   private int store_auth;
   private int name_auth;
   private int goods_num;
   private String qq;

   public String getQq()
   {
     return this.qq;
   }

   public void setQq(String qq) { this.qq = qq; }

   public String getStore_province() {
     return this.store_province;
   }

   public String getStore_banner() { return this.store_banner; }

   public void setStore_banner(String store_banner)
   {
     this.store_banner = store_banner;
   }

   public void setStore_province(String store_province) {
     this.store_province = store_province;
   }

   public String getStore_city() { return this.store_city; }

   public void setStore_city(String store_city) {
     this.store_city = store_city;
   }

   public String getStore_region() { return this.store_region; }

   public void setStore_region(String store_region) {
     this.store_region = store_region;
   }

   @PrimaryKeyField
   public int getStore_id() { return this.store_id; }

   public void setStore_id(int store_id) {
     this.store_id = store_id;
   }

   public String getStore_name() { return this.store_name; }

   public void setStore_name(String store_name) {
     this.store_name = store_name;
   }

   public int getStore_provinceid() { return this.store_provinceid; }

   public void setStore_provinceid(int store_provinceid) {
     this.store_provinceid = store_provinceid;
   }

   public int getStore_cityid() { return this.store_cityid; }

   public void setStore_cityid(int store_cityid) {
     this.store_cityid = store_cityid;
   }

   public int getStore_regionid() { return this.store_regionid; }

   public void setStore_regionid(int store_regionid) {
     this.store_regionid = store_regionid;
   }

   public String getAttr() { return this.attr; }

   public void setAttr(String attr) {
     this.attr = attr;
   }

   public String getZip() { return this.zip; }

   public void setZip(String zip) {
     this.zip = zip;
   }

   public String getTel() { return this.tel; }

   public void setTel(String tel) {
     this.tel = tel;
   }

   public int getStore_level() { return this.store_level; }

   public void setStore_level(int store_level) {
     this.store_level = store_level;
   }

   public int getMember_id() { return this.member_id; }

   public void setMember_id(int member_id) {
     this.member_id = member_id;
   }

   public String getMember_name() { return this.member_name; }

   public void setMember_name(String member_name) {
     this.member_name = member_name;
   }

   public String getId_number() { return this.id_number; }

   public void setId_number(String id_number) {
     this.id_number = id_number;
   }

   public String getId_img() { return this.id_img; }

   public void setId_img(String id_img) {
     this.id_img = id_img;
   }

   public String getLicense_img() { return this.license_img; }

   public void setLicense_img(String license_img) {
     this.license_img = license_img;
   }

   public int getDisabled() { return this.disabled; }

   public void setDisabled(int disabled) {
     this.disabled = disabled;
   }

   public Long getCreate_time() { return this.create_time; }

   public void setCreate_time(Long create_time) {
     this.create_time = create_time;
   }

   public Long getEnd_time() { return this.end_time; }

   public void setEnd_time(Long end_time) {
     this.end_time = end_time;
   }

   public String getStore_logo() { return this.store_logo; }

   public void setStore_logo(String store_logo) {
     this.store_logo = store_logo;
   }

   public String getDescription() { return this.description; }

   public void setDescription(String description) {
     this.description = description;
   }

   public int getStore_recommend() { return this.store_recommend; }

   public void setStore_recommend(int store_recommend) {
     this.store_recommend = store_recommend;
   }

   public int getStore_theme() { return this.store_theme; }

   public void setStore_theme(int store_theme) {
     this.store_theme = store_theme;
   }

   public int getStore_credit() { return this.store_credit; }

   public void setStore_credit(int store_credit) {
     this.store_credit = store_credit;
   }

   public double getPraise_rate() { return this.praise_rate; }

   public void setPraise_rate(double praise_rate) {
     this.praise_rate = praise_rate;
   }

   public double getStore_desccredit() { return this.store_desccredit; }

   public void setStore_desccredit(double store_desccredit) {
     this.store_desccredit = store_desccredit;
   }

   public double getStore_servicecredit() { return this.store_servicecredit; }

   public void setStore_servicecredit(double store_servicecredit) {
     this.store_servicecredit = store_servicecredit;
   }

   public double getStore_deliverycredit() { return this.store_deliverycredit; }

   public void setStore_deliverycredit(double store_deliverycredit) {
     this.store_deliverycredit = store_deliverycredit;
   }

   public int getStore_auth() { return this.store_auth; }

   public void setStore_auth(int store_auth) {
     this.store_auth = store_auth;
   }

   public int getName_auth() { return this.name_auth; }

   public void setName_auth(int name_auth) {
     this.name_auth = name_auth;
   }

   public int getGoods_num() { return this.goods_num; }

   public void setGoods_num(int goods_num) {
     this.goods_num = goods_num;
   }

   public int getStore_collect() { return this.store_collect; }

   public void setStore_collect(int store_collect) {
     this.store_collect = store_collect;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\store\Store.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */