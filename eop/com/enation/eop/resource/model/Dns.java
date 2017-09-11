 package com.enation.eop.resource.model;

 import java.io.Serializable;












 public class Dns
   implements Serializable
 {
   private static final long serialVersionUID = 7525130004L;
   private String domain;
   private EopSite site;

   public String getDomain()
   {
     return this.domain;
   }

   public void setDomain(String domain) {
     this.domain = domain;
   }

   public EopSite getSite() {
     return this.site;
   }

   public void setSite(EopSite site) {
     this.site = site;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\model\Dns.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */