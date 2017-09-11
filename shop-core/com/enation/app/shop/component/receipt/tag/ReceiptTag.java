 package com.enation.app.shop.component.receipt.tag;

 import com.enation.app.shop.component.receipt.Receipt;
 import com.enation.app.shop.component.receipt.service.IReceiptManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;
















 @Component
 @Scope("prototype")
 public class ReceiptTag
   extends BaseFreeMarkerTag
 {
   private IReceiptManager receiptManager;
   private IOrderManager orderManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     Integer orderid = (Integer)params.get("orderid");
     Receipt receipt = this.receiptManager.getByOrderid(orderid);
     if (receipt == null) {
       receipt = new Receipt();
     }
     return receipt;
   }

   public IReceiptManager getReceiptManager()
   {
     return this.receiptManager;
   }

   public void setReceiptManager(IReceiptManager receiptManager) {
     this.receiptManager = receiptManager;
   }

   public IOrderManager getOrderManager() {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager) {
     this.orderManager = orderManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\receipt\tag\ReceiptTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */