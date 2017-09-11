 package com.enation.app.shop.core.model;

 import java.util.List;

 public class ImportDataSource
 {
   private List<Brand> brandList;
   private List<Attribute> propList;
   private String datafolder;
   private int goodsNum;
   private boolean isNewGoods;
   private org.apache.poi.ss.usermodel.Row rowData;

   public List<Brand> getBrandList()
   {
     return this.brandList;
   }

   public void setBrandList(List<Brand> brandList) { this.brandList = brandList; }

   public List<Attribute> getPropList() {
     return this.propList;
   }

   public void setPropList(List<Attribute> propList) { this.propList = propList; }

   public String getDatafolder() {
     return this.datafolder;
   }

   public void setDatafolder(String datafolder) { this.datafolder = datafolder; }

   public int getGoodsNum() {
     return this.goodsNum;
   }

   public void setGoodsNum(int goodsNum) { this.goodsNum = goodsNum; }

   public boolean isNewGoods() {
     return this.isNewGoods;
   }

   public void setNewGoods(boolean isNewGoods) { this.isNewGoods = isNewGoods; }

   public org.apache.poi.ss.usermodel.Row getRowData() {
     return this.rowData;
   }

   public void setRowData(org.apache.poi.ss.usermodel.Row rowData) { this.rowData = rowData; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\model\ImportDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */