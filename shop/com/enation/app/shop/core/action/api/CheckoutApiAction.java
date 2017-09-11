 package com.enation.app.shop.core.action.api;

 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.IMemberAddressManager;
 import com.enation.app.shop.core.service.IPaymentManager;
 import com.enation.framework.action.WWAction;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.util.JsonMessageUtil;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.apache.struts2.convention.annotation.Action;
 import org.apache.struts2.convention.annotation.Namespace;
 import org.apache.struts2.convention.annotation.ParentPackage;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;



















 @Component
 @Scope("prototype")
 @ParentPackage("eop_default")
 @Namespace("/api/shop")
 @Action("checkout")
 public class CheckoutApiAction
   extends WWAction
 {
   private IMemberAddressManager memberAddressManager;
   private ICartManager cartManager;
   private IDlyTypeManager dlyTypeManager;
   private IPaymentManager paymentManager;
   private String regionId;
   private Integer typeId;
   private String isProtected;

   public String getShipingType()
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String sessionid = request.getSession().getId();
     Double orderPrice = this.cartManager.countGoodsTotal(sessionid);
     Double weight = this.cartManager.countGoodsWeight(sessionid);

     List<DlyType> dlyTypeList = this.dlyTypeManager.list(weight, orderPrice, request.getParameter("regionid"));
     this.json = JsonMessageUtil.getListJson(dlyTypeList);

     return "json_message";
   }





   private String showPaymentList()
   {
     List paymentList = this.paymentManager.list();
     this.json = JsonMessageUtil.getListJson(paymentList);

     return "json_message";
   }



   public String getPaymentList()
   {
     return "json_message";
   }

   public String showOrderTotal() {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String sessionid = request.getSession().getId();
     OrderPrice orderPrice = this.cartManager.countPrice(this.cartManager.listGoods(sessionid), this.typeId, this.regionId);
     this.json = JsonMessageUtil.getObjectJson(orderPrice);
     return "json_message";
   }

   public IMemberAddressManager getMemberAddressManager() {
     return this.memberAddressManager;
   }

   public void setMemberAddressManager(IMemberAddressManager memberAddressManager)
   {
     this.memberAddressManager = memberAddressManager;
   }

   public ICartManager getCartManager()
   {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager)
   {
     this.cartManager = cartManager;
   }

   public IDlyTypeManager getDlyTypeManager()
   {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager)
   {
     this.dlyTypeManager = dlyTypeManager;
   }

   public IPaymentManager getPaymentManager()
   {
     return this.paymentManager;
   }

   public void setPaymentManager(IPaymentManager paymentManager)
   {
     this.paymentManager = paymentManager;
   }

   public String getRegionId() {
     return this.regionId;
   }

   public void setRegionId(String regionId) {
     this.regionId = regionId;
   }

   public Integer getTypeId() {
     return this.typeId;
   }

   public void setTypeId(Integer typeId) {
     this.typeId = typeId;
   }

   public String getIsProtected() {
     return this.isProtected;
   }

   public void setIsProtected(String isProtected) {
     this.isProtected = isProtected;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\action\api\CheckoutApiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */