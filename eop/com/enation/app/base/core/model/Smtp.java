 package com.enation.app.base.core.model;

 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;









 public class Smtp
   implements Serializable
 {
   private static final long serialVersionUID = 4645737054149076379L;
   private Integer id;
   private String host;
   private String username;
   private String password;
   private long last_send_time;
   private int send_count;
   private int max_count;
   private String mail_from;

   @PrimaryKeyField
   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) { this.id = id; }

   public String getHost() {
     return this.host;
   }

   public void setHost(String host) { this.host = host; }

   public String getUsername() {
     return this.username;
   }

   public void setUsername(String username) { this.username = username; }

   public String getPassword() {
     return this.password;
   }

   public void setPassword(String password) { this.password = password; }

   public long getLast_send_time()
   {
     return this.last_send_time;
   }

   public void setLast_send_time(long last_send_time) { this.last_send_time = last_send_time; }

   public int getSend_count() {
     return this.send_count;
   }

   public void setSend_count(int send_count) { this.send_count = send_count; }

   public int getMax_count() {
     return this.max_count;
   }

   public void setMax_count(int max_count) { this.max_count = max_count; }

   public String getMail_from() {
     return this.mail_from;
   }

   public void setMail_from(String mail_from) { this.mail_from = mail_from; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\Smtp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */