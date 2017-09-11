 package com.enation.app.b2b2c.core.tag.store;

 import com.enation.app.b2b2c.core.service.IStoreDlyTypeManager;
 import com.enation.app.b2b2c.core.service.IStoreTemplateManager;
 import com.enation.app.b2b2c.core.service.cart.IStoreCartManager;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;
















 @Component
 public class StoreDlytypeTag
   extends BaseFreeMarkerTag
 {
   private IDlyTypeManager dlyTypeManager;
   private IStoreDlyTypeManager storeDlyTypeManager;
   private IStoreCartManager storeCartManager;
   private IStoreGoodsManager storeGoodsManager;
   private IStoreTemplateManager storeTemplateManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer storeid = (Integer)params.get("storeid");
     Integer regionid = (Integer)params.get("regionid");
     String originalPrice = (String)params.get("originalPrice");
     String weight = (String)params.get("weight");

     List<Map> list = new ArrayList();
     if (Double.valueOf(weight).doubleValue() != 0.0D) {
       Integer tempid = this.storeTemplateManager.getDefTempid(storeid);
       list = this.storeDlyTypeManager.getDlyTypeList(tempid);
       for (Map maps : list) {
         Integer typeid = (Integer)maps.get("type_id");
         Double[] priceArray = this.dlyTypeManager.countPrice(typeid, Double.valueOf(weight), Double.valueOf(originalPrice), regionid + "");
         Double dlyPrice = priceArray[0];
         maps.put("dlyPrice", dlyPrice);
       }
     }

     return list;
   }


   public IStoreTemplateManager getStoreTemplateManager()
   {
     return this.storeTemplateManager;
   }

   public void setStoreTemplateManager(IStoreTemplateManager storeTemplateManager)
   {
     this.storeTemplateManager = storeTemplateManager;
   }

   public IStoreDlyTypeManager getStoreDlyTypeManager()
   {
     return this.storeDlyTypeManager;
   }

   public void setStoreDlyTypeManager(IStoreDlyTypeManager storeDlyTypeManager) {
     this.storeDlyTypeManager = storeDlyTypeManager;
   }

   public IDlyTypeManager getDlyTypeManager() {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) { this.dlyTypeManager = dlyTypeManager; }


   public IStoreCartManager getStoreCartManager()
   {
     return this.storeCartManager;
   }

   public void setStoreCartManager(IStoreCartManager storeCartManager)
   {
     this.storeCartManager = storeCartManager;
   }

   public IStoreGoodsManager getStoreGoodsManager()
   {
     return this.storeGoodsManager;
   }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager)
   {
     this.storeGoodsManager = storeGoodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\tag\store\StoreDlytypeTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */