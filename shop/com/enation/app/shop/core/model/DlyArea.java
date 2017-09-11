 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;



 public class DlyArea
   implements Serializable
 {
   private Integer area_id;
   private String name;

   @PrimaryKeyField
   public Integer getArea_id()
   {
     return this.area_id;
   }

   public void setArea_id(Integer areaId) {
     this.area_id = areaId;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\DlyArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */