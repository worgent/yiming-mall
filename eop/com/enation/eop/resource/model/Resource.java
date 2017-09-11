 package com.enation.eop.resource.model;

 import com.enation.framework.database.NotDbField;
 import com.enation.framework.database.PrimaryKeyField;










 public class Resource
 {
   private Integer id;
   private Integer deleteflag = Integer.valueOf(0);
   private String productId;

   public Integer getDeleteflag()
   {
     return this.deleteflag;
   }

   public void setDeleteflag(Integer deleteflag) {
     this.deleteflag = deleteflag;
   }

   @PrimaryKeyField
   public Integer getId() {
     return this.id;
   }

   public void setId(Integer id) {
     this.id = id;
   }

   @NotDbField
   public String getProductId() {
     return this.productId;
   }

   public void setProductId(String productId) {
     this.productId = productId;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-eop.jar!\com\enation\eop\resource\model\Resource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */