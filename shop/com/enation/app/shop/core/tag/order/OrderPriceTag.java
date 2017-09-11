 package com.enation.app.shop.core.tag.order;

 import com.enation.app.base.core.model.MemberAddress;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.app.shop.core.service.IMemberAddressManager;
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
 public class OrderPriceTag
   extends BaseFreeMarkerTag
 {
   private ICartManager cartManager;
   private IMemberAddressManager memberAddressManager;

   public Object exec(Map args)
     throws TemplateModelException
   {
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     String sessionid = request.getSession().getId();

     Integer addressid = (Integer)args.get("address_id");
     Integer shipping_id = (Integer)args.get("shipping_id");
     String regionid = null;


     if (addressid != null) {
       MemberAddress address = this.memberAddressManager.getAddress(addressid.intValue());
       regionid = "" + address.getRegion_id();
     }

     OrderPrice orderprice = this.cartManager.countPrice(this.cartManager.listGoods(sessionid), shipping_id, regionid);
     return orderprice;
   }

   public ICartManager getCartManager() {
     return this.cartManager;
   }

   public void setCartManager(ICartManager cartManager) {
     this.cartManager = cartManager;
   }

   public IMemberAddressManager getMemberAddressManager() {
     return this.memberAddressManager;
   }

   public void setMemberAddressManager(IMemberAddressManager memberAddressManager) {
     this.memberAddressManager = memberAddressManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\tag\order\OrderPriceTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */