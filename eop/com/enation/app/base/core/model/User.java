 package com.enation.app.base.core.model;

 import com.enation.framework.database.PrimaryKeyField;







 public class User
 {
   private Integer userId;
   private String name;
   private String code;

   public String getCode()
   {
     return this.code;
   }



   public void setCode(String code)
   {
     this.code = code;
   }



   public void finalize()
     throws Throwable
   {}


   @PrimaryKeyField
   public Integer getUserId()
   {
     return this.userId;
   }

   public String getName() {
     return this.name;
   }




   public void setName(String newVal)
   {
     this.name = newVal;
   }




   public void setUserId(Integer newVal)
   {
     this.userId = newVal;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\User.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */