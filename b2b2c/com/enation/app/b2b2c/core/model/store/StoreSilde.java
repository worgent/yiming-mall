 package com.enation.app.b2b2c.core.model.store;

 import com.enation.framework.database.NotDbField;




 public class StoreSilde
 {
   private Integer silde_id;
   private Integer store_id;
   private String silde_url;
   private String img;
   private String sildeImg;

   @NotDbField
   public String getSildeImg()
   {
     return this.sildeImg;
   }

   public void setSildeImg(String sildeImg) { this.sildeImg = sildeImg; }

   public Integer getSilde_id() {
     return this.silde_id;
   }

   public void setSilde_id(Integer silde_id) { this.silde_id = silde_id; }

   public Integer getStore_id() {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) { this.store_id = store_id; }

   public String getSilde_url() {
     return this.silde_url;
   }

   public void setSilde_url(String silde_url) { this.silde_url = silde_url; }

   public String getImg() {
     return this.img;
   }

   public void setImg(String img) { this.img = img; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\store\StoreSilde.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */