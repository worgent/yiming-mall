 package com.enation.app.shop.core.plugin.goods;

 import com.enation.app.shop.core.plugin.search.IGoodsDataFilter;
 import com.enation.framework.plugin.AutoRegisterPluginsBundle;
 import java.util.List;
 import java.util.Map;


 public class GoodsDataFilterBundle
   extends AutoRegisterPluginsBundle
 {
   public String getName()
   {
     return "商品数据过滤插件桩";
   }


   public List getPluginList()
   {
     return getPlugins();
   }

   public void filterGoodsData(List<Map> goodsList)
   {
     List<IGoodsDataFilter> filterList = getPluginList();

     if (filterList == null) { return;
     }
     for (Map goods : goodsList) {
       for (IGoodsDataFilter filter : filterList) {
         filter.filter(goodsList);
       }
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\plugin\goods\GoodsDataFilterBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */