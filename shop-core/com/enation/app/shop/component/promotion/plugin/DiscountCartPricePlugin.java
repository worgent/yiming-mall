 package com.enation.app.shop.component.promotion.plugin;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.model.MemberLv;
 import com.enation.app.shop.core.model.GoodsLvPrice;
 import com.enation.app.shop.core.model.support.CartItem;
 import com.enation.app.shop.core.plugin.cart.ICartItemFilter;
 import com.enation.app.shop.core.service.IGoodsManager;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.app.shop.core.service.IMemberPriceManager;
 import com.enation.app.shop.core.service.IPromotionManager;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.CurrencyUtil;
import org.springframework.stereotype.Component;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;












 @Component
 public class DiscountCartPricePlugin
   extends AutoRegisterPlugin
   implements ICartItemFilter
 {
   private IPromotionManager promotionManager;
   private IMemberPriceManager memberPriceManager;
   private IMemberLvManager memberLvManager;
   private IGoodsManager goodsManager;

   public void filter(List<CartItem> list, String sessionid)
   {
     IUserService userService = UserServiceFactory.getUserService();
     Member member = userService.getCurrentMember();
     List<GoodsLvPrice> memPriceList = new ArrayList();
     double discount = 1.0D;
     if ((member != null) && (member.getLv_id() != null)) {
       this.promotionManager.applyGoodsPmt(list, member.getLv_id());
       memPriceList = this.memberPriceManager.listPriceByLvid(member.getLv_id().intValue());
       MemberLv lv = this.memberLvManager.get(member.getLv_id());
       discount = lv.getDiscount().intValue() / 100.0D;
       applyMemPrice(list, memPriceList, discount);
     }
   }








































   private double getMemberPrice(List<GoodsLvPrice> memPriceList, int goodsId)
   {
     for (GoodsLvPrice lvPrice : memPriceList) {
       if (goodsId == lvPrice.getGoodsid()) {
         return lvPrice.getPrice().doubleValue();
       }
     }
     return 0.0D;
   }





   private int getCatDicount(List discountList, int catId)
   {
     for (int i = 0; i < discountList.size(); i++) {
       Map map = (Map)discountList.get(i);
       Integer cat_id = (Integer)map.get("cat_id");
       Integer discount = (Integer)map.get("discount");
       if (cat_id.intValue() == catId) {
         return discount.intValue();
       }
     }
     return 0;
   }






   private void applyMemPrice(List<CartItem> itemList, List<GoodsLvPrice> memPriceList, double discount)
   {
     for (CartItem item : itemList) {
       double oldprice = item.getPrice().doubleValue();
       if (item.getCoupPrice().doubleValue() >= oldprice) {
         double price = CurrencyUtil.mul(item.getPrice().doubleValue(), discount).doubleValue();
         for (GoodsLvPrice lvPrice : memPriceList) {
           if (item.getProduct_id().intValue() == lvPrice.getProductid()) {
             price = lvPrice.getPrice().doubleValue();
           }
         }

         item.setPrice(Double.valueOf(oldprice));
         item.setCoupPrice(Double.valueOf(price));
       }
     }
   }

   public IPromotionManager getPromotionManager() {
     return this.promotionManager;
   }

   public void setPromotionManager(IPromotionManager promotionManager) {
     this.promotionManager = promotionManager;
   }

   public IMemberPriceManager getMemberPriceManager() {
     return this.memberPriceManager;
   }

   public void setMemberPriceManager(IMemberPriceManager memberPriceManager) {
     this.memberPriceManager = memberPriceManager;
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager) {
     this.memberLvManager = memberLvManager;
   }


   public IGoodsManager getGoodsManager()
   {
     return this.goodsManager;
   }

   public void setGoodsManager(IGoodsManager goodsManager) {
     this.goodsManager = goodsManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\promotion\plugin\DiscountCartPricePlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */