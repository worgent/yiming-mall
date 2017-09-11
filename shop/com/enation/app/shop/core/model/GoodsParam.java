 package com.enation.app.shop.core.model;

 import java.util.ArrayList;
 import java.util.List;








 public class GoodsParam
 {
   private String name;
   private String value;
   private List valueList;

   public void addValue(String _value)
   {
     if (this.valueList == null) this.valueList = new ArrayList();
     this.valueList.add(_value);
   }

   public String getName() {
     return this.name;
   }

   public void setName(String name) { this.name = name; }

   public String getValue() {
     return this.value;
   }

   public void setValue(String value) { this.value = value; }

   public List getValueList() {
     return this.valueList;
   }

   public void setValueList(List valueList) { this.valueList = valueList; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\GoodsParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */