 package com.enation.framework.jms;

 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;
 import java.util.HashMap;
 import java.util.Map;





 public class EmailModel
 {
   private Map data = new HashMap();

   private String title = "";

   private String email = "";


   private String template = "";

   private int email_id;

   private String email_type;
   private int is_success;
   private int error_num;
   private int last_send;
   private String content;

   public EmailModel() {}

   public EmailModel(Map data, String title, String to, String template, String type)
   {
     this.data = data;
     this.title = title;
     this.email = to;
     this.template = template;
     this.email_type = type;
   }

   @NotDbField
   public Map getData() {
     return this.data;
   }

   public void setData(Map data) {
     this.data = data;
   }

   public String getTitle()
   {
     return this.title;
   }

   public void setTitle(String title) {
     this.title = title;
   }

   public String getEmail()
   {
     return this.email;
   }

   public void setEmail(String email) {
     this.email = email;
   }

   @NotDbField
   public String getTemplate() {
     return this.template;
   }

   public void setTemplate(String template) {
     this.template = template;
   }



   public String getEmail_type()
   {
     return this.email_type;
   }

   public void setEmail_type(String email_type) {
     this.email_type = email_type;
   }

   public int getError_num() {
     return this.error_num;
   }

   public void setError_num(int error_num) {
     this.error_num = error_num;
   }

   public int getLast_send() {
     return this.last_send;
   }

   public void setLast_send(int last_send) {
     this.last_send = last_send;
   }

   public String getContent() {
     return this.content;
   }

   public void setContent(String content) {
     this.content = content;
   }

   @PrimaryKeyField
   public int getEmail_id() {
     return this.email_id;
   }

   public void setEmail_id(int email_id) {
     this.email_id = email_id;
   }

   public int getIs_success() {
     return this.is_success;
   }

   public void setIs_success(int is_success) {
     this.is_success = is_success;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\jms\EmailModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */