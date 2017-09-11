 package com.enation.eop.resource.model;

 import com.enation.framework.database.PrimaryKeyField;






 public class EopSiteDomain
 {
   private Integer id;
   private String domain;
   private Integer domaintype;
   private Integer siteid;
   private Integer userid;
   private Integer status;

   public Integer getStatus()
   {
     return this.status;
   }

   public void setStatus(Integer status) {
     this.status = status;
   }

   public Integer getUserid() {
     return this.userid;
   }

   public void setUserid(Integer userid) {
     this.userid = userid;
   }

   @PrimaryKeyField
   public Integer getId() {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public String getDomain() {
     return this.domain;
   }

   public void setDomain(String domain) {
     this.domain = domain;
   }

   public Integer getDomaintype() {
     return this.domaintype;
   }

   public void setDomaintype(Integer domaintype) {
     this.domaintype = domaintype;
   }

   public Integer getSiteid() {
     return this.siteid;
   }

   public void setSiteid(Integer siteid) {
     this.siteid = siteid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\model\EopSiteDomain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */