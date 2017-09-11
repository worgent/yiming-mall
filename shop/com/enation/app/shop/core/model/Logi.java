 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;




 public class Logi
   implements Serializable
 {
   private Integer id;
   private String name;
   private String code;

   @PrimaryKeyField
   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getCode() {
     return this.code;
   }

   public void setCode(String code) {
     this.code = code;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\Logi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */