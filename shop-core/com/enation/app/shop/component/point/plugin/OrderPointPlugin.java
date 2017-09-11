 package com.enation.app.shop.component.point.plugin;

 import com.enation.app.base.core.model.Member;
 import com.enation.app.base.core.plugin.job.IEveryDayExecuteEvent;
 import com.enation.app.shop.core.model.Delivery;
 import com.enation.app.shop.core.model.DeliveryItem;
 import com.enation.app.shop.core.model.FreezePoint;
 import com.enation.app.shop.core.model.Order;
 import com.enation.app.shop.core.model.support.CartItem;
 import com.enation.app.shop.core.model.support.OrderPrice;
 import com.enation.app.shop.core.plugin.cart.ICountPriceEvent;
 import com.enation.app.shop.core.plugin.order.IAfterOrderCreateEvent;
 import com.enation.app.shop.core.plugin.order.IOrderCanelEvent;
 import com.enation.app.shop.core.plugin.order.IOrderPayEvent;
 import com.enation.app.shop.core.plugin.order.IOrderRestoreEvent;
 import com.enation.app.shop.core.plugin.order.IOrderReturnsEvent;
 import com.enation.app.shop.core.service.IMemberManager;
 import com.enation.app.shop.core.service.IMemberPointManger;
 import com.enation.framework.context.webcontext.ThreadContextHolder;
 import com.enation.framework.database.IDaoSupport;
 import com.enation.framework.plugin.AutoRegisterPlugin;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpSession;
 import org.springframework.stereotype.Component;














 @Component
 public class OrderPointPlugin
   extends AutoRegisterPlugin
   implements IAfterOrderCreateEvent, IEveryDayExecuteEvent, IOrderPayEvent, IOrderCanelEvent, IOrderRestoreEvent, IOrderReturnsEvent, ICountPriceEvent
 {
   private IDaoSupport daoSupport;
   private IMemberPointManger memberPointManger;
   private IMemberManager memberManager;

   public void onAfterOrderCreate(Order order, List<CartItem> itemList, String sessionid)
   {
     if (order.getMember_id() != null) {
       Member member = this.memberManager.get(order.getMember_id());





       if (this.memberPointManger.checkIsOpen("buygoods")) {
         int point = this.memberPointManger.getItemPoint("buygoods_num");
         int mp = this.memberPointManger.getItemPoint("buygoods_num_mp");
         point = order.getGoods_amount().intValue() * point;
         mp = order.getGoods_amount().intValue() * mp;
         FreezePoint freezePoint = new FreezePoint();
         freezePoint.setMemberid(order.getMember_id().intValue());
         freezePoint.setPoint(point);
         freezePoint.setMp(mp);
         freezePoint.setType("buygoods");
         freezePoint.setOrderid(order.getOrder_id());
         this.memberPointManger.addFreezePoint(freezePoint, member.getName());
       }






       Map map = getGoodsPoint(sessionid);
       int goodspoint = ((Double)map.get("point")).intValue();

       FreezePoint freezeGoodsPoint = new FreezePoint();
       freezeGoodsPoint.setMemberid(order.getMember_id().intValue());
       freezeGoodsPoint.setPoint(goodspoint);
       freezeGoodsPoint.setMp(goodspoint);
       freezeGoodsPoint.setType("buygoods");
       freezeGoodsPoint.setOrderid(order.getOrder_id());
       this.memberPointManger.addFreezePoint(freezeGoodsPoint, member.getName());








       if ((this.memberPointManger.checkIsOpen("register_link")) &&
         (member.getParentid().intValue() != 0) && (member.getRecommend_point_state() == 0)) {
         int point = this.memberPointManger.getItemPoint("register_link_num");
         int mp = this.memberPointManger.getItemPoint("register_link_num_mp");
         FreezePoint freezePoint = new FreezePoint();
         freezePoint.setMemberid(member.getParentid().intValue());
         freezePoint.setChildid(order.getMember_id());
         freezePoint.setPoint(point);
         freezePoint.setMp(mp);
         freezePoint.setType("register_link");
         freezePoint.setOrderid(order.getOrder_id());
         this.memberPointManger.addFreezePoint(freezePoint, member.getName());
       }
     }
   }











   public void everyDay()
   {
     List<FreezePoint> list = this.memberPointManger.listByBeforeDay(15);
     for (FreezePoint fp : list)
     {

       if ((fp.getOrder_status() == 6) || (fp.getOrder_status() == 7)) {
         this.memberPointManger.thaw(fp, false);
       }
     }
   }







   public void pay(Order order, boolean isOnline)
   {
     if (order.getMember_id() != null) {
       Member member = this.memberManager.get(order.getMember_id());
       if (isOnline)
       {
         if (this.memberPointManger.checkIsOpen("onlinepay")) {
           int point = this.memberPointManger.getItemPoint("onlinepay_num");
           int mp = this.memberPointManger.getItemPoint("onlinepay_num_mp");
           FreezePoint freezePoint = new FreezePoint();
           freezePoint.setMemberid(order.getMember_id().intValue());
           freezePoint.setPoint(point);
           freezePoint.setMp(mp);
           freezePoint.setType("onlinepay");
           freezePoint.setOrderid(order.getOrder_id());
           this.memberPointManger.addFreezePoint(freezePoint, member.getName());
         }
       }
     }
   }




   public void canel(Order order)
   {
     if ((order != null) && (order.getOrder_id() != null)) {
       this.memberPointManger.deleteByOrderId(order.getOrder_id());
     }
   }

   public IMemberPointManger getMemberPointManger()
   {
     return this.memberPointManger;
   }

   public void setMemberPointManger(IMemberPointManger memberPointManger)
   {
     this.memberPointManger = memberPointManger;
   }

   public IMemberManager getMemberManager()
   {
     return this.memberManager;
   }

   public void setMemberManager(IMemberManager memberManager)
   {
     this.memberManager = memberManager;
   }





   public void restore(Order order)
   {
     if (order.getMember_id() != null) {
       Member member = this.memberManager.get(order.getMember_id());





       if (this.memberPointManger.checkIsOpen("buygoods")) {
         int point = this.memberPointManger.getItemPoint("buygoods_num");
         int mp = this.memberPointManger.getItemPoint("buygoods_num_mp");
         point = order.getGoods_amount().intValue() * point;
         mp = order.getGoods_amount().intValue() * mp;
         FreezePoint freezePoint = new FreezePoint();
         freezePoint.setMemberid(order.getMember_id().intValue());
         freezePoint.setPoint(point);
         freezePoint.setMp(mp);
         freezePoint.setType("buygoods");
         freezePoint.setOrderid(order.getOrder_id());
         this.memberPointManger.addFreezePoint(freezePoint, member.getName());
       }










       if ((this.memberPointManger.checkIsOpen("register_link")) &&
         (member.getParentid().intValue() != 0) && (member.getRecommend_point_state() == 0)) {
         int point = this.memberPointManger.getItemPoint("register_link_num");
         int mp = this.memberPointManger.getItemPoint("register_link_num_mp");
         FreezePoint freezePoint = new FreezePoint();
         freezePoint.setMemberid(member.getParentid().intValue());
         freezePoint.setChildid(order.getMember_id());
         freezePoint.setPoint(point);
         freezePoint.setMp(mp);
         freezePoint.setType("register_link");
         freezePoint.setOrderid(order.getOrder_id());
         this.memberPointManger.addFreezePoint(freezePoint, member.getName());
       }
     }
   }











   private int getGoodsPricePoint(Double goodsPrice)
   {
     if (this.memberPointManger.checkIsOpen("buygoods"))
     {
       int point = this.memberPointManger.getItemPoint("buygoods_num");
       point = goodsPrice.intValue() * point;
       return point;
     }

     return 0;
   }

   public OrderPrice countPrice(OrderPrice orderprice)
   {
     String sessionid = ThreadContextHolder.getHttpRequest().getSession().getId();
     Map map = getGoodsPoint(sessionid);
     int point = ((Double)map.get("point")).intValue();
     double goodsprice = ((Double)map.get("price")).doubleValue();


     int price_point = getGoodsPricePoint(Double.valueOf(goodsprice));
     point += price_point;
     orderprice.setPoint(Integer.valueOf(point));
     return orderprice;
   }

   private Map getGoodsPoint(String sessionid)
   {
     StringBuffer sql = new StringBuffer();
     sql.append("select  sum(g.point * c.num) point ,sum( c.price *c.num ) price from  es_cart  c,es_product  p ,es_goods g  where    c.product_id=p.product_id and p.goods_id= g.goods_id and c.session_id=?");

     Map map = this.daoSupport.queryForMap(sql.toString(), new Object[] { sessionid });
     return map;
   }


   public void returned(Delivery delivery, List<DeliveryItem> itemList)
   {
     this.memberPointManger.deleteByOrderId(delivery.getOrder_id());
   }

   public IDaoSupport getDaoSupport()
   {
     return this.daoSupport;
   }

   public void setDaoSupport(IDaoSupport daoSupport) {
     this.daoSupport = daoSupport;
   }
 }


/* Location:              C:\Users\worgen\Desktop\临时\javashop_b2b2c.war!\WEB-INF\lib\component-shop-core.jar!\com\enation\app\shop\component\point\plugin\OrderPointPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */