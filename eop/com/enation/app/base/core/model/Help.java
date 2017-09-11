 package com.enation.app.base.core.model;

 import com.enation.framework.database.PrimaryKeyField;




 public class Help
 {
   private Integer id;
   private String helpid;
   private String title;
   private String content;

   @PrimaryKeyField
   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public String getHelpid() {
     return this.helpid;
   }

   public void setHelpid(String helpid) {
     this.helpid = helpid;
   }

   public String getTitle() {
     return this.title;
   }

   public void setTitle(String title) {
     this.title = title;
   }

   public String getContent() {
     return this.content;
   }

   public void setContent(String content) {
     this.content = content;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\Help.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */