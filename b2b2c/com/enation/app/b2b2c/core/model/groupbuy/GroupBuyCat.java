 package com.enation.app.b2b2c.core.model.groupbuy;

 import com.enation.framework.database.PrimaryKeyField;






 public class GroupBuyCat
 {
   private int catid;
   private int parentid;
   private String cat_name;
   private String cat_path;
   private int cat_order;

   @PrimaryKeyField
   public int getCatid()
   {
     return this.catid;
   }

   public void setCatid(int catid) { this.catid = catid; }

   public int getParentid() {
     return this.parentid;
   }

   public void setParentid(int parentid) { this.parentid = parentid; }

   public String getCat_name() {
     return this.cat_name;
   }

   public void setCat_name(String cat_name) { this.cat_name = cat_name; }

   public String getCat_path() {
     return this.cat_path;
   }

   public void setCat_path(String cat_path) { this.cat_path = cat_path; }

   public int getCat_order() {
     return this.cat_order;
   }

   public void setCat_order(int cat_order) { this.cat_order = cat_order; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\groupbuy\GroupBuyCat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */