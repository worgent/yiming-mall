 package com.enation.app.b2b2c.component.plugin.order;

 import com.enation.app.b2b2c.core.model.cart.StoreCartItem;
 import com.enation.app.b2b2c.core.model.order.StoreOrder;
 import com.enation.app.b2b2c.core.service.cart.IStoreCartManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.model.DlyType;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.OrderItem;
 import com.enation.app.shop.core.model.OrderLog;
 import com.enation.app.shop.core.model.support.CartItem;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.plugin.order.IAfterOrderCreateEvent;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.IOrderFlowManager;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import com.enation.framework.util.DateUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.beanutils.BeanUtils;
 import org.springframework.stereotype.Component;








 @Component
 public class B2b2cOrderPlugin
   extends AutoRegisterPlugin
   implements IAfterOrderCreateEvent
 {
   private IDaoSupport daoSupport;
   private IStoreCartManager storeCartManager;
   private ICartManager cartManager;
   private IDlyTypeManager dlyTypeManager;
   private IOrderFlowManager orderFlowManager;
   private IStoreMemberManager storeMemberManager;

   public void onAfterOrderCreate(Order order, List<CartItem> itemList, String sessionid)
   {
     Member member = this.storeMemberManager.getStoreMember();

     List<Map> storeGoodsList = this.storeCartManager.storeListGoods(sessionid);
     HttpServletRequest request = ThreadContextHolder.getHttpRequest();
     StoreOrder store_order = new StoreOrder();
     Double goodsPrice = Double.valueOf(0.0D);
     Double orderprice = Double.valueOf(0.0D);
     Double discountPrice = Double.valueOf(0.0D);
     Double shippingPrice = Double.valueOf(0.0D);
     Double needPayMoney = Double.valueOf(0.0D);
     try {
       BeanUtils.copyProperties(store_order, order);
     } catch (Exception e) {
       e.printStackTrace();
     }
     int num = 0;

     String[] shippingIds = request.getParameterValues("shippingId");

     for (Map map : storeGoodsList) {
       int store_id = Integer.parseInt(map.get("store_id").toString());
       Integer shippingId = Integer.valueOf(Integer.parseInt(shippingIds[num]));
       store_order.setStore_id(Integer.valueOf(store_id));
       store_order.setParent_id(order.getOrder_id());

       List<StoreCartItem> list = (List)map.get("goodslist");

       OrderPrice orderPrice = this.storeCartManager.countPrice(list, order.getRegionid() + "", shippingId, Boolean.valueOf(false));

       store_order.setGoods_amount(orderPrice.getGoodsPrice());
       store_order.setWeight(orderPrice.getWeight());

       store_order.setDiscount(orderPrice.getDiscountPrice());
       store_order.setOrder_amount(orderPrice.getOrderPrice());
       store_order.setProtect_price(orderPrice.getProtectPrice());
       store_order.setShipping_amount(orderPrice.getShippingPrice());
       store_order.setGainedpoint(orderPrice.getPoint().intValue());
       store_order.setNeed_pay_money(orderPrice.getNeedPayMoney());

       discountPrice = Double.valueOf(discountPrice.doubleValue() + orderPrice.getDiscountPrice().doubleValue());
       shippingPrice = Double.valueOf(shippingPrice.doubleValue() + orderPrice.getShippingPrice().doubleValue());
       needPayMoney = Double.valueOf(needPayMoney.doubleValue() + orderPrice.getNeedPayMoney().doubleValue());
       orderprice = Double.valueOf(orderprice.doubleValue() + orderPrice.getOrderPrice().doubleValue());
       goodsPrice = Double.valueOf(goodsPrice.doubleValue() + orderPrice.getGoodsPrice().doubleValue());

       String shipName = null;
       if ((shippingId != null) && (shippingId.intValue() != 0)) {
         shipName = this.dlyTypeManager.getDlyTypeById(shippingId).getName();
       } else {
         shipName = "卖家承担运费！";
       }
       store_order.setShipping_type(shipName);


       store_order.setSn(store_order.getSn().substring(0, store_order.getSn().length() - 1) + num);
       this.daoSupport.insert("es_order", store_order);

       store_order.setOrder_id(Integer.valueOf(this.daoSupport.getLastId("es_order")));
       saveGoodsItem(list, store_order.getOrder_id());


       OrderLog log = new OrderLog();
       log.setMessage("订单创建");
       log.setOp_name(member.getName());
       log.setOrder_id(store_order.getOrder_id());
       addLog(log);

       if (!store_order.getIsCod()) {
         this.orderFlowManager.confirmOrder(store_order.getOrder_id());
       }
       num++;
     }
     updateOrderPrice(order.getOrder_id(), goodsPrice, orderprice, discountPrice, shippingPrice, needPayMoney);
   }


   private void updateOrderPrice(Integer order_id, Double goodsPrice, Double orderprice, Double discountPrice, Double shippingPrice, Double needPayMoney)
   {
     Map map = new HashMap();
     map.put("goods_amount", goodsPrice);
     map.put("order_amount", orderprice);
     map.put("discount", discountPrice);
     map.put("shipping_amount", shippingPrice);
     map.put("need_pay_money", needPayMoney);
     this.daoSupport.update("es_order", map, "order_id=" + order_id);
   }




   private void saveGoodsItem(List<StoreCartItem> itemList, Integer order_id)
   {
     for (int i = 0; i < itemList.size(); i++)
     {
       OrderItem orderItem = new OrderItem();

       CartItem cartItem = (CartItem)itemList.get(i);
       orderItem.setPrice(cartItem.getCoupPrice());
       orderItem.setName(cartItem.getName());
       orderItem.setNum(Integer.valueOf(cartItem.getNum()));

       orderItem.setGoods_id(cartItem.getGoods_id());
       orderItem.setShip_num(Integer.valueOf(0));
       orderItem.setProduct_id(cartItem.getProduct_id());
       orderItem.setOrder_id(order_id);
       orderItem.setGainedpoint(cartItem.getPoint().intValue());
       orderItem.setAddon(cartItem.getAddon());


       orderItem.setSn(cartItem.getSn());
       orderItem.setImage(cartItem.getImage_default());
       orderItem.setCat_id(cartItem.getCatid());

       orderItem.setUnit(cartItem.getUnit());
       this.daoSupport.insert("es_order_items", orderItem);
     }
   }




   private void addLog(OrderLog log)
   {
     log.setOp_time(Long.valueOf(DateUtil.getDatelineLong()));
     this.daoSupport.insert("es_order_log", log);
   }

   public IStoreCartManager getStoreCartManager() { return this.storeCartManager; }

   public void setStoreCartManager(IStoreCartManager storeCartManager) {
     this.storeCartManager = storeCartManager;
   }

   public ICartManager getCartManager() { return this.cartManager; }

   public void setCartManager(ICartManager cartManager) {
     this.cartManager = cartManager;
   }

   public IDaoSupport getDaoSupport() { return this.daoSupport; }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }

   public IDlyTypeManager getDlyTypeManager() { return this.dlyTypeManager; }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }

   public IOrderFlowManager getOrderFlowManager() { return this.orderFlowManager; }

   public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
     this.orderFlowManager = orderFlowManager;
   }

   public IStoreMemberManager getStoreMemberManager() { return this.storeMemberManager; }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\component\plugin\order\B2b2cOrderPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */