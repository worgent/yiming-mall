 package com.enation.eop.sdk.user;

 import java.io.Serializable;






 public class UserContext
   implements Serializable
 {
   private static final long serialVersionUID = 752513002L;
   public static final String CONTEXT_KEY = "usercontext";
   private Integer userid;
   private Integer siteid;
   private Integer managerid;

   public UserContext() {}

   public UserContext(Integer userid, Integer siteid, Integer managerid)
   {
     this.userid = userid;
     this.siteid = siteid;
     this.managerid = managerid;
   }

   public Integer getUserid()
   {
     return this.userid;
   }

   public void setUserid(Integer userid) { this.userid = userid; }

   public Integer getSiteid() {
     return this.siteid;
   }

   public void setSiteid(Integer siteid) { this.siteid = siteid; }

   public Integer getManagerid()
   {
     return this.managerid;
   }

   public void setManagerid(Integer managerid) {
     this.managerid = managerid;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\sdk\\user\UserContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */