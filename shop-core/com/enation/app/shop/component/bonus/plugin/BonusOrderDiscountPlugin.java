 package com.enation.app.shop.component.bonus.plugin;

 import com.enation.app.shop.component.bonus.model.MemberBonus;
 import com.enation.app.shop.component.bonus.service.BonusSession;
 import com.enation.app.shop.component.bonus.service.IBonusManager;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderMeta;
 import com.enation.app.shop.core.model.support.CartItem;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.plugin.cart.ICountPriceEvent;
 import com.enation.app.shop.core.plugin.order.IAfterOrderCreateEvent;
 import com.enation.app.shop.core.plugin.order.IOrderCanelEvent;
 import com.enation.app.shop.core.service.IOrderMetaManager;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.CurrencyUtil;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Map;
 import org.springframework.context.annotation.Scope;
 import org.springframework.stereotype.Component;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;







 @Component
 @Scope("prototype")
 public class BonusOrderDiscountPlugin
   extends AutoRegisterPlugin
   implements ICountPriceEvent, IAfterOrderCreateEvent, IOrderCanelEvent
 {
   private IOrderMetaManager orderMetaManager;
   private final String discount_key = "bonusdiscount";



   private IBonusManager bonusManager;



   @Transactional(propagation=Propagation.REQUIRED)
   public void onAfterOrderCreate(Order order, List<CartItem> arg1, String arg2)
   {
     List<MemberBonus> bonusList = BonusSession.get();
     if ((bonusList == null) || (bonusList.isEmpty())) {
       bonusList = new ArrayList();
     }


     MemberBonus bonus = BonusSession.getOne();
     if (bonus != null) {
       bonusList.add(bonus);
     }

     for (MemberBonus memberBonus : bonusList) {
       int bonusid = memberBonus.getBonus_id();
       int bonusTypeid = memberBonus.getBonus_type_id();
       this.bonusManager.use(bonusid, order.getMember_id().intValue(), order.getOrder_id().intValue(), order.getSn(), bonusTypeid);
     }

     OrderPrice orderPrice = order.getOrderprice();
     Map disItems = orderPrice.getDiscountItem();

     Double bonusdiscount = (Double)disItems.get("bonusdiscount");

     OrderMeta orderMeta = new OrderMeta();
     orderMeta.setOrderid(order.getOrder_id().intValue());

     if (bonusdiscount != null) {
       orderMeta.setMeta_key("bonusdiscount");
       orderMeta.setMeta_value(String.valueOf(bonusdiscount));
       this.orderMetaManager.add(orderMeta);
     }


     BonusSession.cleanAll();
   }


   public OrderPrice countPrice(OrderPrice orderprice)
   {
     Map<String, Object> disItems = orderprice.getDiscountItem();
     double moneyCount = BonusSession.getUseMoney();

     disItems.put("bonusdiscount", Double.valueOf(moneyCount));


     double needPay = orderprice.getNeedPayMoney().doubleValue();
     needPay = CurrencyUtil.sub(needPay, moneyCount);



     orderprice.setDiscountPrice(Double.valueOf(CurrencyUtil.add(orderprice.getDiscountPrice().doubleValue(), moneyCount)));
     if (needPay < 0.0D) {
       orderprice.setNeedPayMoney(Double.valueOf(0.0D));
     } else {
       orderprice.setNeedPayMoney(Double.valueOf(needPay));
     }

     return orderprice;
   }



   @Transactional(propagation=Propagation.REQUIRED)
   public void canel(Order order)
   {
     this.bonusManager.returned(order.getOrder_id().intValue());
   }

   public IOrderMetaManager getOrderMetaManager() {
     return this.orderMetaManager;
   }

   public void setOrderMetaManager(IOrderMetaManager orderMetaManager) {
     this.orderMetaManager = orderMetaManager;
   }

   public IBonusManager getBonusManager() {
     return this.bonusManager;
   }

   public void setBonusManager(IBonusManager bonusManager) {
     this.bonusManager = bonusManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\bonus\plugin\BonusOrderDiscountPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */