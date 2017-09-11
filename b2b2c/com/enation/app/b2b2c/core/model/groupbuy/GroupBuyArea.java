 package com.enation.app.b2b2c.core.model.groupbuy;


 public class GroupBuyArea
 {
   private int area_id;

   private int parent_id;

   private String area_name;
   private String area_path;
   private int area_order;

   public int getArea_id()
   {
     return this.area_id;
   }

   public void setArea_id(int area_id) { this.area_id = area_id; }

   public int getParent_id() {
     return this.parent_id;
   }

   public void setParent_id(int parent_id) { this.parent_id = parent_id; }

   public String getArea_name() {
     return this.area_name;
   }

   public void setArea_name(String area_name) { this.area_name = area_name; }

   public String getArea_path() {
     return this.area_path;
   }

   public void setArea_path(String area_path) { this.area_path = area_path; }

   public int getArea_order() {
     return this.area_order;
   }

   public void setArea_order(int area_order) { this.area_order = area_order; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\groupbuy\GroupBuyArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */