 package com.enation.app.shop.core.action.api;

 import com.enation.app.shop.core.model.Cart;
 import com.enation.app.shop.core.model.Product;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.app.shop.core.service.IProductManager;
 import com.enation.app.shop.core.service.IPromotionManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.JsonMessageUtil;
 import com.enation.framework.util.StringUtil;
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
 @Namespace("/api/shop")
 @Action("cart")
 public class CartApiAction
   extends WWAction
 {
   private ICartManager cartManager;
   private IPromotionManager promotionManager;
   private int goodsid;
   private int productid;
   private int num;
   private IProductManager productManager;
   private int showCartData;

   public String addProduct()
   {
     Product product = this.productManager.get(Integer.valueOf(this.productid));
     addProductToCart(product);

     return "json_message";
   }














   public String addGoods()
   {
     Product product = this.productManager.getByGoodsId(Integer.valueOf(this.goodsid));
     addProductToCart(product);


     return "json_message";
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






   private boolean addProductToCart(Product product)
   {
     String sessionid = ThreadContextHolder.getHttpRequest().getSession().getId();

     if (product != null) {
       try {
         if (product.getEnable_store() < this.num) {
           throw new RuntimeException("抱歉！您所选选择的货品库存不足。");
         }
         Cart cart = new Cart();
         cart.setGoods_id(product.getGoods_id());
         cart.setProduct_id(product.getProduct_id());
         cart.setSession_id(sessionid);
         cart.setNum(Integer.valueOf(this.num));
         cart.setItemtype(Integer.valueOf(0));
         cart.setWeight(product.getWeight());
         cart.setPrice(product.getPrice());
         cart.setName(product.getName());

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










   public String delete()
   {
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       String cartid = request.getParameter("cartid");
       this.cartManager.delete(request.getSession().getId(), Integer.valueOf(cartid));
       showSuccessJson("删除成功");
     } catch (RuntimeException e) {
       this.logger.error("删除购物项失败", e);
       showErrorJson("删除购物项失败");
     }
     return "json_message";
   }







   public String updateNum()
   {
     try
     {
       HttpServletRequest request = ThreadContextHolder.getHttpRequest();
       String cartid = request.getParameter("cartid");
       String num = request.getParameter("num");
       num = StringUtil.isEmpty(num) ? "1" : num;
       String productid = request.getParameter("productid");
       Product product = this.productManager.get(Integer.valueOf(productid));
       Integer store = Integer.valueOf(product.getEnable_store());
       if (store == null)
         store = Integer.valueOf(0);
       if (store.intValue() >= Integer.valueOf(num).intValue()) {
         this.cartManager.updateNum(request.getSession().getId(), Integer.valueOf(cartid), Integer.valueOf(num));
       }
       this.json = JsonMessageUtil.getNumberJson("store", store);
     } catch (RuntimeException e) {
       this.logger.error("更新购物车数量出现意外错误", e);
       showErrorJson("更新购物车数量出现意外错误" + e.getMessage());
     }
     return "json_message";
   }











   public String getTotal()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String sessionid = request.getSession().getId();
     OrderPrice orderprice = this.cartManager.countPrice(this.cartManager.listGoods(sessionid), null, null);
     this.json = JsonMessageUtil.getObjectJson(orderprice);
     return "json_message";
   }




   public String clean()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     try {
       this.cartManager.clean(request.getSession().getId());
       showSuccessJson("清空购物车成功");
     } catch (RuntimeException e) {
       this.logger.error("清空购物车", e);
       showErrorJson(e.getMessage());
     }
     return "json_message";
   }

   public ICartManager getCartManager() {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) { this.cartManager = cartManager; }


   public int getGoodsid()
   {
     return this.goodsid;
   }

   public void setGoodsid(int goodsid)
   {
     this.goodsid = goodsid;
   }

   public int getProductid()
   {
     return this.productid;
   }

   public void setProductid(int productid)
   {
     this.productid = productid;
   }

   public IProductManager getProductManager()
   {
     return this.productManager;
   }

   public void setProductManager(IProductManager productManager)
   {
     this.productManager = productManager;
   }

   public int getNum()
   {
     return this.num;
   }

   public void setNum(int num)
   {
     this.num = num;
   }



   public int getShowCartData()
   {
     return this.showCartData;
   }



   public void setShowCartData(int showCartData)
   {
     this.showCartData = showCartData;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\CartApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */