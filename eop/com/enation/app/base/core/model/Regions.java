 package com.enation.app.base.core.model;

 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;
 import java.io.Serializable;
 import java.util.List;











 public class Regions
   implements Serializable
 {
   private static final long serialVersionUID = -8793615515414923123L;
   private Integer region_id;
   private Integer p_region_id;
   private String region_path;
   private Integer region_grade;
   private String local_name;
   private String zipcode;
   private Integer cod;
   private List<Regions> children;
   private String state;

   @PrimaryKeyField
   public Integer getRegion_id()
   {
     return this.region_id;
   }

   public void setRegion_id(Integer region_id) { this.region_id = region_id; }

   public Integer getRegion_grade() {
     return this.region_grade;
   }

   public void setRegion_grade(Integer region_grade) { this.region_grade = region_grade; }

   public Integer getCod() {
     return this.cod;
   }

   public void setCod(Integer cod) { this.cod = cod; }

   public Integer getP_region_id() {
     return this.p_region_id;
   }

   public void setP_region_id(Integer pRegionId) { this.p_region_id = pRegionId; }

   public String getRegion_path() {
     return this.region_path;
   }

   public void setRegion_path(String regionPath) { this.region_path = regionPath; }

   public String getLocal_name() {
     return this.local_name;
   }

   public void setLocal_name(String localName) { this.local_name = localName; }

   public String getZipcode() {
     return this.zipcode;
   }

   public void setZipcode(String zipcode) { this.zipcode = zipcode; }

   @NotDbField
   public List<Regions> getChildren()
   {
     return this.children;
   }

   public void setChildren(List<Regions> children) { this.children = children; }

   public String getState() {
     return this.state;
   }

   public void setState(String state) { this.state = state; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\Regions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */