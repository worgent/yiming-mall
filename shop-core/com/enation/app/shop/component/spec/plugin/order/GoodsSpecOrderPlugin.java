 package com.enation.app.shop.component.spec.plugin.order;

 import com.enation.app.shop.component.product.plugin.order.GenericOrderPlugin;
 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.plugin.order.IOrderItemFilter;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONArray;
 import org.springframework.stereotype.Component;
















 @Component
 public class GoodsSpecOrderPlugin
   extends GenericOrderPlugin
   implements IOrderItemFilter
 {
   public void filter(Integer orderid, List<OrderItem> itemList)
   {
     for (OrderItem item : itemList) {
       String addon = item.getAddon();
       if (!StringUtil.isEmpty(addon)) {
         JSONArray specArray = JSONArray.fromObject(addon);
         List<Map> specList = (List)JSONArray.toCollection(specArray, Map.class);
         FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
         freeMarkerPaser.setClz(getClass());
         freeMarkerPaser.putData("specList", specList);
         freeMarkerPaser.setPageName("order_item_spec");
         String html = freeMarkerPaser.proessPageContent();
         item.setOther(html);
       }
     }
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\plugin\order\GoodsSpecOrderPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */