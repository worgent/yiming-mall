 package com.enation.app.shop.component.product.plugin.allocation;

 import com.enation.app.shop.core.model.AllocationItem;
 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.plugin.order.IOrderAllocationItemEvent;
 import com.enation.app.shop.core.service.IGoodsStoreManager;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.sql.ResultSet;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;













 @Component
 public class GenericAllocationPlugin
   extends AutoRegisterPlugin
   implements IOrderAllocationItemEvent
 {
   private IGoodsStoreManager goodsStoreManager;

   public void filterAlloViewItem(Map colValues, ResultSet rs) {}

   public String getAllocationStoreHtml(OrderItem item)
   {
     List<Map> storeList = this.goodsStoreManager.listProductStore(item.getProduct_id());
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getCurrInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("item", item);
     freeMarkerPaser.putData("storeList", storeList);
     return freeMarkerPaser.proessPageContent();
   }






   public void onAllocation(AllocationItem allocationItem) {}





   public String getAllocationViewHtml(OrderItem item)
   {
     List<Map> storeList = this.goodsStoreManager.listProductAllo(item.getOrder_id(), item.getItem_id());

     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getCurrInstance();
     freeMarkerPaser.setClz(getClass());
     freeMarkerPaser.putData("item", item);
     freeMarkerPaser.putData("storeList", storeList);
     freeMarkerPaser.setPageName("view_allocation");
     return freeMarkerPaser.proessPageContent();
   }



   public boolean canBeExecute(int catid)
   {
     return true;
   }


   public IGoodsStoreManager getGoodsStoreManager()
   {
     return this.goodsStoreManager;
   }

   public void setGoodsStoreManager(IGoodsStoreManager goodsStoreManager) {
     this.goodsStoreManager = goodsStoreManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\product\plugin\allocation\GenericAllocationPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */