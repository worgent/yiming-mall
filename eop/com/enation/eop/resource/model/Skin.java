 package com.enation.eop.resource.model;


 public class Skin
   extends Resource
 {
   private String skinname;

   private String path;


   public String getSkinname()
   {
     return this.skinname;
   }

   public void setSkinname(String skinname) {
     this.skinname = skinname;
   }


   private String thumb = "preview.png";

   public String getThumb() {
     return this.thumb;
   }

   public void setThumb(String thumb) {
     this.thumb = thumb;
   }

   public String getPath() {
     return this.path;
   }

   public void setPath(String path) {
     this.path = path;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\model\Skin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */