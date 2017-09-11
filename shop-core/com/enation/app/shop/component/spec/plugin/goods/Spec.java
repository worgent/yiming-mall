 package com.enation.app.shop.component.spec.plugin.goods;

 import java.io.Serializable;









 public class Spec
   implements Serializable
 {
   private Integer spec_id;
   private Integer goods_id;
   private String sn;
   private Integer store;
   private Double price;
   private String specs;

   public Integer getGoods_id()
   {
     return this.goods_id;
   }

   public void setGoods_id(Integer goods_id) { this.goods_id = goods_id; }

   public Double getPrice() {
     return this.price;
   }

   public void setPrice(Double price) { this.price = price; }

   public String getSn() {
     return this.sn;
   }

   public void setSn(String sn) { this.sn = sn; }

   public Integer getSpec_id() {
     return this.spec_id;
   }

   public void setSpec_id(Integer spec_id) { this.spec_id = spec_id; }

   public Integer getStore() {
     return this.store;
   }

   public void setStore(Integer store) { this.store = store; }

   public String getSpecs() {
     return this.specs;
   }

   public void setSpecs(String specs) { this.specs = specs; }

   public String[] getSpecAr()
   {
     String[] spec_ar = null;

     if (this.specs != null) {
       String[] tempAr = this.specs.split(",");
       spec_ar = new String[tempAr.length];

       for (int i = 0; i < tempAr.length; i++) {
         spec_ar[i] = tempAr[i].replaceAll("\"", "");
       }
     }


     return spec_ar;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\plugin\goods\Spec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */