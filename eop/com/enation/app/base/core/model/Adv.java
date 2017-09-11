 package com.enation.app.base.core.model;

 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;













 public class Adv
 {
   private Integer aid;
   private Integer acid;
   private Integer atype;
   private Long begintime;
   private Long endtime;
   private Integer isclose;
   private String attachment;
   private String atturl;
   private String url;
   private String aname;
   private Integer clickcount;
   private String disabled;
   private String linkman;
   private String company;
   private String contact;
   private String cname;

   @PrimaryKeyField
   public Integer getAid()
   {
     return this.aid;
   }

   public void setAid(Integer aid) {
     this.aid = aid;
   }

   public Integer getAcid() {
     return this.acid;
   }

   public void setAcid(Integer acid) {
     this.acid = acid;
   }

   public Integer getAtype() {
     return this.atype;
   }

   public void setAtype(Integer atype) {
     this.atype = atype;
   }

   public Long getBegintime() {
     return this.begintime;
   }

   public void setBegintime(Long begintime) {
     this.begintime = begintime;
   }

   public Long getEndtime() {
     return this.endtime;
   }

   public void setEndtime(Long endtime) {
     this.endtime = endtime;
   }

   public Integer getIsclose() {
     return this.isclose;
   }

   public void setIsclose(Integer isclose) {
     this.isclose = isclose;
   }

   public String getAttachment() {
     return this.attachment;
   }

   public void setAttachment(String attachment) {
     this.attachment = attachment;
   }

   public String getAtturl() {
     return this.atturl;
   }

   public void setAtturl(String atturl) {
     this.atturl = atturl;
   }

   public String getUrl() {
     return this.url;
   }

   public void setUrl(String url) {
     this.url = url;
   }

   public String getAname() {
     return this.aname;
   }

   public void setAname(String aname) {
     this.aname = aname;
   }

   public Integer getClickcount() {
     return this.clickcount;
   }

   public void setClickcount(Integer clickcount) {
     this.clickcount = clickcount;
   }

   public String getDisabled() {
     return this.disabled;
   }

   public void setDisabled(String disabled) {
     this.disabled = disabled;
   }

   public String getLinkman() {
     return this.linkman;
   }

   public void setLinkman(String linkman) {
     this.linkman = linkman;
   }

   public String getCompany() {
     return this.company;
   }

   public void setCompany(String company) {
     this.company = company;
   }

   public String getContact() {
     return this.contact;
   }

   public void setContact(String contact) {
     this.contact = contact;
   }

   @NotDbField
   public String getCname() {
     return this.cname;
   }

   public void setCname(String cname) {
     this.cname = cname;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\Adv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */