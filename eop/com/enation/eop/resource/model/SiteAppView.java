 package com.enation.eop.resource.model;

 import com.enation.framework.database.PrimaryKeyField;






 public class SiteAppView
 {
   private Integer id;
   private Integer siteid;
   private Integer userid;
   private String appid;
   private String app_name;
   private String descript;

   @PrimaryKeyField
   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public Integer getSiteid() {
     return this.siteid;
   }

   public void setSiteid(Integer siteid) {
     this.siteid = siteid;
   }

   public Integer getUserid() {
     return this.userid;
   }

   public void setUserid(Integer userid) {
     this.userid = userid;
   }

   public String getAppid() {
     return this.appid;
   }

   public void setAppid(String appid) {
     this.appid = appid;
   }

   public String getApp_name() {
     return this.app_name;
   }

   public void setApp_name(String appName) {
     this.app_name = appName;
   }

   public String getDescript() {
     return this.descript;
   }

   public void setDescript(String descript) {
     this.descript = descript;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\model\SiteAppView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */