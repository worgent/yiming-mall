 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;

 public class FlashAdv implements java.io.Serializable
 {
   private Integer id;
   private String remark;
   private String url;
   private String pic;
   private Integer sort;

   @PrimaryKeyField
   public Integer getId() {
     return this.id;
   }

   public void setId(Integer id) { this.id = id; }

   public String getPic() {
     return this.pic;
   }

   public void setPic(String pic) { this.pic = pic; }

   public String getRemark() {
     return this.remark;
   }

   public void setRemark(String remark) { this.remark = remark; }

   public Integer getSort() {
     return this.sort;
   }

   public void setSort(Integer sort) { this.sort = sort; }

   public String getUrl() {
     return this.url;
   }

   public void setUrl(String url) { this.url = url; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\FlashAdv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */