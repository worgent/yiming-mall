 package com.enation.app.shop.component.receipt.plugin;

 import com.enation.app.shop.component.receipt.Receipt;
 import com.enation.app.shop.component.receipt.service.IReceiptManager;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.plugin.order.IOrderTabShowEvent;
 import com.enation.app.shop.core.plugin.order.IShowOrderDetailHtmlEvent;
 import com.enation.eop.processor.core.freemarker.FreeMarkerPaser;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.plugin.IAjaxExecuteEnable;
 import org.springframework.stereotype.Component;













 @Component
 public class OrderDetailReceiptPlugin
   extends AutoRegisterPlugin
   implements IOrderTabShowEvent, IShowOrderDetailHtmlEvent, IAjaxExecuteEnable
 {
   private IReceiptManager receiptManager;

   public String execute()
   {
     return null;
   }

   public String onShowOrderDetailHtml(Order order)
   {
     FreeMarkerPaser freeMarkerPaser = FreeMarkerPaser.getInstance();
     freeMarkerPaser.setClz(getClass());

     Receipt receipt = this.receiptManager.getByOrderid(order.getOrder_id());

     freeMarkerPaser.putData("receipt", receipt);
     freeMarkerPaser.setPageName("receipt_detail");
     return freeMarkerPaser.proessPageContent();
   }









   public String getTabName(Order order)
   {
     return "发票";
   }


   public int getOrder()
   {
     return 11;
   }


   public boolean canBeExecute(Order order)
   {
     return true;
   }

   public IReceiptManager getReceiptManager()
   {
     return this.receiptManager;
   }

   public void setReceiptManager(IReceiptManager receiptManager) {
     this.receiptManager = receiptManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\receipt\plugin\OrderDetailReceiptPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */