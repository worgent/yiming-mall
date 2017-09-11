 package com.enation.app.b2b2c.core.model.goods;

 import com.enation.app.shop.core.model.Tag;



 public class StoreTag
   extends Tag
 {
   private Integer store_id;
   private String mark;

   public Integer getStore_id()
   {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) {
     this.store_id = store_id;
   }

   public String getMark() { return this.mark; }

   public void setMark(String mark)
   {
     this.mark = mark;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\goods\StoreTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */