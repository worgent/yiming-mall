 package com.enation.eop.resource.model;

 import com.enation.framework.database.PrimaryKeyField;






 public class EopUserDetail
 {
   private Integer id;
   private Integer userid;
   private String bussinessscope;
   private String regaddress;
   private Long regdate;
   private Integer corpscope;
   private String corpdescript;

   @PrimaryKeyField
   public Integer getId()
   {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   public Integer getUserid() {
     return this.userid;
   }

   public void setUserid(Integer userid) {
     this.userid = userid;
   }

   public String getBussinessscope() {
     return this.bussinessscope;
   }

   public void setBussinessscope(String bussinessscope) {
     this.bussinessscope = bussinessscope;
   }

   public String getRegaddress() {
     return this.regaddress;
   }

   public void setRegaddress(String regaddress) {
     this.regaddress = regaddress;
   }

   public Long getRegdate() {
     return this.regdate;
   }

   public void setRegdate(Long regdate) {
     this.regdate = regdate;
   }

   public Integer getCorpscope() {
     return this.corpscope;
   }

   public void setCorpscope(Integer corpscope) {
     this.corpscope = corpscope;
   }

   public String getCorpdescript() {
     return this.corpdescript;
   }

   public void setCorpdescript(String corpdescript) {
     this.corpdescript = corpdescript;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\model\EopUserDetail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */