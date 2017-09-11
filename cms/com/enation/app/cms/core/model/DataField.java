 package com.enation.app.cms.core.model;

 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;









 public class DataField
 {
   private Integer field_id;
   private String china_name;
   private String english_name;
   private String data_size;
   private String show_form;
   private String show_value;
   private Long add_time;
   private Integer model_id;
   private String save_value;
   private int is_validate;
   private Integer taxis;
   private Integer dict_id;
   private Integer is_show;
   private String inputHtml;

   @NotDbField
   public String getInputHtml()
   {
     return this.inputHtml;
   }

   public void setInputHtml(String inputHtml) { this.inputHtml = inputHtml; }

   public Long getAdd_time() {
     return this.add_time;
   }

   public void setAdd_time(Long add_time) { this.add_time = add_time; }

   public String getChina_name() {
     return this.china_name;
   }

   public void setChina_name(String china_name) { this.china_name = china_name; }

   public String getEnglish_name()
   {
     return this.english_name;
   }

   public void setEnglish_name(String english_name) { this.english_name = english_name; }

   @PrimaryKeyField
   public Integer getField_id() {
     return this.field_id;
   }

   public void setField_id(Integer field_id) { this.field_id = field_id; }

   public Integer getModel_id() {
     return this.model_id;
   }

   public void setModel_id(Integer model_id) { this.model_id = model_id; }

   public String getShow_value()
   {
     return this.show_value;
   }

   public void setShow_value(String show_value) { this.show_value = show_value; }

   public String getShow_form() {
     return this.show_form;
   }

   public void setShow_form(String show_form) { this.show_form = show_form; }

   public String getData_size() {
     return this.data_size;
   }

   public void setData_size(String data_size) { this.data_size = data_size; }

   public int getIs_validate()
   {
     return this.is_validate;
   }

   public void setIs_validate(int isValidate) { this.is_validate = isValidate; }

   public String getSave_value() {
     return this.save_value;
   }

   public void setSave_value(String save_value) { this.save_value = save_value; }

   public Integer getTaxis() {
     return this.taxis;
   }

   public void setTaxis(Integer taxis) { this.taxis = taxis; }

   public Integer getDict_id() {
     return this.dict_id;
   }

   public void setDict_id(Integer dict_id) { this.dict_id = dict_id; }

   public Integer getIs_show() {
     return this.is_show;
   }

   public void setIs_show(Integer isShow) { this.is_show = isShow; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-cms.jar!\com\enation\app\cms\core\model\DataField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */