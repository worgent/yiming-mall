 package com.enation.app.base.core.model;

 import java.io.Serializable;




 public class SiteMapUrl
   implements Serializable
 {
   private String loc;
   private Long lastmod;
   private String changefreq;
   private String priority;

   public String getLoc()
   {
     return this.loc;
   }

   public void setLoc(String loc) {
     this.loc = loc;
   }

   public Long getLastmod() {
     return this.lastmod;
   }

   public void setLastmod(Long lastmod) {
     this.lastmod = lastmod;
   }

   public String getChangefreq() {
     return this.changefreq;
   }

   public void setChangefreq(String changefreq) {
     this.changefreq = changefreq;
   }

   public String getPriority() {
     return this.priority;
   }

   public void setPriority(String priority) {
     this.priority = priority;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\SiteMapUrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */