 package com.enation.eop.sdk;

 import net.sf.json.JSONObject;




 public class WebItem
 {
   private String style;
   private String target;
   private String text;
   private String url;
   private String className;

   public WebItem() {}

   public WebItem(String text)
   {
     this.text = text;
   }






   public String getStyle()
   {
     return this.style;
   }

   public void setStyle(String style) { this.style = style; }

   public String getTarget() {
     return this.target;
   }

   public void setTarget(String target) { this.target = target; }

   public String getText() {
     return this.text;
   }

   public void setText(String text) { this.text = text; }

   public String getUrl() {
     return this.url;
   }

   public void setUrl(String url) { this.url = url; }

   public String getClassName()
   {
     return this.className;
   }

   public void setClassName(String className) { this.className = className; }

   public String toJson()
   {
     JSONObject jsonObject = JSONObject.fromObject(this);
     return jsonObject.toString();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\WebItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */