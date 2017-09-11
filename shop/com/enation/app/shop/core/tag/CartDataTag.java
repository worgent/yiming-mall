 package com.enation.app.shop.core.tag;

 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.taglib.BaseFreeMarkerTag;
 import freemarker.template.TemplateModelException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;












 @Component
 @Scope("prototype")
 public class CartDataTag
   extends BaseFreeMarkerTag
 {
   private ICartManager cartManager;

   protected Object exec(Map params)
     throws TemplateModelException
   {
     String sessionid = ThreadContextHolder.getHttpRequest().getSession().getId();

     Double goodsTotal = this.cartManager.countGoodsTotal(sessionid);
     int count = this.cartManager.countItemNum(sessionid).intValue();

     Map<String, Object> data = new HashMap();
     data.put("count", Integer.valueOf(count));
     data.put("total", goodsTotal);
     return data;
   }

   public ICartManager getCartManager() { return this.cartManager; }

   public void setCartManager(ICartManager cartManager) {
     this.cartManager = cartManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\CartDataTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */