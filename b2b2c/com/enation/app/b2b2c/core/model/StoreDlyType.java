 package com.enation.app.b2b2c.core.model;

 import com.enation.app.shop.core.model.DlyType;



 public class StoreDlyType
   extends DlyType
 {
   private static final long serialVersionUID = 3441985028777685352L;
   private Integer store_id;
   private Integer template_id;

   public Integer getStore_id()
   {
     return this.store_id;
   }

   public void setStore_id(Integer store_id) { this.store_id = store_id; }

   public Integer getTemplate_id() {
     return this.template_id;
   }

   public void setTemplate_id(Integer template_id) { this.template_id = template_id; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\model\StoreDlyType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */