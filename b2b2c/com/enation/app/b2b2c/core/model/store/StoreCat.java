 package com.enation.app.b2b2c.core.model.store;

 import com.enation.framework.database.PrimaryKeyField;




 public class StoreCat
 {
   private Integer store_cat_id;
   private Integer store_cat_pid;
   private Integer store_id;
   private String store_cat_name;
   private Integer disable;
   private Integer sort;
   private String cat_path;

   @PrimaryKeyField
   public Integer getStore_cat_id()
   {
     return this.store_cat_id;
   }

   public void setStore_cat_id(Integer store_cat_id) { this.store_cat_id = store_cat_id; }

   public Integer getStore_cat_pid() {
     return this.store_cat_pid;
   }

   public void setStore_cat_pid(Integer store_cat_pid) { this.store_cat_pid = store_cat_pid; }

   public Integer getStore_id() {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) { this.store_id = store_id; }

   public String getStore_cat_name() {
     return this.store_cat_name;
   }

   public void setStore_cat_name(String store_cat_name) { this.store_cat_name = store_cat_name; }

   public Integer getDisable() {
     return this.disable;
   }

   public void setDisable(Integer disable) { this.disable = disable; }

   public Integer getSort() {
     return this.sort;
   }

   public void setSort(Integer sort) { this.sort = sort; }

   public String getCat_path() {
     return this.cat_path;
   }

   public void setCat_path(String cat_path) { this.cat_path = cat_path; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\store\StoreCat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */