 package com.enation.app.b2b2c.core.service.cart.impl;

 import com.enation.app.b2b2c.core.model.cart.StoreCartItem;
 import com.enation.app.b2b2c.core.model.goods.StoreGoods;
 import com.enation.app.b2b2c.core.model.member.StoreMember;
 import com.enation.app.b2b2c.core.service.cart.IStoreCartManager;
 import com.enation.app.b2b2c.core.service.goods.IStoreGoodsManager;
 import com.enation.app.b2b2c.core.service.member.IStoreMemberManager;
 import com.enation.app.shop.core.model.support.DiscountPrice;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.plugin.cart.CartPluginBundle;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.IPromotionManager;
 import com.enation.eop.processor.httpcache.HttpCacheManager;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.CurrencyUtil;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import org.springframework.stereotype.Component;

 @Component
 public class StoreCartManager
   extends BaseSupport
   implements IStoreCartManager
 {
   private CartPluginBundle cartPluginBundle;
   private IDlyTypeManager dlyTypeManager;
   private IPromotionManager promotionManager;
   private IStoreGoodsManager storeGoodsManager;
   private IStoreMemberManager storeMemberManager;

   public List<StoreCartItem> listGoods(String sessionid)
   {
     StringBuffer sql = new StringBuffer();

     sql.append("select s.store_id as store_id,s.store_name as store_name,p.weight AS weight,c.cart_id as id,g.goods_id,g.thumbnail as image_default,c.name ,  p.sn, p.specs  ,g.mktprice,g.unit,g.point,p.product_id,c.price,c.cart_id as cart_id,c.num as num,c.itemtype,c.addon, (c.num*c.price) as coupPrice from " + getTableName("cart") + " c," + getTableName("product") + " p," + getTableName("goods") + " g ," + getTableName("store") + " s ");
     sql.append("where c.itemtype=0 and c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=?  AND c.store_id=s.store_id");

     List list = this.daoSupport.queryForList(sql.toString(), StoreCartItem.class, new Object[] { sessionid });

     this.cartPluginBundle.filterList(list, sessionid);


     return list;
   }

   public OrderPrice countPrice(List<StoreCartItem> storeCart, String regionid, Integer shippingid, Boolean isProtected)
   {
     OrderPrice orderPrice = new OrderPrice();

     Double weight = Double.valueOf(0.0D);

     Double originalPrice = Double.valueOf(0.0D);

     Double orderTotal = Double.valueOf(0.0D);

     Double dlyPrice = Double.valueOf(0.0D);

     Double coupPrice = Double.valueOf(0.0D);

     StoreMember member = this.storeMemberManager.getStoreMember();

     if (member == null) coupPrice = originalPrice;
     for (StoreCartItem storeCartItem : storeCart)
     {
       StoreGoods goods = this.storeGoodsManager.getGoods(storeCartItem.getGoods_id());

       if (goods.getGoods_transfee_charge().intValue() != 1) {
         weight = Double.valueOf(CurrencyUtil.add(weight.doubleValue(), CurrencyUtil.mul(goods.getWeight().doubleValue(), storeCartItem.getNum()).doubleValue()));
       }
       originalPrice = Double.valueOf(CurrencyUtil.add(originalPrice.doubleValue(), CurrencyUtil.mul(storeCartItem.getPrice().doubleValue(), storeCartItem.getNum()).doubleValue()));
       if (member != null) {
         coupPrice = Double.valueOf(CurrencyUtil.add(coupPrice.doubleValue(), CurrencyUtil.mul(storeCartItem.getPrice().doubleValue(), storeCartItem.getNum()).doubleValue()));
       }
     }







     if ((regionid != null) && (shippingid != null) && (isProtected != null)) {
       if ((shippingid.intValue() != 0) && (weight.doubleValue() != 0.0D))
       {
         Double[] priceArray = this.dlyTypeManager.countPrice(shippingid, weight, originalPrice, regionid);
         dlyPrice = priceArray[0];
       }

       if (member != null)
       {
         DiscountPrice discountPrice = this.promotionManager.applyOrderPmt(coupPrice, dlyPrice, Integer.valueOf(0), member.getLv_id());
         coupPrice = discountPrice.getOrderPrice();
         dlyPrice = discountPrice.getShipFee();
       }
     }








     Double reducePrice = Double.valueOf(CurrencyUtil.sub(originalPrice.doubleValue(), coupPrice.doubleValue()));


     orderTotal = Double.valueOf(CurrencyUtil.add(coupPrice.doubleValue(), dlyPrice.doubleValue()));

     orderPrice.setDiscountPrice(reducePrice);
     orderPrice.setGoodsPrice(coupPrice);
     orderPrice.setShippingPrice(dlyPrice);

     orderPrice.setPoint(Integer.valueOf(0));
     orderPrice.setOriginalPrice(originalPrice);
     orderPrice.setOrderPrice(orderTotal);
     orderPrice.setWeight(weight);
     orderPrice.setNeedPayMoney(orderTotal);
     orderPrice = this.cartPluginBundle.coutPrice(orderPrice);
     return orderPrice;
   }

   public OrderPrice countPrice(List<StoreCartItem> storeCart, String regionid, String[] shippingId, Boolean isProtected)
   {
     OrderPrice orderPrice = new OrderPrice();

     Double weight = Double.valueOf(0.0D);

     Double originalPrice = Double.valueOf(0.0D);

     Double orderTotal = Double.valueOf(0.0D);

     Double dlyPrice = Double.valueOf(0.0D);

     Double coupPrice = Double.valueOf(0.0D);

     StoreMember member = this.storeMemberManager.getStoreMember();

     if (member == null) coupPrice = originalPrice;
     for (StoreCartItem storeCartItem : storeCart)
     {
       StoreGoods goods = this.storeGoodsManager.getGoods(storeCartItem.getGoods_id());

       weight = Double.valueOf(CurrencyUtil.add(weight.doubleValue(), CurrencyUtil.mul(goods.getWeight().doubleValue(), storeCartItem.getNum()).doubleValue()));
       originalPrice = Double.valueOf(CurrencyUtil.add(originalPrice.doubleValue(), CurrencyUtil.mul(storeCartItem.getPrice().doubleValue(), storeCartItem.getNum()).doubleValue()));
       if (member != null) {
         coupPrice = Double.valueOf(CurrencyUtil.add(coupPrice.doubleValue(), CurrencyUtil.mul(storeCartItem.getPrice().doubleValue(), storeCartItem.getNum()).doubleValue()));
       }
     }







     if ((regionid != null) && (isProtected != null) &&
       (shippingId.length > 0)) {
       Double dlyPriceTotal = Double.valueOf(0.0D);
       for (int i = 0; i < shippingId.length; i++)
       {
         Double[] priceArray = this.dlyTypeManager.countPrice(Integer.valueOf(shippingId[i]), weight, originalPrice, regionid);
         dlyPrice = priceArray[0];

         if (member != null)
         {
           DiscountPrice discountPrice = this.promotionManager.applyOrderPmt(coupPrice, dlyPrice, Integer.valueOf(0), member.getLv_id());
           coupPrice = discountPrice.getOrderPrice();
           dlyPrice = discountPrice.getShipFee();
         }
         dlyPriceTotal = Double.valueOf(CurrencyUtil.add(dlyPriceTotal.doubleValue(), dlyPrice.doubleValue()));
       }
       dlyPrice = dlyPriceTotal;
     }









     Double reducePrice = Double.valueOf(CurrencyUtil.sub(originalPrice.doubleValue(), coupPrice.doubleValue()));


     orderTotal = Double.valueOf(CurrencyUtil.add(coupPrice.doubleValue(), 21.0D));

     orderPrice.setDiscountPrice(reducePrice);
     orderPrice.setGoodsPrice(coupPrice);
     orderPrice.setShippingPrice(Double.valueOf(21.0D));

     orderPrice.setPoint(Integer.valueOf(0));
     orderPrice.setOriginalPrice(originalPrice);
     orderPrice.setOrderPrice(orderTotal);
     orderPrice.setWeight(weight);
     orderPrice.setNeedPayMoney(orderTotal);
     orderPrice = this.cartPluginBundle.coutPrice(orderPrice);
     return null;
   }

   public List<Map> storeListGoods(String sessionid)
   {
     List<Map> storeGoodsList = new ArrayList();
     List<StoreCartItem> goodsList = new ArrayList();

     goodsList = listGoods(sessionid);
     for (StoreCartItem item : goodsList) {
       findStoreMap(storeGoodsList, item);
     }
     return storeGoodsList;
   }






   private void findStoreMap(List<Map> storeGoodsList, StoreCartItem item)
   {
     int is_store = 0;
     if (storeGoodsList.isEmpty()) {
       addGoodsList(item, storeGoodsList);
     } else {
       for (Map map : storeGoodsList) {
         if (map.containsValue(item.getStore_id())) {
           List list = (List)map.get("goodslist");
           list.add(item);
           is_store = 1;
         }
       }
       if (is_store == 0) {
         addGoodsList(item, storeGoodsList);
       }
     }
   }




   private void addGoodsList(StoreCartItem item, List<Map> storeGoodsList)
   {
     Map map = new HashMap();
     List list = new ArrayList();
     list.add(item);
     map.put("store_id", item.getStore_id());
     map.put("store_name", item.getStore_name());
     map.put("goodslist", list);
     storeGoodsList.add(map);
   }

   public void clean(String sessionid) {
     String sql = "delete from cart where session_id=?";

     this.baseDaoSupport.execute(sql, new Object[] { sessionid });
     HttpCacheManager.sessionChange();
   }

   public IDlyTypeManager getDlyTypeManager() { return this.dlyTypeManager; }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }

   public IPromotionManager getPromotionManager() { return this.promotionManager; }

   public void setPromotionManager(IPromotionManager promotionManager) {
     this.promotionManager = promotionManager;
   }

   public IStoreGoodsManager getStoreGoodsManager() { return this.storeGoodsManager; }

   public void setStoreGoodsManager(IStoreGoodsManager storeGoodsManager) {
     this.storeGoodsManager = storeGoodsManager;
   }

   public CartPluginBundle getCartPluginBundle() { return this.cartPluginBundle; }

   public void setCartPluginBundle(CartPluginBundle cartPluginBundle) {
     this.cartPluginBundle = cartPluginBundle;
   }

   public IStoreMemberManager getStoreMemberManager() {
     return this.storeMemberManager;
   }

   public void setStoreMemberManager(IStoreMemberManager storeMemberManager) {
     this.storeMemberManager = storeMemberManager;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-b2b2c.jar!\com\enation\app\b2b2c\core\service\cart\impl\StoreCartManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */