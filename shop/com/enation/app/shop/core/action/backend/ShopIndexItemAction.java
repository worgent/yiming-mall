 package com.enation.app.shop.core.action.backend;

 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IOrderManager;
 import com.enation.framework.action.WWAction;
 import java.util.Map;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.apache.struts2.convention.annotation.Results;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;











 @Component
 @Scope("prototype")
 @ParentPackage("shop_default")
 @Namespace("/shop/admin")
 @Action("indexItem")
 @Results({@org.apache.struts2.convention.annotation.Result(name="order", location="/shop/admin/index/order.jsp"), @org.apache.struts2.convention.annotation.Result(name="goods", location="/shop/admin/index/goods.jsp")})
 public class ShopIndexItemAction
   extends WWAction
 {
   private IOrderManager orderManager;
   private IGoodsManager goodsManager;
   private Map orderss;
   private Map goodsss;

   public String order()
   {
     this.orderss = this.orderManager.censusState();
     return "order";
   }


   public String goods()
   {
     this.goodsss = this.goodsManager.census();
     return "goods";
   }

   public IOrderManager getOrderManager()
   {
     return this.orderManager;
   }

   public void setOrderManager(IOrderManager orderManager)
   {
     this.orderManager = orderManager;
   }

   public IGoodsManager getGoodsManager()
   {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager)
   {
     this.goodsManager = goodsManager;
   }

   public Map getOrderss()
   {
     return this.orderss;
   }

   public void setOrderss(Map orderss)
   {
     this.orderss = orderss;
   }

   public Map getGoodsss()
   {
     return this.goodsss;
   }

   public void setGoodsss(Map goodsss)
   {
     this.goodsss = goodsss;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\backend\ShopIndexItemAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */