 package com.enation.app.b2b2c.core.action.api.cart;

 import com.enation.app.b2b2c.core.model.StoreProduct;
 import com.enation.app.b2b2c.core.model.cart.StoreCart;
 import com.enation.app.b2b2c.core.service.cart.IStoreProductManager;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.JsonMessageUtil;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.apache.log4j.Logger;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;







 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/store")
 @Action("storeCart")
 public class StoreCartApiAction
   extends WWAction
 {
   private int num;
   private int goodsid;
   private ICartManager cartManager;
   private int showCartData;
   private IStoreProductManager storeProductManager;

   private boolean addProductToCart(StoreProduct product)
   {
     String sessionid = ThreadContextHolder.getHttpRequest().getSession().getId();

     if (product != null) {
       try {
         if ((product.getStore() == null) || (product.getStore().intValue() < this.num)) {
           throw new RuntimeException("抱歉！您所选选择的货品库存不足。");
         }
         StoreCart cart = new StoreCart();
         cart.setGoods_id(product.getGoods_id());
         cart.setProduct_id(product.getProduct_id());
         cart.setSession_id(sessionid);
         cart.setNum(Integer.valueOf(this.num));
         cart.setItemtype(Integer.valueOf(0));
         cart.setWeight(product.getWeight());
         cart.setPrice(product.getPrice());
         cart.setName(product.getName());
         cart.setStore_id(product.getStore_id());
         this.cartManager.add(cart);
         showSuccessJson("货品成功添加到购物车");



         if (this.showCartData == 1) {
           getCartData();
         }


         return true;
       } catch (RuntimeException e) {
         this.logger.error("将货品添加至购物车出错", e);
         showErrorJson("将货品添加至购物车出错[" + e.getMessage() + "]");
         return false;
       }
     }

     showErrorJson("该货品不存在，未能添加到购物车");
     return false;
   }










   public String getCartData()
   {
     try
     {
       String sessionid = ThreadContextHolder.getHttpRequest().getSession().getId();

       Double goodsTotal = this.cartManager.countGoodsTotal(sessionid);
       int count = this.cartManager.countItemNum(sessionid).intValue();

       Map<String, Object> data = new HashMap();
       data.put("count", Integer.valueOf(count));
       data.put("total", goodsTotal);

       this.json = JsonMessageUtil.getObjectJson(data);
     }
     catch (Throwable e) {
       this.logger.error("获取购物车数据出错", e);
       showErrorJson("获取购物车数据出错[" + e.getMessage() + "]");
     }

     return "json_message";
   }










   public String addGoods()
   {
     StoreProduct product = this.storeProductManager.getByGoodsId(Integer.valueOf(this.goodsid));
     addProductToCart(product);
     return "json_message";
   }

   public int getNum() {
     return this.num;
   }

   public void setNum(int num) { this.num = num; }

   public int getGoodsid() {
     return this.goodsid;
   }

   public void setGoodsid(int goodsid) { this.goodsid = goodsid; }

   public ICartManager getCartManager() {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) { this.cartManager = cartManager; }

   public int getShowCartData() {
     return this.showCartData;
   }

   public void setShowCartData(int showCartData) { this.showCartData = showCartData; }

   public IStoreProductManager getStoreProductManager() {
     return this.storeProductManager;
   }

   public void setStoreProductManager(IStoreProductManager storeProductManager) { this.storeProductManager = storeProductManager; }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\action\api\cart\StoreCartApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */