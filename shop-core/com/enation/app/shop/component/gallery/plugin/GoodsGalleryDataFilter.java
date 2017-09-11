 package com.enation.app.shop.component.gallery.plugin;

 import com.enation.app.shop.core.plugin.search.IGoodsDataFilter;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;








 @Component
 public class GoodsGalleryDataFilter
   extends AutoRegisterPlugin
   implements IGoodsDataFilter
 {
   public void filter(List<Map> goodsList)
   {
     for (Map goods : goodsList) {
       goods.put("original", UploadUtil.replacePath((String)goods.get("original")));
       goods.put("big", UploadUtil.replacePath((String)goods.get("big")));
       goods.put("small", UploadUtil.replacePath((String)goods.get("small")));
       goods.put("thumbnail", UploadUtil.replacePath((String)goods.get("thumbnail")));
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\gallery\plugin\GoodsGalleryDataFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */