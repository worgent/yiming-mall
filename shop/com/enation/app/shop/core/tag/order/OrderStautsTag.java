 package com.enation.app.shop.core.tag.order;

 import com.enation.app.shop.core.service.OrderStatus;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;













 @Component
 @Scope("prototype")
 public class OrderStautsTag
   extends BaseFreeMarkerTag
 {
   protected Object exec(Map params)
     throws TemplateModelException
   {
     return OrderStatus.getOrderStatusMap();
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\order\OrderStautsTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */