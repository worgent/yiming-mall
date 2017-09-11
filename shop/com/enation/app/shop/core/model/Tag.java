 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;


 public class Tag
   implements Serializable
 {
   private Integer tag_id;
   private String tag_name;
   private Integer rel_count;

   @PrimaryKeyField
   public Integer getTag_id()
   {
     return this.tag_id;
   }

   public void setTag_id(Integer tagId) { this.tag_id = tagId; }

   public String getTag_name() {
     return this.tag_name;
   }

   public void setTag_name(String tagName) { this.tag_name = tagName; }

   public Integer getRel_count() {
     return this.rel_count;
   }

   public void setRel_count(Integer relCount) { this.rel_count = relCount; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\Tag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */