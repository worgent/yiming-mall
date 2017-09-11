 package com.enation.app.base.core.model;


 public class Reply
 {
   private Integer replyid;

   private Integer askid;

   private String content;
   private String username;
   private Long dateline;

   public Integer getReplyid()
   {
     return this.replyid;
   }

   public void setReplyid(Integer replyid) {
     this.replyid = replyid;
   }

   public Integer getAskid() {
     return this.askid;
   }

   public void setAskid(Integer askid) {
     this.askid = askid;
   }

   public String getContent() {
     return this.content;
   }

   public void setContent(String content) {
     this.content = content;
   }

   public String getUsername() {
     return this.username;
   }

   public void setUsername(String username) {
     this.username = username;
   }

   public Long getDateline() {
     return this.dateline;
   }

   public void setDateline(Long dateline) {
     this.dateline = dateline;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\Reply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */