 package com.enation.app.shop.core.model;

 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;
 import java.util.List;






 public class Specification
 {
   private Integer spec_id;
   private String spec_name;
   private Integer spec_show_type;
   private Integer spec_type;
   private String spec_memo;
   private List<SpecValue> valueList;

   @PrimaryKeyField
   public Integer getSpec_id()
   {
     return this.spec_id;
   }

   public void setSpec_id(Integer specId) { this.spec_id = specId; }

   public String getSpec_name() {
     return this.spec_name;
   }

   public void setSpec_name(String specName) { this.spec_name = specName; }

   public Integer getSpec_show_type() {
     return this.spec_show_type;
   }

   public void setSpec_show_type(Integer specShowType) { this.spec_show_type = specShowType; }

   public Integer getSpec_type() {
     return this.spec_type;
   }

   public void setSpec_type(Integer specType) { this.spec_type = specType; }

   public String getSpec_memo() {
     return this.spec_memo;
   }

   public void setSpec_memo(String specMemo) { this.spec_memo = specMemo; }

   @NotDbField
   public List<SpecValue> getValueList()
   {
     return this.valueList;
   }

   public void setValueList(List<SpecValue> valueList) { this.valueList = valueList; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\Specification.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */