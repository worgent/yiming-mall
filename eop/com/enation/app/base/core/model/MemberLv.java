 package com.enation.app.base.core.model;

 import com.enation.framework.database.NotDbField;

 public class MemberLv implements java.io.Serializable
 {
   private Integer lv_id;
   private String name;
   private Integer default_lv;
   private Integer discount;
   private Double lvPrice;
   private int point;
   private boolean selected;

   public MemberLv() {}

   public MemberLv(Integer lv_id, String name)
   {
     this.lv_id = lv_id;
     this.name = name;
   }








   public Integer getDefault_lv()
   {
     return this.default_lv;
   }

   public void setDefault_lv(Integer default_lv) { this.default_lv = default_lv; }

   @com.enation.framework.database.PrimaryKeyField
   public Integer getLv_id() {
     return this.lv_id;
   }

   public void setLv_id(Integer lv_id) { this.lv_id = lv_id; }

   public String getName() {
     return this.name;
   }

   public void setName(String name) { this.name = name; }

   @NotDbField
   public boolean getSelected()
   {
     return this.selected;
   }

   public void setSelected(boolean selected) { this.selected = selected; }

   public Integer getDiscount() {
     return this.discount;
   }

   public void setDiscount(Integer discount) { this.discount = discount; }

   @NotDbField
   public Double getLvPrice()
   {
     return this.lvPrice;
   }

   public void setLvPrice(Double lvPrice) { this.lvPrice = lvPrice; }

   public int getPoint() {
     return this.point;
   }

   public void setPoint(int point) { this.point = point; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\app\base\core\model\MemberLv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */