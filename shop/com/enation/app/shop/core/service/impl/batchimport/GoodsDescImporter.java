 package com.enation.app.shop.core.service.impl.batchimport;

 import com.enation.app.shop.core.model.ImportDataSource;
 import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
 import com.enation.app.shop.core.service.impl.batchimport.util.GoodsDescReader;
 import com.enation.framework.database.IDaoSupport;
 import java.util.Map;
 import org.apache.log4j.Logger;
 import org.w3c.dom.Element;












 public class GoodsDescImporter
   implements IGoodsDataImporter
 {
   protected final Logger logger = Logger.getLogger(getClass());

   private IDaoSupport daoSupport;

   private IDaoSupport baseDaoSupport;
   private GoodsDescReader goodsDescReader;

   public void imported(Object value, Element node, ImportDataSource importDs, Map goods)
   {
     Integer goodsid = (Integer)goods.get("goods_id");


     if (this.logger.isDebugEnabled()) {
       this.logger.debug("开始导入商品[" + goodsid + "]描述...");
     }

     String bodyhtml = this.goodsDescReader.read(importDs.getDatafolder(), goodsid.toString());

     if (bodyhtml != null) {
       this.baseDaoSupport.execute("update goods set intro=? where goods_id=?", new Object[] { bodyhtml, goodsid });
     }


     if (this.logger.isDebugEnabled()) {
       this.logger.debug("导入商品[" + goodsid + "]描述 完成");
     }
   }

   public IDaoSupport getDaoSupport()
   {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) { this.daoSupport = daoSupport; }


   public IDaoSupport getBaseDaoSupport()
   {
     return this.baseDaoSupport;
   }

   public void setBaseDaoSupport(IDaoSupport baseDaoSupport) {
     this.baseDaoSupport = baseDaoSupport;
   }

   public GoodsDescReader getGoodsDescReader() {
     return this.goodsDescReader;
   }

   public void setGoodsDescReader(GoodsDescReader goodsDescReader) {
     this.goodsDescReader = goodsDescReader;
   }

   public static void main(String[] args) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\batchimport\GoodsDescImporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */