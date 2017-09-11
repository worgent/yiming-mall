 package com.enation.app.shop.component.receipt.service;

 import com.enation.app.shop.component.receipt.Receipt;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import java.util.List;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;






 @Component
 public class ReceiptManager
   extends BaseSupport<Receipt>
   implements IReceiptManager
 {
   @Transactional(propagation=Propagation.REQUIRED)
   public void add(Receipt invoice)
   {
     invoice.setAdd_time(Long.valueOf(System.currentTimeMillis()));
     this.baseDaoSupport.insert("receipt", invoice);
   }



   public Receipt getByOrderid(Integer orderid)
   {
     List list = this.baseDaoSupport.queryForList("select * from receipt where order_id = ? ", Receipt.class, new Object[] { orderid });
     if (list.size() == 0) {
       return null;
     }
     return (Receipt)list.get(0);
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\receipt\service\ReceiptManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */