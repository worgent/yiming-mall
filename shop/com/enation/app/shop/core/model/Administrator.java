 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;







 public class Administrator
   implements Serializable
 {
   private Integer id;
   private String uname;
   private String name;
   private String pwd;
   private Integer status;
   private Long last_login;

   @PrimaryKeyField
   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public String getUname() {
     return this.uname;
   }

   public void setUname(String uname) {
     this.uname = uname;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getPwd() {
     return this.pwd;
   }

   public void setPwd(String pwd) {
     this.pwd = pwd;
   }

   public Integer getStatus()
   {
     return this.status;
   }

   public void setStatus(Integer status) {
     this.status = status;
   }

   public Long getLast_login() {
     return this.last_login;
   }

   public void setLast_login(Long last_login) {
     this.last_login = last_login;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\Administrator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */