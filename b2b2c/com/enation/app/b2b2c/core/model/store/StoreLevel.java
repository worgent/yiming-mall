 package com.enation.app.b2b2c.core.model.store;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;



 public class StoreLevel
   implements Serializable
 {
   private Integer level_id;
   private String level_name;

   @PrimaryKeyField
   public Integer getLevel_id()
   {
     return this.level_id;
   }

   public void setLevel_id(Integer level_id) { this.level_id = level_id; }

   public String getLevel_name() {
     return this.level_name;
   }

   public void setLevel_name(String level_name) { this.level_name = level_name; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\store\StoreLevel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */