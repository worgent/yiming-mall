 package com.enation.app.b2b2c.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;




 public class Navigation
   implements Serializable
 {
   private static final long serialVersionUID = -7709232369561209625L;
   private Integer id;
   private String name;
   private Integer disable;
   private Integer sort;
   private String contents;
   private String nav_url;
   private Integer target;
   private Integer store_id;

   @PrimaryKeyField
   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) { this.id = id; }

   public String getName() {
     return this.name;
   }

   public void setName(String name) { this.name = name; }

   public Integer getDisable() {
     return this.disable;
   }

   public void setDisable(Integer disable) { this.disable = disable; }

   public Integer getSort() {
     return this.sort;
   }

   public void setSort(Integer sort) { this.sort = sort; }

   public String getContents() {
     return this.contents;
   }

   public void setContents(String contents) { this.contents = contents; }

   public String getNav_url() {
     return this.nav_url;
   }

   public void setNav_url(String nav_url) { this.nav_url = nav_url; }

   public Integer getTarget()
   {
     return this.target;
   }

   public void setTarget(Integer target) { this.target = target; }

   public Integer getStore_id() {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) { this.store_id = store_id; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\Navigation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */