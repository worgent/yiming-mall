 package com.enation.app.base.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;

 public class FriendsLink
   implements Serializable
 {
   private Integer link_id;
   private String name;
   private String url;
   private String logo;
   private Integer sort;

   @PrimaryKeyField
   public Integer getLink_id()
   {
     return this.link_id;
   }

   public void setLink_id(Integer link_id) {
     this.link_id = link_id;
   }

   public String getLogo() {
     return this.logo;
   }

   public void setLogo(String logo) {
     this.logo = logo;
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) {
     this.name = name;
   }

   public Integer getSort() {
     return this.sort;
   }

   public void setSort(Integer sort) {
     this.sort = sort;
   }

   public String getUrl() {
     return this.url;
   }

   public void setUrl(String url) {
     this.url = url;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\FriendsLink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */