 package com.enation.app.shop.core.model;


 public class SellBackLog
 {
   private Integer id;

   private Integer recid;

   private Long logtime;
   private String logdetail;
   private String operator;

   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) { this.id = id; }

   public Integer getRecid() {
     return this.recid;
   }

   public void setRecid(Integer recid) { this.recid = recid; }

   public String getLogdetail() {
     return this.logdetail;
   }

   public void setLogdetail(String logdetail) { this.logdetail = logdetail; }

   public String getOperator() {
     return this.operator;
   }

   public void setOperator(String operator) { this.operator = operator; }

   public Long getLogtime() {
     return this.logtime;
   }

   public void setLogtime(Long logtime) { this.logtime = logtime; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\SellBackLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */