 package com.enation.framework.database;

 import java.util.HashMap;
 import java.util.Map;





 public class DynamicField
 {
   private Map<String, Object> fields;

   public DynamicField()
   {
     this.fields = new HashMap();
   }

   public void addField(String name, Object value) {
     this.fields.put(name, value);
   }

   @NotDbField
   public Map<String, Object> getFields() {
     return this.fields;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\framework\database\DynamicField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */