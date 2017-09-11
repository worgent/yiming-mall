 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;




 public class Depot
 {
   public Integer id;
   public String name;
   public Integer choose;

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

   public Integer getChoose() {
     return this.choose;
   }

   public void setChoose(Integer choose) { this.choose = choose; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\Depot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */