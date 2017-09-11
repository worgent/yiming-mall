 package com.enation.app.shop.component.visited.plugin;

 import com.enation.app.shop.core.plugin.goods.IGoodsVisitEvent;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.utils.UploadUtil;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.context.webcontext.WebSessionContext;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;













 @Component
 public class VisitedGoodsPlugin
   extends AutoRegisterPlugin
   implements IGoodsVisitEvent
 {
   private IGoodsManager goodsManager;

   public void onVisit(Map goods)
   {
     WebSessionContext sessionContext = ThreadContextHolder.getSessionContext();
     List<Map> visitedGoods = (List)sessionContext.getAttribute("visitedGoods");
     Integer goods_id = Integer.valueOf(goods.get("goods_id").toString());
     boolean visited = false;
     if (visitedGoods == null) visitedGoods = new ArrayList();
     for (Map map : visitedGoods) {
       if (map.get("goods_id").toString().equals(StringUtil.toString(goods_id))) {
         visitedGoods.remove(map);
         visited = true;
         break;
       }
     }
     String thumbnail = (String)goods.get("thumbnail");
     if (StringUtil.isEmpty(thumbnail)) {
       thumbnail = EopSetting.DEFAULT_IMG_URL;
     } else {
       thumbnail = UploadUtil.replacePath(thumbnail);
     }
     Map newmap = new HashMap();
     newmap.put("goods_id", goods_id);
     newmap.put("thumbnail", thumbnail);
     newmap.put("name", goods.get("name"));
     newmap.put("price", goods.get("price"));
     visitedGoods.add(0, newmap);
     sessionContext.setAttribute("visitedGoods", visitedGoods);
     if (!visited) {
       this.goodsManager.incViewCount(goods_id);
     }
   }

   public IGoodsManager getGoodsManager() {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\visited\plugin\VisitedGoodsPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */