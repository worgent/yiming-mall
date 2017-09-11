 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;











 @Component
 @Scope("prototype")
 public class ShopPaymentListTag
   extends BaseFreeMarkerTag
 {
   private IPaymentManager paymentManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     List paymentList = this.paymentManager.list();
     return paymentList;
   }

   public IPaymentManager getPaymentManager() {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager) { this.paymentManager = paymentManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\ShopPaymentListTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */