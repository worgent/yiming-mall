 package com.enation.app.shop.component.bonus.depot.plugin.goods;

 import com.enation.app.shop.core.plugin.goods.IGoodsAfterAddEvent;
 import com.enation.app.shop.core.plugin.goods.IGoodsAfterEditEvent;
 import com.enation.eop.processor.httpcache.HttpCacheManager;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;

 @Component
 public class GoodsModifedUpdateCachePlugin
   extends AutoRegisterPlugin
   implements IGoodsAfterAddEvent, IGoodsAfterEditEvent
 {
   public void updateCache(Map goods)
   {
     int catid = Integer.valueOf(goods.get("cat_id").toString()).intValue();
     int goodsid = Integer.valueOf(goods.get("goods_id").toString()).intValue();

     String link = "";

     link = "/goods-" + goodsid + ".html";

     HttpCacheManager.updateUrlModified(link);
     HttpCacheManager.updateUriModified("/search-(.*).html");
   }


   public void onAfterGoodsEdit(Map goods, HttpServletRequest request)
   {
     updateCache(goods);
   }

   public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
     throws RuntimeException
   {
     updateCache(goods);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\depot\plugin\goods\GoodsModifedUpdateCachePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */