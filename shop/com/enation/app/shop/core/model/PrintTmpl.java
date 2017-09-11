 package com.enation.app.shop.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;




 public class PrintTmpl
   implements Serializable
 {
   private Integer prt_tmpl_id;
   private String prt_tmpl_title;
   private String shortcut;
   private String disabled;
   private String prt_tmpl_width;
   private String prt_tmpl_height;
   private String prt_tmpl_data;
   private String bgimage;

   @PrimaryKeyField
   public Integer getPrt_tmpl_id()
   {
     return this.prt_tmpl_id;
   }

   public void setPrt_tmpl_id(Integer prtTmplId) {
     this.prt_tmpl_id = prtTmplId;
   }

   public String getPrt_tmpl_title() {
     return this.prt_tmpl_title;
   }

   public void setPrt_tmpl_title(String prtTmplTitle) {
     this.prt_tmpl_title = prtTmplTitle;
   }

   public String getShortcut() {
     return this.shortcut;
   }

   public void setShortcut(String shortcut) {
     this.shortcut = shortcut;
   }

   public String getDisabled() {
     return this.disabled;
   }

   public void setDisabled(String disabled) {
     this.disabled = disabled;
   }

   public String getPrt_tmpl_width() {
     return this.prt_tmpl_width;
   }

   public void setPrt_tmpl_width(String prt_tmpl_width) {
     this.prt_tmpl_width = prt_tmpl_width;
   }

   public String getPrt_tmpl_height() {
     return this.prt_tmpl_height;
   }

   public void setPrt_tmpl_height(String prt_tmpl_height) {
     this.prt_tmpl_height = prt_tmpl_height;
   }

   public String getPrt_tmpl_data() {
     return this.prt_tmpl_data;
   }

   public void setPrt_tmpl_data(String prtTmplData) {
     this.prt_tmpl_data = prtTmplData;
   }

   public String getBgimage() {
     return this.bgimage;
   }

   public void setBgimage(String bgimage) {
     this.bgimage = bgimage;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\PrintTmpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */