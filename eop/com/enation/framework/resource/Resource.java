 package com.enation.framework.resource;

 public class Resource
 {
   private String src;
   private int compress;
   private int merge;
   private String type;
   private boolean iscommon;

   public String getSrc() {
     return this.src;
   }

   public void setSrc(String src) { this.src = src; }

   public int getCompress() {
     return this.compress;
   }

   public void setCompress(int compress) { this.compress = compress; }

   public int getMerge() {
     return this.merge;
   }

   public void setMerge(int merge) { this.merge = merge; }

   public String getType() {
     return this.type;
   }

   public void setType(String type) { this.type = type; }

   public boolean isIscommon() {
     return this.iscommon;
   }

   public void setIscommon(boolean iscommon) { this.iscommon = iscommon; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\resource\Resource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */