 package com.enation.app.b2b2c.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;




 public class StoreTemlplate
   implements Serializable
 {
   private static final long serialVersionUID = -6119297496435190538L;
   private Integer id;
   private String name;
   private Integer store_id;
   private Integer def_temp;

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

   public Integer getStore_id() {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) { this.store_id = store_id; }

   public Integer getDef_temp() {
     return this.def_temp;
   }

   public void setDef_temp(Integer def_temp) { this.def_temp = def_temp; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\StoreTemlplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */