 package com.enation.eop.resource.model;

 import com.enation.framework.database.PrimaryKeyField;








 public class EopSiteAdmin
 {
   private Integer id;
   private Integer managerid;
   private Integer siteid;
   private Integer userid;

   @PrimaryKeyField
   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public Integer getManagerid() {
     return this.managerid;
   }

   public void setManagerid(Integer managerid) {
     this.managerid = managerid;
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
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\model\EopSiteAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */