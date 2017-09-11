 package com.enation.eop.resource.model;

 import com.enation.framework.database.PrimaryKeyField;





 public class IndexItem
 {
   private Integer id;
   private String title;
   private String url;
   private int sort;

   public String getTitle()
   {
     return this.title;
   }

   public void setTitle(String title) {
     this.title = title;
   }

   public String getUrl() {
     return this.url;
   }

   public void setUrl(String url) {
     this.url = url;
   }

   public int getSort() {
     return this.sort;
   }

   public void setSort(int sort) {
     this.sort = sort;
   }

   @PrimaryKeyField
   public Integer getId() {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\model\IndexItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */