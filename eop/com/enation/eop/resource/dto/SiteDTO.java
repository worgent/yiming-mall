 package com.enation.eop.resource.dto;

 import com.enation.eop.resource.model.EopSite;
 import com.enation.eop.resource.model.EopSiteAdmin;
 import com.enation.eop.resource.model.EopSiteDomain;













 public class SiteDTO
 {
   private EopSite site;
   private EopSiteDomain domain;
   private EopSiteAdmin siteAdmin;

   public void vaild()
   {
     if (this.domain == null) {
       throw new IllegalArgumentException("站点至少要有一个域名！");
     }

     if (this.siteAdmin == null) {
       throw new IllegalArgumentException("站点至少应该指定一位管理员！");
     }
   }

   public void setUserId(Integer userid) {
     this.site.setUserid(userid);
     this.domain.setUserid(userid);
     this.siteAdmin.setUserid(userid);
   }

   public void setSiteId(Integer siteid) {
     this.domain.setSiteid(siteid);
     this.siteAdmin.setSiteid(siteid);
   }

   public void setManagerid(Integer managerid) {
     this.siteAdmin.setManagerid(managerid);
   }

   public EopSite getSite() {
     return this.site;
   }

   public void setSite(EopSite site) {
     this.site = site;
   }

   public EopSiteDomain getDomain() {
     return this.domain;
   }

   public void setDomain(EopSiteDomain domain) {
     this.domain = domain;
   }

   public EopSiteAdmin getSiteAdmin() {
     return this.siteAdmin;
   }

   public void setSiteAdmin(EopSiteAdmin siteAdmin) {
     this.siteAdmin = siteAdmin;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\dto\SiteDTO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */