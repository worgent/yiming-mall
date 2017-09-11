 package com.enation.eop.processor;

 import com.enation.eop.resource.model.EopApp;
 import com.enation.eop.resource.model.EopSite;









 public class FacadePage
 {
   private Integer id;
   private EopSite site;
   private String uri;
   private EopApp app;

   public FacadePage() {}

   public FacadePage(EopSite site)
   {
     this.site = site;
   }

   public EopSite getSite() {
     return this.site;
   }

   public void setSite(EopSite site) {
     this.site = site;
   }

   public String getUri() {
     return this.uri;
   }

   public void setUri(String uri) {
     this.uri = uri;
   }

   public Integer getId() {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public EopApp getApp() {
     return this.app;
   }

   public void setApp(EopApp app) {
     this.app = app;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\FacadePage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */