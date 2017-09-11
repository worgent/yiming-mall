 package com.enation.app.shop.core.model.support;

 import com.enation.app.shop.core.model.Goods;
 import com.enation.framework.util.CurrencyUtil;
 import java.util.List;
 import java.util.Map;



















 public class GoodsView
   extends Goods
 {
   private Double save_price;
   private Double agio;
   private String brand_name;
   private Map propMap;
   private int hasSpec;
   private List specList;
   private Integer productid;
   private boolean isLast = false;
   private boolean isFirst = false;





   public Double getAgio()
   {
     this.agio = Double.valueOf(CurrencyUtil.div(getPrice().doubleValue(), getMktprice().doubleValue()));
     return this.agio;
   }

   public void setAgio(Double agio) {
     this.agio = agio;
   }

   public Double getSave_price()
   {
     this.save_price = Double.valueOf(CurrencyUtil.sub(getMktprice().doubleValue(), getPrice().doubleValue()));
     return this.save_price;
   }

   public void setSave_price(Double save_price) {
     this.save_price = save_price;
   }

   public String getBrand_name() {
     return this.brand_name;
   }

   public void setBrand_name(String brand_name) {
     this.brand_name = brand_name;
   }

   public Map getPropMap() {
     return this.propMap;
   }

   public void setPropMap(Map propMap) {
     this.propMap = propMap;
   }

   public List getSpecList() {
     return this.specList;
   }

   public void setSpecList(List specList) {
     this.specList = specList;
   }

   public int getHasSpec() {
     return this.hasSpec;
   }

   public void setHasSpec(int hasSpec) {
     this.hasSpec = hasSpec;
   }

   public Integer getProductid() {
     return this.productid;
   }

   public void setProductid(Integer productid) {
     this.productid = productid;
   }

   public boolean getIsLast() {
     return this.isLast;
   }

   public void setIsLast(boolean isLast) {
     this.isLast = isLast;
   }

   public boolean getIsFirst() {
     return this.isFirst;
   }

   public void setIsFirst(boolean isFirst) {
     this.isFirst = isFirst;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\support\GoodsView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */