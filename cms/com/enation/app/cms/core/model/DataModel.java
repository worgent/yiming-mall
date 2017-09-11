 package com.enation.app.cms.core.model;

 import com.enation.framework.database.PrimaryKeyField;







 public class DataModel
 {
   private Integer model_id;
   private String china_name;
   private String english_name;
   private Long add_time;
   private String project_name;
   private String brief;
   private Integer if_audit;
   private String is_unlock;

   public Long getAdd_time()
   {
     return this.add_time;
   }

   public void setAdd_time(Long add_time) { this.add_time = add_time; }

   public String getBrief() {
     return this.brief;
   }

   public void setBrief(String brief) { this.brief = brief; }

   public String getChina_name() {
     return this.china_name;
   }

   public void setChina_name(String china_name) { this.china_name = china_name; }

   public String getEnglish_name() {
     return this.english_name;
   }

   public void setEnglish_name(String english_name) { this.english_name = english_name; }

   @PrimaryKeyField
   public Integer getModel_id() {
     return this.model_id;
   }

   public void setModel_id(Integer model_id) { this.model_id = model_id; }

   public String getProject_name() {
     return this.project_name;
   }

   public void setProject_name(String project_name) { this.project_name = project_name; }

   public Integer getIf_audit() {
     return this.if_audit;
   }

   public void setIf_audit(Integer if_audit) { this.if_audit = if_audit; }

   public String getIs_unlock() {
     return this.is_unlock;
   }

   public void setIs_unlock(String is_unlock) { this.is_unlock = is_unlock; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\model\DataModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */