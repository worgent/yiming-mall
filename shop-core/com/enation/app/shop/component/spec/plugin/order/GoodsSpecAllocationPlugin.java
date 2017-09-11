 package com.enation.app.shop.component.spec.plugin.order;

 import com.enation.app.shop.core.model.AllocationItem;
 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.plugin.order.IOrderAllocationItemEvent;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.List;
 import java.util.Map;
 import net.sf.json.JSONArray;
 import org.springframework.stereotype.Component;










 @Component
 public class GoodsSpecAllocationPlugin
   extends AutoRegisterPlugin
   implements IOrderAllocationItemEvent
 {
   private IOrderAllocationItemEvent genericAllocationPlugin;

   public String getAllocationStoreHtml(OrderItem item)
   {
     return this.genericAllocationPlugin.getAllocationStoreHtml(item);
   }


   public String getAllocationViewHtml(OrderItem item)
   {
     return this.genericAllocationPlugin.getAllocationViewHtml(item);
   }

   public void onAllocation(AllocationItem allocationItem)
   {
     this.genericAllocationPlugin.onAllocation(allocationItem);
   }


   public void filterAlloViewItem(Map colValues, ResultSet rs)
     throws SQLException
   {
     String addon = rs.getString("addon");

     if (!StringUtil.isEmpty(addon)) {
       JSONArray specArray = JSONArray.fromObject(addon);
       List<Map> specList = (List)JSONArray.toCollection(specArray, Map.class);
       FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
       freeMarkerPaser.setClz(getClass());
       freeMarkerPaser.putData("specList", specList);
       freeMarkerPaser.setPageName("order_item_spec");
       String html = freeMarkerPaser.proessPageContent();
       colValues.put("other", html);
     }
   }





   public boolean canBeExecute(int catid)
   {
     return true;
   }





   public IOrderAllocationItemEvent getGenericAllocationPlugin()
   {
     return this.genericAllocationPlugin;
   }

   public void setGenericAllocationPlugin(IOrderAllocationItemEvent genericAllocationPlugin)
   {
     this.genericAllocationPlugin = genericAllocationPlugin;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\plugin\order\GoodsSpecAllocationPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */