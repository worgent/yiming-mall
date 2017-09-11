 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;












 @Component
 @Scope("prototype")
 public class CheckoutOrderTotalTag
   extends BaseFreeMarkerTag
 {
   private ICartManager cartManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String sessionid = request.getSession().getId();
     Integer regionId = (Integer)params.get("regionId");
     Integer typeId = (Integer)params.get("typeId");
     OrderPrice orderPrice = new OrderPrice();
     if ((regionId != null) && (typeId != null))
       orderPrice = this.cartManager.countPrice(this.cartManager.listGoods(sessionid), typeId, regionId.toString());
     return orderPrice;
   }

   public ICartManager getCartManager() {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) {
     this.cartManager = cartManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\CheckoutOrderTotalTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */