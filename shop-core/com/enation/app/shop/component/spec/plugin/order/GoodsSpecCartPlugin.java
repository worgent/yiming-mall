 package com.enation.app.shop.component.spec.plugin.order;

 import com.enation.app.shop.component.spec.service.ISpecManager;
 import com.enation.app.shop.core.model.Cart;
 import com.enation.app.shop.core.model.support.CartItem;
 import com.enation.app.shop.core.plugin.cart.ICartAddEvent;
 import com.enation.app.shop.core.plugin.cart.ICartItemFilter;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.StringUtil;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import net.sf.json.JSONArray;
 import org.springframework.stereotype.Component;


















 @Component
 public class GoodsSpecCartPlugin
   extends AutoRegisterPlugin
   implements ICartAddEvent, ICartItemFilter
 {
   private ISpecManager specManager;

   public void filter(List<CartItem> itemlist, String sessionid)
   {
     for (CartItem cartItem : itemlist) {
       String addon = cartItem.getAddon();
       if (!StringUtil.isEmpty(addon)) {
         JSONArray specArray = JSONArray.fromObject(addon);
         List<Map> list = (List)JSONArray.toCollection(specArray, Map.class);
         cartItem.getOthers().put("specList", list);
       }
     }
   }










   public void add(Cart cart)
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String havespec = request.getParameter("havespec");

     if ("1".equals(havespec)) {
       int productid = StringUtil.toInt(request.getParameter("productid"), true);
       List<Map<String, String>> specList = this.specManager.getProSpecList(productid);
       if ((specList != null) && (!specList.isEmpty()))
       {
         String specstr = JSONArray.fromObject(specList).toString();
         cart.setAddon(specstr);
       }
     }
   }









   public ISpecManager getSpecManager()
   {
     return this.specManager;
   }

   public void setSpecManager(ISpecManager specManager) {
     this.specManager = specManager;
   }

   public void afterAdd(Cart cart) {}
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\spec\plugin\order\GoodsSpecCartPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */