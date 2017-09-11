 package com.enation.app.shop.core.plugin.search;

 import com.enation.framework.util.StringUtil;





 public class SearchSelector
 {
   private String name;
   private String url;
   private boolean isSelected;
   private String value;

   public String getName()
   {
     return this.name;
   }

   public void setName(String name) { this.name = name; }

   public String getUrl() {
     if ((!StringUtil.isEmpty(this.url)) && (this.url.startsWith("/"))) {
       this.url = this.url.substring(1, this.url.length());
     }
     return this.url;
   }

   public void setUrl(String url) { this.url = url; }

   public boolean getIsSelected() {
     return this.isSelected;
   }

   public void setSelected(boolean isSelected) { this.isSelected = isSelected; }

   public String getValue() {
     return this.value;
   }

   public void setValue(String value) { this.value = value; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\search\SearchSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */