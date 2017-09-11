 package com.enation.app.shop.core.service.impl;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.shop.core.model.Cart;
 import com.enation.app.shop.core.model.GoodsLvPrice;
 import com.enation.app.shop.core.model.mapper.CartItemMapper;
 import com.enation.app.shop.core.model.support.CartItem;
 import com.enation.app.shop.core.model.support.DiscountPrice;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.plugin.cart.CartPluginBundle;
 import com.enation.app.shop.core.service.ICartManager;
 import com.enation.app.shop.core.service.IDlyTypeManager;
 import com.enation.app.shop.core.service.IMemberLvManager;
 import com.enation.app.shop.core.service.IPromotionManager;
 import com.enation.eop.processor.httpcache.HttpCacheManager;
 import com.enation.eop.sdk.context.EopSetting;
 import com.enation.eop.sdk.database.BaseSupport;
 import com.enation.eop.sdk.user.IUserService;
 import com.enation.eop.sdk.user.UserServiceFactory;
 import com.enation.framework.database.DoubleMapper;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.util.CurrencyUtil;
 import java.util.List;
 import org.apache.log4j.Logger;
 import org.springframework.transaction.annotation.Propagation;
 import org.springframework.transaction.annotation.Transactional;














 public class CartManager
   extends BaseSupport
   implements ICartManager
 {
   private IDlyTypeManager dlyTypeManager;
   private CartPluginBundle cartPluginBundle;
   private IMemberLvManager memberLvManager;
   private IPromotionManager promotionManager;

   @Transactional(propagation=Propagation.REQUIRED)
   public int add(Cart cart)
   {
     HttpCacheManager.sessionChange();





     this.cartPluginBundle.onAdd(cart);



     String sql = "select count(0) from cart where  product_id=? and session_id=? and itemtype=? ";

     int count = this.baseDaoSupport.queryForInt(sql, new Object[] { cart.getProduct_id(), cart.getSession_id(), cart.getItemtype() });
     if (count > 0) {
       this.baseDaoSupport.execute("update cart set num=num+? where  product_id=? and session_id=? and itemtype=? ", new Object[] { cart.getNum(), cart.getProduct_id(), cart.getSession_id(), cart.getItemtype() });

       return 0;
     }


     this.baseDaoSupport.insert("cart", cart);


     Integer cartid = Integer.valueOf(this.baseDaoSupport.getLastId("cart"));
     cart.setCart_id(cartid);

     this.cartPluginBundle.onAfterAdd(cart);
     return cartid.intValue();
   }





   public Cart get(int cart_id)
   {
     return (Cart)this.baseDaoSupport.queryForObject("SELECT * FROM cart WHERE cart_id=?", Cart.class, new Object[] { Integer.valueOf(cart_id) });
   }

   public Cart getCartByProductId(int productId, String sessionid) {
     return (Cart)this.baseDaoSupport.queryForObject("SELECT * FROM cart WHERE product_id=? AND session_id=?", Cart.class, new Object[] { Integer.valueOf(productId), sessionid });
   }

   public Cart getCartByProductId(int productId, String sessionid, String addon) {
     return (Cart)this.baseDaoSupport.queryForObject("SELECT * FROM cart WHERE product_id=? AND session_id=? AND addon=?", Cart.class, new Object[] { Integer.valueOf(productId), sessionid, addon });
   }

   public Integer countItemNum(String sessionid) {
     String sql = "select count(0) from cart where session_id =?";
     return Integer.valueOf(this.baseDaoSupport.queryForInt(sql, new Object[] { sessionid }));
   }



   public List<CartItem> listGoods(String sessionid)
   {
     StringBuffer sql = new StringBuffer();

     sql.append("select g.cat_id as catid,g.goods_id,g.thumbnail,c.name ,  p.sn, p.specs  ,g.mktprice,g.unit,g.point,p.product_id,c.price,c.cart_id as cart_id,c.num as num,c.itemtype,c.addon  from " + getTableName("cart") + " c," + getTableName("product") + " p," + getTableName("goods") + " g ");
     sql.append("where c.itemtype=0 and c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=?");
     List<CartItem> list = this.daoSupport.queryForList(sql.toString(), new CartItemMapper(), new Object[] { sessionid });

     this.cartPluginBundle.filterList(list, sessionid);


     return list;
   }









   private void applyMemPrice(List<CartItem> itemList, List<GoodsLvPrice> memPriceList, double discount)
   {
     for (CartItem item : itemList) {
       double price = item.getCoupPrice().doubleValue() * discount;
       for (GoodsLvPrice lvPrice : memPriceList) {
         if (item.getProduct_id().intValue() == lvPrice.getProductid()) {
           price = lvPrice.getPrice().doubleValue();
         }
       }


       item.setCoupPrice(Double.valueOf(price));
     }
   }








   public void clean(String sessionid)
   {
     String sql = "delete from cart where session_id=?";

     this.baseDaoSupport.execute(sql, new Object[] { sessionid });
     HttpCacheManager.sessionChange();
   }

   public void clean(String sessionid, Integer userid, Integer siteid)
   {
     if ("2".equals(EopSetting.RUNMODE)) {
       String sql = "delete from es_cart_" + userid + "_" + siteid + " where session_id=?";

       this.daoSupport.execute(sql, new Object[] { sessionid });
     }
     else {
       String sql = "delete from cart where session_id=?";
       this.baseDaoSupport.execute(sql, new Object[] { sessionid });
     }

     if (this.logger.isDebugEnabled()) {
       this.logger.debug("clean cart sessionid[" + sessionid + "]");
     }
     HttpCacheManager.sessionChange();
   }

   public void delete(String sessionid, Integer cartid) {
     String sql = "delete from cart where session_id=? and cart_id=?";
     this.baseDaoSupport.execute(sql, new Object[] { sessionid, cartid });
     this.cartPluginBundle.onDelete(sessionid, cartid);
     this.cartPluginBundle.onDelete(sessionid, cartid);
     HttpCacheManager.sessionChange();
   }

   public void updateNum(String sessionid, Integer cartid, Integer num) {
     String sql = "update cart set num=? where session_id =? and cart_id=?";
     this.baseDaoSupport.execute(sql, new Object[] { num, sessionid, cartid });
   }

   public Double countGoodsTotal(String sessionid) {
     StringBuffer sql = new StringBuffer();
     sql.append("select sum( c.price * c.num ) as num from cart c ");
     sql.append("where  c.session_id=? and c.itemtype=0 ");
     Double price = (Double)this.baseDaoSupport.queryForObject(sql.toString(), new DoubleMapper(), new Object[] { sessionid });

     return price;
   }





   public Double countGoodsDiscountTotal(String sessionid)
   {
     List<CartItem> itemList = listGoods(sessionid);

     double price = 0.0D;
     for (CartItem item : itemList)
     {
       price = CurrencyUtil.add(price, item.getSubtotal().doubleValue());
     }

     return Double.valueOf(price);
   }



































































   public Integer countPoint(String sessionid)
   {
     StringBuffer sql = new StringBuffer();
     sql.append("select  sum(g.point * c.num) from " + getTableName("cart") + " c," + getTableName("product") + " p," + getTableName("goods") + " g ");



     sql.append("where (c.itemtype=0  or c.itemtype=1)  and c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=?");


     return Integer.valueOf(this.daoSupport.queryForInt(sql.toString(), new Object[] { sessionid }));
   }

   public Double countGoodsWeight(String sessionid)
   {
     StringBuffer sql = new StringBuffer("select sum( c.weight * c.num )  from cart c where c.session_id=?");

     Double weight = (Double)this.baseDaoSupport.queryForObject(sql.toString(), new DoubleMapper(), new Object[] { sessionid });

     return weight;
   }







   public OrderPrice countPrice(List<CartItem> cartItemList, Integer shippingid, String regionid)
   {
     OrderPrice orderPrice = new OrderPrice();

     Double weight = Double.valueOf(0.0D);

     Double originalPrice = Double.valueOf(0.0D);

     Double orderTotal = Double.valueOf(0.0D);

     Double dlyPrice = Double.valueOf(0.0D);

     Double coupPrice = Double.valueOf(0.0D);

     Member member = UserServiceFactory.getUserService().getCurrentMember();

     if (member == null) coupPrice = originalPrice;
     for (CartItem cartItem : cartItemList) {
       weight = Double.valueOf(CurrencyUtil.add(weight.doubleValue(), CurrencyUtil.mul(cartItem.getWeight(), cartItem.getNum()).doubleValue()));
       originalPrice = Double.valueOf(CurrencyUtil.add(originalPrice.doubleValue(), CurrencyUtil.mul(cartItem.getPrice().doubleValue(), cartItem.getNum()).doubleValue()));
       if (member != null) {
         coupPrice = Double.valueOf(CurrencyUtil.add(coupPrice.doubleValue(), CurrencyUtil.mul(cartItem.getPrice().doubleValue(), cartItem.getNum()).doubleValue()));
       }
     }

     int point = 0;







     if ((regionid != null) && (shippingid != null)) {
       if (shippingid.intValue() != 0)
       {
         Double[] priceArray = this.dlyTypeManager.countPrice(shippingid, weight, originalPrice, regionid);
         dlyPrice = priceArray[0];
       }

       if (member != null)
       {
         DiscountPrice discountPrice = this.promotionManager.applyOrderPmt(coupPrice, dlyPrice, Integer.valueOf(point), member.getLv_id());
         coupPrice = discountPrice.getOrderPrice();
         dlyPrice = discountPrice.getShipFee();
         point = discountPrice.getPoint().intValue();
       }
     }











     Double reducePrice = Double.valueOf(CurrencyUtil.sub(originalPrice.doubleValue(), coupPrice.doubleValue()));


     orderTotal = Double.valueOf(CurrencyUtil.add(coupPrice.doubleValue(), dlyPrice.doubleValue()));

     orderPrice.setDiscountPrice(reducePrice);
     orderPrice.setGoodsPrice(coupPrice);
     orderPrice.setShippingPrice(dlyPrice);
     orderPrice.setPoint(Integer.valueOf(point));
     orderPrice.setOriginalPrice(originalPrice);
     orderPrice.setOrderPrice(orderTotal);
     orderPrice.setWeight(weight);
     orderPrice.setNeedPayMoney(orderTotal);
     orderPrice = this.cartPluginBundle.coutPrice(orderPrice);
     return orderPrice;
   }
















   public IPromotionManager getPromotionManager()
   {
     return this.promotionManager;
   }

   public CartPluginBundle getCartPluginBundle()
   {
     return this.cartPluginBundle;
   }

   public void setCartPluginBundle(CartPluginBundle cartPluginBundle) {
     this.cartPluginBundle = cartPluginBundle;
   }

   public void setMemberLvManager(IMemberLvManager memberLvManager)
   {
     this.memberLvManager = memberLvManager;
   }

   public IDlyTypeManager getDlyTypeManager() {
     return this.dlyTypeManager;
   }

   public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
     this.dlyTypeManager = dlyTypeManager;
   }

   public IMemberLvManager getMemberLvManager() {
     return this.memberLvManager;
   }

   public void setPromotionManager(IPromotionManager promotionManager) {
     this.promotionManager = promotionManager;
   }

   public boolean checkGoodsInCart(Integer goodsid)
   {
     String sql = "select count(0) from cart where goods_id=?";
     return this.baseDaoSupport.queryForInt(sql, new Object[] { goodsid }) > 0;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop.jar!\com\enation\app\shop\core\service\impl\CartManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */