 package com.enation.app.base.core.model;

 import com.enation.framework.database.PrimaryKeyField;

 public class MultiSite implements java.io.Serializable
 {
   private Integer siteid;
   private Integer parentid;
   private Integer code;
   private String name;
   private String domain;
   private Integer themeid;
   private Integer sitelevel;

   @PrimaryKeyField
   public Integer getSiteid()
   {
     return this.siteid;
   }

   public void setSiteid(Integer siteid) {
     this.siteid = siteid;
   }

   public Integer getParentid() {
     return this.parentid;
   }

   public void setParentid(Integer parentid) {
     this.parentid = parentid;
   }

   public Integer getSitelevel() {
     return this.sitelevel;
   }

   public void setSitelevel(Integer sitelevel) {
     this.sitelevel = sitelevel;
   }

   public Integer getCode() {
     return this.code;
   }

   public void setCode(Integer code) {
     this.code = code;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public String getDomain() {
     return this.domain;
   }

   public void setDomain(String domain) {
     this.domain = domain;
   }

   public Integer getThemeid() {
     return this.themeid;
   }

   public void setThemeid(Integer themeid) {
     this.themeid = themeid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\MultiSite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */