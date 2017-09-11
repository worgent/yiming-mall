 package com.enation.app.base.core.model;

 import com.enation.framework.database.PrimaryKeyField;

 public class ProductColor {
   private Integer id;
   private String colorname;
   private Integer num;

   @PrimaryKeyField
   public Integer getId() {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public Integer getNum() {
     return this.num;
   }

   public void setNum(Integer num) {
     this.num = num;
   }

   public String getColorname() {
     return this.colorname;
   }

   public void setColorname(String colorname) {
     this.colorname = colorname;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\ProductColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */