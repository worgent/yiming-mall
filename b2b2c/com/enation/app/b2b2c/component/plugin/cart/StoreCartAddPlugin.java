 package com.enation.app.b2b2c.component.plugin.cart;

 import com.enation.app.shop.core.model.Cart;
 import com.enation.app.shop.core.plugin.cart.ICartAddEvent;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.stereotype.Component;


 @Component
 public class StoreCartAddPlugin
   extends AutoRegisterPlugin
   implements ICartAddEvent
 {
   private IDaoSupport daoSupport;

   public void add(Cart cart) {}

   public void afterAdd(Cart cart)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String store_id = request.getParameter("store_id");
     this.daoSupport.execute("update es_cart set store_id=? where cart_id=?", new Object[] { store_id, cart.getCart_id() });
   }

   public IDaoSupport getDaoSupport() {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\cart\StoreCartAddPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */