 package com.enation.app.base.core.model;

 import com.enation.framework.database.PrimaryKeyField;




 public class ProductCat
 {
   private Integer id;
   private String name;
   private Integer num;
   private Integer sort;

   public String getName()
   {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

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

   public Integer getSort() {
     return this.sort;
   }

   public void setSort(Integer sort) {
     this.sort = sort;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\ProductCat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */