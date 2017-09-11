 package com.enation.app.shop.component.search.plugin;

 import com.enation.app.shop.core.model.Attribute;
 import com.enation.app.shop.core.plugin.search.IGoodsDataFilter;
 import com.enation.app.shop.core.service.IGoodsTypeManager;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;










 @Component
 public class GoodsPropertyDataFilter
   extends AutoRegisterPlugin
   implements IGoodsDataFilter
 {
   private IGoodsTypeManager goodsTypeManager;

   public void filter(List<Map> goodsList)
   {
     for (Map goods : goodsList) {
       if ((goods != null) &&
         (goods.get("type_id") != null)) {
         Map propMap = new HashMap();
         List<Attribute> attrList = this.goodsTypeManager.getAttrListByTypeId(((Integer)goods.get("type_id")).intValue());

         int i = 1;
         for (Attribute attr : attrList) {
           String value = (String)goods.get("p" + i);

           attr.setValue(value);
           propMap.put("p" + i, attr.getValStr());
           i++;
         }

         goods.put("propMap", propMap);
       }
     }
   }

   public String getAuthor()
   {
     return "kingapex";
   }


   public String getId()
   {
     return "goodsPropertyDataFilter";
   }


   public String getName()
   {
     return "商品属性数据过滤器";
   }


   public String getType()
   {
     return "searchFilter";
   }


   public String getVersion()
   {
     return "1.0";
   }

   public IGoodsTypeManager getGoodsTypeManager()
   {
     return this.goodsTypeManager;
   }

   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager)
   {
     this.goodsTypeManager = goodsTypeManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\search\plugin\GoodsPropertyDataFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */