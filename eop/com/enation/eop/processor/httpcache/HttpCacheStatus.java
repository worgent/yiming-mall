 package com.enation.eop.processor.httpcache;



 public class HttpCacheStatus
 {
   private boolean isModified;


   private long lasyModified;



   public boolean isModified()
   {
     return this.isModified;
   }

   public void setModified(boolean isModified) {
     this.isModified = isModified;
   }

   public long getLasyModified() {
     return this.lasyModified;
   }

   public void setLasyModified(long lasyModified) {
     this.lasyModified = lasyModified;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\processor\httpcache\HttpCacheStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */